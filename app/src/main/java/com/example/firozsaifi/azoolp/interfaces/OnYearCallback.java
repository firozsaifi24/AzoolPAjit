package com.example.firozsaifi.azoolp.interfaces;

import com.example.firozsaifi.azoolp.model.Year;

import java.util.ArrayList;

public interface OnYearCallback {

    void onYearSuccess(ArrayList<Year> yearList);
    void onYearError(String error);
    void onNetworkError();

}
