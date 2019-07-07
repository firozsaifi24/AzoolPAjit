package com.example.firozsaifi.azoolp.interfaces;

import com.example.firozsaifi.azoolp.model.Ads;

import java.util.ArrayList;

public interface OnAccidentCarCallback {

    void onAccidentCarSuccess(ArrayList<Ads> adsArrayList);
    void onAccidentCarError(String error);
    void onNetworkError();


}
