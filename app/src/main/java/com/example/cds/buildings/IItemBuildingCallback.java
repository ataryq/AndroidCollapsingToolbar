package com.example.cds.buildings;

import com.example.data.BuildingItem;

public interface IItemBuildingCallback {
    void remove(BuildingItem item);
    void click(BuildingItem item);
}
