package com.example.firozsaifi.azoolp.interfaces;


import com.example.firozsaifi.azoolp.model.Model;

import java.util.ArrayList;

public interface OnModelCallback {

    void onModelSuccess(ArrayList<Model> modelList);
    void onModelError(String error);
    void onNetworkError();

}
