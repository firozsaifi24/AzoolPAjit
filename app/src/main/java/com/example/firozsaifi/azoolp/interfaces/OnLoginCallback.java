package com.example.firozsaifi.azoolp.interfaces;

import org.json.JSONObject;

public interface OnLoginCallback {

    void onLoginSuccess(JSONObject credentials);
    void onLoginError(String error);
    void onNetworkError();

}
