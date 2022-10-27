package com.example.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BuildingItem {
    public static final String DistrictAll = "Все";

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public String address;
    @ColumnInfo
    public String metro;
    @ColumnInfo
    public String district;
    @ColumnInfo
    public String averagePrice;
    @ColumnInfo
    public String numberFloors;

}
