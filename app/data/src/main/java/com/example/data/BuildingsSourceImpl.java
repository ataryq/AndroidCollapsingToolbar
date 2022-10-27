package com.example.data;

import android.content.Context;

import androidx.room.Room;

import com.example.data.db.BuildingsDatabase;
import com.example.data.db.DbDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class BuildingsSourceImpl implements IBuildingsSource {

    private final BuildingsDatabase db;
    private final DbDao dao;

    public BuildingsSourceImpl(Context appContext) {
        db = Room.databaseBuilder(
                appContext,
                BuildingsDatabase.class,
                "database-name").build();
        dao = db.buildingsDao();

        generateBuildings();
    }

    @Override
    public Observable<List<BuildingItem>> getBuildings() {
        return dao.getAll();
    }

    @Override
    public void removeItem(BuildingItem item) {
        //@TODO Replace with safe construction
        new Thread(() -> dao.delete(item)).start();
    }

    @Override
    public void addItem(BuildingItem item) {
        //@TODO Replace with safe construction
        new Thread(() -> dao.insertAll(item)).start();
    }

    @Override
    public void updateItem(BuildingItem item) {
        //@TODO Replace with safe construction
        new Thread(() -> dao.update(item)).start();
    }

    private void generateBuildings() {
/*
        buildings = new ArrayList<>();

        for(int i = 0; i < 2; ++i) {
            buildings.add(new BuildingItem(
                    "ЖК Александра Невского",
                    "Санкт-Петербург, проспект Александра Невского, д. 1",
                    "Новочеркасская",
                    "Невский",
                    String.valueOf(i + 1),
                    "12"));

            buildings.add(new BuildingItem(
                    "ЖК Александра Невского 1",
                    "Санкт-Петербург, проспект Александра Невского, д. 1",
                    "Новочеркасская",
                    "Центральный",
                    String.valueOf(i + 2),
                    "12"));

            buildings.add(new BuildingItem(
                    "ЖК Александра Невского 2",
                    "Санкт-Петербург, проспект Александра Невского, д. 1",
                    "Новочеркасская",
                    "Приморский",
                    String.valueOf(i + 3),
                    "12"));

            buildings.add(new BuildingItem(
                    "ЖК Александра Невского 3",
                    "Санкт-Петербург, проспект Александра Невского, д. 1",
                    "Новочеркасская",
                    "Приморский",
                    String.valueOf(i + 4),
                    "12"));


        }

 */
    }
}
