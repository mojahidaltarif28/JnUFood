package com.example.jnufood.ui.Food_Item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.jnufood.databinding.FragmentFooditemBinding;

public class FoodItem extends Fragment {

private FragmentFooditemBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        FoodItemViewModel galleryViewModel =
                new ViewModelProvider(this).get(FoodItemViewModel.class);

    binding = FragmentFooditemBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textFoodItem;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}