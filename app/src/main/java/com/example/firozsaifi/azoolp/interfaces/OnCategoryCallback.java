package com.example.firozsaifi.azoolp.interfaces;

import com.example.firozsaifi.azoolp.model.Category;
import com.example.firozsaifi.azoolp.model.Year;

import java.util.ArrayList;

public interface OnCategoryCallback {

    void onCategorySuccess(ArrayList<Category> categoryList);
    void onCategoryError(String error);
    void onNetworkError();

}
