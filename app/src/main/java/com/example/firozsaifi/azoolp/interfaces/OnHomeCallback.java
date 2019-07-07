package com.example.firozsaifi.azoolp.interfaces;

import com.example.firozsaifi.azoolp.model.Ads;

import java.util.ArrayList;

public interface OnHomeCallback {

    void onHomeSuccess(ArrayList<Ads> adsArrayList);
    void onHomeError(String error);
    void onNetworkError();

}
