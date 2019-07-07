package com.example.firozsaifi.azoolp.interfaces;

import com.example.firozsaifi.azoolp.model.Ads;

import org.json.JSONObject;

import java.util.ArrayList;

public interface OnShopDetailsCallback {

    void onShopDetailSuccess(JSONObject shopDetail, ArrayList<Ads> carArrayList, ArrayList<Ads> spareArrayList, ArrayList<Ads> allProductArrayList);
    void onShopDetailError(String error);
    void onNetworkError();

}
