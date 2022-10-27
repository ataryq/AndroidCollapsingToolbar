package com.example.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.data.BuildingItem;

@Database(entities = {BuildingItem.class}, version = 1)
public abstract class BuildingsDatabase extends RoomDatabase {
    public abstract DbDao buildingsDao();
}