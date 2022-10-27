package com.example.cds.buildings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cds.BuildingViewModel;
import com.example.cds.R;
import com.example.cds.databinding.BuildingListFragmentBinding;
import com.example.data.BuildingItem;

import java.util.ArrayList;
import java.util.List;

public class BuildingsListFragment extends Fragment
        implements IItemBuildingCallback,
        AdapterView.OnItemSelectedListener {

    private BuildingListFragmentBinding binding;
    private BuildingViewModel buildingViewModel;
    private BuildingsListAdapter adapter;
    private String[] districtsArray;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = BuildingListFragmentBinding.inflate(inflater, container, false);
        buildingViewModel = new ViewModelProvider(requireActivity()).get(BuildingViewModel.class);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new BuildingsListAdapter(this);

        buildingViewModel.getBuildings().observe(getViewLifecycleOwner(), o -> {
            adapter.setSource(o);
            initSettings(o);
            adapter.notifyDataSetChanged();
        });

        initPriceFilter();

        binding.buildingListRv.setAdapter(adapter);

        binding.addBtn.setOnClickListener(v -> {
            NavHostFragment.findNavController(BuildingsListFragment.this)
                    .navigate(R.id.action_FirstFragment_to_AddFragment);
        });

        binding.filterBtn.setOnClickListener( v -> {
            setVisibilityFilters(binding.filterPanel.getVisibility() != View.VISIBLE);
        });

        buildingViewModel.updateBuildings();
    }

    @Override
    public void onResume() {
        super.onResume();
        buildingViewModel.updateBuildings();
    }

    private void setVisibilityFilters(boolean isVisible) {
        View panel = binding.filterPanel;
        if(!isVisible) panel.setVisibility(View.GONE);
        else panel.setVisibility(View.VISIBLE);
    }

    protected void initSettings(List<BuildingItem> items) {
        Log.d("mylog", "initSettings");

        binding.enableFilter.setOnClickListener(v -> {
            boolean isActive = binding.enableFilter.isChecked();
            buildingViewModel.setEnableFilter(isActive);
            setVisibilityFilters(isActive);
        });

        initDistrict();
        setPrices();
    }

    Integer parseInt(String numberString) {
        try {
            return Integer.parseInt(numberString);
        }
        catch (NumberFormatException ignore) {
            return 0;
        }
    }

    @SuppressLint("SetTextI18n")
    private void setPrices() {
        Integer maxPriceVM = buildingViewModel.filterMaxPrice;
        Integer maxPriceET = parseInt(binding.maxPriceET.getText().toString());
        if(!maxPriceVM.equals(maxPriceET)) {
            binding.maxPriceET.setText(buildingViewModel.filterMaxPrice.toString());
        }

        Log.d("mylog", "maxPriceVM: " + maxPriceVM + " maxPriceET: " + maxPriceET);

        Integer minPriceVM = buildingViewModel.filterMinPrice;
        Integer minPriceET = parseInt(binding.minPriceET.getText().toString());

        if(!minPriceVM.equals(minPriceET)) {
            binding.minPriceET.setText(buildingViewModel.filterMinPrice.toString());
        }
    }

    @SuppressLint("SetTextI18n")
    private void initPriceFilter() {
        binding.minPriceET.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String value = s.toString();
                try {
                    int number = Integer.parseInt(value);
                    Log.d("mylog", "setMinPriceFilter: " + number);
                    buildingViewModel.setMinPriceFilter(number);
                }
                catch (NumberFormatException ignored) {
                    Log.d("mylog", "ignored");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.maxPriceET.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String value = s.toString();
                try {
                    int number = Integer.parseInt(value);
                    Log.d("mylog", "setMaxPriceFilter: " + number);
                    buildingViewModel.setMaxPriceFilter(number);
                }
                catch (NumberFormatException ignored) {
                    Log.d("mylog", "ignored");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }

    private void initDistrict() {
        districtsArray = buildingViewModel.getDistricts();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, districtsArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(spinnerAdapter);

        int i = 0;
        for(; i < districtsArray.length; ++i) {
            if(districtsArray[i].equals(buildingViewModel.filterDistrict)) {
                break;
            }
        }
        binding.spinner.setSelection(i);

        binding.spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void remove(BuildingItem item) {
        buildingViewModel.removeBuilding(item);
        Toast.makeText(requireContext(), "Удалено", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void click(BuildingItem item) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.id);
        NavHostFragment.findNavController(BuildingsListFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("mylog", "onItemSelected");

        buildingViewModel.setDistrictFilter(districtsArray[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}