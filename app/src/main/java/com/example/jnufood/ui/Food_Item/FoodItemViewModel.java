package com.example.jnufood.ui.Food_Item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodItemViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FoodItemViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Food Item fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}