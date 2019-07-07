package com.example.firozsaifi.azoolp.interfaces;

import org.json.JSONObject;

public interface OnProductEditCallback {
    void onProductEditSuccess(JSONObject adsDetails);
    void onProductEditError(String error);
    void onNetworkError();
}
