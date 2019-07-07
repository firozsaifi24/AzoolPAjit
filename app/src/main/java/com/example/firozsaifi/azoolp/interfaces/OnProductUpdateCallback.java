package com.example.firozsaifi.azoolp.interfaces;

import org.json.JSONObject;

public interface OnProductUpdateCallback {

    void onProductUpdateSuccess(JSONObject credentials);
    void onProductUpdateError(String error);
    void onNetworkError();

}
