package com.example.firozsaifi.azoolp.interfaces;

import org.json.JSONObject;

public interface OnProductAddCallback {

    void onProductAddSuccess(JSONObject credentials);
    void onProductAddError(String error);
    void onNetworkError();

}
