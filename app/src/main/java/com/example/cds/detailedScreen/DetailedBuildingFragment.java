package com.example.cds.detailedScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cds.BuildingViewModel;
import com.example.cds.databinding.DetailedBuildingFragmentBinding;
import com.example.data.BuildingItem;

public class DetailedBuildingFragment extends Fragment {

    protected DetailedBuildingFragmentBinding binding;
    protected BuildingItem buildingItem;
    protected BuildingViewModel buildingViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = DetailedBuildingFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backBtn.setOnClickListener(view1 -> getBack());

        binding.saveBtn.setOnClickListener(view1 -> {
            save();
        });

        buildingViewModel = new ViewModelProvider(requireActivity()).
                get(BuildingViewModel.class);

        Bundle arguments = getArguments();

        if(arguments != null) {
            int id = arguments.getInt("id");

            buildingViewModel.getBuildings().observe(getViewLifecycleOwner(), o -> {
                for(int i = 0; i < o.size(); ++i) {
                    if(o.get(i).id == id) {
                        buildingItem = o.get(i);
                        break;
                    }
                }

                initFields();
            } );

        }
    }

    protected void getBack() {
        NavHostFragment.findNavController(DetailedBuildingFragment.this).popBackStack();
    }

    private void initFields() {
        binding.nameET.setText(buildingItem.name);
        binding.adressET.setText(buildingItem.address);
        binding.metroET.setText(buildingItem.metro);
        binding.districtET.setText(buildingItem.district);
        binding.averagePriceET.setText(buildingItem.averagePrice);
        binding.numberFloorsET.setText(buildingItem.numberFloors);
    }

    private void save() {
        buildingItem.name = binding.nameET.getText().toString();
        buildingItem.metro = binding.adressET.getText().toString();
        buildingItem.metro = binding.metroET.getText().toString();
        buildingItem.district = binding.districtET.getText().toString();
        buildingItem.averagePrice = binding.averagePriceET.getText().toString();
        buildingItem.numberFloors = binding.numberFloorsET.getText().toString();

        buildingViewModel.updateBuilding(buildingItem);

        Toast.makeText(requireContext(), "Сохранено", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}