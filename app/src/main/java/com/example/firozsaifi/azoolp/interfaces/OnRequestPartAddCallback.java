package com.example.firozsaifi.azoolp.interfaces;

import org.json.JSONObject;

public interface OnRequestPartAddCallback {

    void onRequestPartAddSuccess(JSONObject credentials);
    void onRequestPartAddError(String error);
    void onNetworkError();

}
