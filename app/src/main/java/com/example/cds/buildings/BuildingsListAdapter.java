package com.example.cds.buildings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cds.R;
import com.example.data.BuildingItem;

import java.util.ArrayList;
import java.util.List;

public class BuildingsListAdapter extends RecyclerView.Adapter<BuildingListItem> {
    private List<BuildingItem> items = new ArrayList<>();
    IItemBuildingCallback callback;

    BuildingsListAdapter(IItemBuildingCallback _callback) {
        callback = _callback;
    }

    public void setSource(List<BuildingItem> _items) {
        items = _items;
    }

    @NonNull
    @Override
    public BuildingListItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.building_card, parent, false);
        return new BuildingListItem(listItem, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingListItem holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
