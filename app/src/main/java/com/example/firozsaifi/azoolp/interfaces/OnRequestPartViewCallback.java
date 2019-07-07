package com.example.firozsaifi.azoolp.interfaces;

import com.example.firozsaifi.azoolp.model.Ads;
import com.example.firozsaifi.azoolp.model.ReqPartAds;

import java.util.ArrayList;

public interface OnRequestPartViewCallback {

    void onRequestPartViewSuccess(ArrayList<ReqPartAds> adsArrayList);
    void onRequestPartViewError(String error);
    void onNetworkError();

}
