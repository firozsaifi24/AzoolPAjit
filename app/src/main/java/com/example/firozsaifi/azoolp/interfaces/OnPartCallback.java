package com.example.firozsaifi.azoolp.interfaces;

import com.example.firozsaifi.azoolp.model.Ads;

import java.util.ArrayList;

public interface OnPartCallback {

    void onPartSuccess(ArrayList<Ads> adsArrayList);
    void onPartError(String error);
    void onNetworkError();


}
