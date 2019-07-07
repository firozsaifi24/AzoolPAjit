package com.example.firozsaifi.azoolp.interfaces;

import com.example.firozsaifi.azoolp.model.Ads;
import com.example.firozsaifi.azoolp.model.Shop;

import java.util.ArrayList;

public interface OnShopCallback {

    void onShopSuccess(ArrayList<Shop> adsArrayList);
    void onShopError(String error);
    void onNetworkError();


}
