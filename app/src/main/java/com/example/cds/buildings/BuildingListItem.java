package com.example.cds.buildings;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cds.databinding.BuildingCardBinding;
import com.example.data.BuildingItem;

public class BuildingListItem extends RecyclerView.ViewHolder {
    private IItemBuildingCallback callback;
    public BuildingListItem(@NonNull View itemView,
                            IItemBuildingCallback _callback) {
        super(itemView);
        callback = _callback;
    }

    public void bind(BuildingItem item) {
        BuildingCardBinding binding = BuildingCardBinding.bind(itemView);

        binding.name.setText(item.name);
        binding.metro.setText(item.metro);

        String price = item.averagePrice + " млн";
        binding.averagePrice.setText(price);

        binding.deleteBtn.setOnClickListener(v -> callback.remove(item));

        binding.getRoot().setOnClickListener(v -> callback.click(item));
    }
}
