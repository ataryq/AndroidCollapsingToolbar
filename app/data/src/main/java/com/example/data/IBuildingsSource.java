package com.example.data;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface IBuildingsSource {
    Observable<List<BuildingItem>> getBuildings();
    void removeItem(BuildingItem item);
    void addItem(BuildingItem item);
    void updateItem(BuildingItem item);
}
