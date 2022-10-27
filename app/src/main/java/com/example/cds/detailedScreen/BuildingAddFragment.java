package com.example.cds.detailedScreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cds.R;
import com.example.data.BuildingItem;

public class BuildingAddFragment extends DetailedBuildingFragment {

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.saveBtn.setImageResource(R.drawable.ic_baseline_add_24);

        binding.saveBtn.setOnClickListener(view1 -> {
            add();
        });
    }

    private void add() {
        buildingItem = new BuildingItem();

        buildingItem.name = binding.nameET.getText().toString();
        buildingItem.address = binding.adressET.getText().toString();
        buildingItem.metro = binding.metroET.getText().toString();
        buildingItem.district = binding.districtET.getText().toString();
        buildingItem.averagePrice = binding.averagePriceET.getText().toString();
        buildingItem.numberFloors = binding.numberFloorsET.getText().toString();

        try {
            Integer.parseInt(binding.averagePriceET.getText().toString());
        }
        catch (NumberFormatException ignore) {
            Toast.makeText(requireContext(), "Неверный формат цены", Toast.LENGTH_SHORT).show();
            return;
        }

        buildingViewModel.addBuilding(buildingItem);
        Toast.makeText(requireContext(), "Добавлено", Toast.LENGTH_SHORT).show();
        getBack();

    }
}
