package com.example.cds;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.data.BuildingItem;
import com.example.data.BuildingsSourceImpl;
import com.example.data.IBuildingsSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BuildingViewModel extends ViewModel {
    public String filterDistrict = BuildingItem.DistrictAll;
    public Integer filterMaxPrice = -1;
    public Integer filterMinPrice = -1;
    public boolean enableFilters = true;

    private final IBuildingsSource buildingSource = new BuildingsSourceImpl(MainActivity.AppContext);
    private final MutableLiveData<List<BuildingItem>> buildings =
            new MutableLiveData<>();
    private List<BuildingItem> rawBuildings;
    private Disposable subscription;

    public LiveData<List<BuildingItem>> getBuildings() {
        subscription = buildingSource.getBuildings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processBuildings);

        return buildings;
    }

    public void updateBuildings() {
        processBuildings(rawBuildings);
    }

    @Override
    protected void onCleared() {
        subscription.dispose();
        super.onCleared();
    }

    public void removeBuilding(BuildingItem item) {
        buildingSource.removeItem(item);
    }

    public void updateBuilding(BuildingItem item) {
        buildingSource.updateItem(item);
    }

    public void addBuilding(BuildingItem item) {
        buildingSource.addItem(item);
    }

    private void processBuildings(List<BuildingItem> items) {
        if(items == null || items.isEmpty()) return;

        rawBuildings = items;
        if(filterMaxPrice.equals(-1)) {
            calculateMaxMinPrices(items);
        }

        buildings.setValue(getFilteredBuilds(items));
    }


    public String [] getDistricts() {
        HashSet<String> districts = new HashSet<>();

        districts.add(BuildingItem.DistrictAll);

        for(BuildingItem item: rawBuildings) {
            districts.add(item.district);
        }

        String [] districtsArray = new String[0];
        districtsArray = districts.toArray(districtsArray);

        return districtsArray;
    }


    public void setDistrictFilter(String district) {
        if(filterDistrict.equals(district)) return;

        filterDistrict = district;
        updateBuildings();
    }

    public void setMinPriceFilter(Integer minPrice) {
        setPriceFilter(filterMaxPrice, minPrice);
    }

    public void setMaxPriceFilter(Integer maxPrice) {
        setPriceFilter(maxPrice, filterMinPrice);
    }

    public void setPriceFilter(Integer maxPrice, Integer minPrice) {
        if(filterMaxPrice.equals(maxPrice) && filterMinPrice.equals(minPrice)) return;

        filterMaxPrice = maxPrice;
        filterMinPrice = minPrice;
        updateBuildings();
    }

    public void setEnableFilter(boolean isEnabledFilter) {
        Log.d("mylog", "setEnableFilter " + isEnabledFilter);
        enableFilters = isEnabledFilter;
        updateBuildings();
    }

    private List<BuildingItem> getFilteredBuilds(List<BuildingItem> items) {
        Log.d("mylog", "getFilteredBuilds " + enableFilters);

        if(!enableFilters) {
            return items;
        }

        List<BuildingItem> filtered = items;

        filtered = getFilteredByDistrict(filtered);
        filtered = getFilteredByPrice(filtered);

        return filtered;
    }

    @SuppressWarnings("ConstantConditions")
    private List<BuildingItem> getFilteredByPrice(List<BuildingItem> items) {
        ArrayList<BuildingItem> filtered = new ArrayList<>();

        for (BuildingItem item: items) {
            int price = Integer.parseInt(item.averagePrice);
            price *= 10e5;

            if(price >= filterMinPrice && price <= filterMaxPrice) {
                filtered.add(item);
            }
        }

        return filtered;
    }


    private List<BuildingItem> getFilteredByDistrict(List<BuildingItem> items) {
        if(filterDistrict.isEmpty() || filterDistrict.equals(BuildingItem.DistrictAll)) {
            return items;
        }

        ArrayList<BuildingItem> filtered = new ArrayList<>();

        for (BuildingItem item: items) {
            if(item.district.equals(filterDistrict))
                filtered.add(item);
        }

        return filtered;
    }

    private void calculateMaxMinPrices(List<BuildingItem> items) {
        Integer maxPrice = null;
        Integer minPrice = null;

        for(BuildingItem item: items) {
            int price = Integer.parseInt(item.averagePrice);
            price *= 10e5;

            if(maxPrice == null) {
                maxPrice = price;
                minPrice = price;
            }

            maxPrice = Math.max(maxPrice, price);
            minPrice = Math.min(minPrice, price);
        }

        filterMaxPrice = maxPrice;
        filterMinPrice = minPrice;
    }
}
