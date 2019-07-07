package com.example.firozsaifi.azoolp.interfaces;

import com.example.firozsaifi.azoolp.model.Ads;

import org.json.JSONObject;

import java.util.ArrayList;

public interface OnAdsDetailCallback {

    void onAdsDetailSuccess(JSONObject adsDetail, ArrayList<Ads> carArrayList, ArrayList<Ads> spareArrayList);
    void onAdsDetailError(String error);
    void onNetworkError();

}
