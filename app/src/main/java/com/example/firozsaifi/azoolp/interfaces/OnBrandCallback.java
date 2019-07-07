package com.example.firozsaifi.azoolp.interfaces;

import com.example.firozsaifi.azoolp.model.Brand;

import java.util.ArrayList;

public interface OnBrandCallback {

    void onBrandSuccess(ArrayList<Brand> brandList);
    void onBrandError(String error);
    void onNetworkError();

}
