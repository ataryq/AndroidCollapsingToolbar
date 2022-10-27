package com.example.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.data.BuildingItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface DbDao {
    @Query("SELECT * FROM BuildingItem")
    Observable<List<BuildingItem>> getAll();

    @Query("SELECT * FROM BuildingItem WHERE id IN (:buildingIds)")
    List<BuildingItem> loadAllByIds(int[] buildingIds);

    @Update
    void update(BuildingItem user);

    @Insert
    void insertAll(BuildingItem... users);

    @Delete
    void delete(BuildingItem user);
}