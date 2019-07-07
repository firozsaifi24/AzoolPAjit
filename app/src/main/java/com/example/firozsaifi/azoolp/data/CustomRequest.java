package com.example.firozsaifi.azoolp.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.firozsaifi.azoolp.interfaces.OnAccidentCarCallback;
import com.example.firozsaifi.azoolp.interfaces.OnAdsDetailCallback;
import com.example.firozsaifi.azoolp.interfaces.OnBrandCallback;
import com.example.firozsaifi.azoolp.interfaces.OnCategoryCallback;
import com.example.firozsaifi.azoolp.interfaces.OnHomeCallback;
import com.example.firozsaifi.azoolp.interfaces.OnLoginCallback;
import com.example.firozsaifi.azoolp.interfaces.OnModelCallback;
import com.example.firozsaifi.azoolp.interfaces.OnPartCallback;
import com.example.firozsaifi.azoolp.interfaces.OnProductAddCallback;
import com.example.firozsaifi.azoolp.interfaces.OnProductEditCallback;
import com.example.firozsaifi.azoolp.interfaces.OnProductUpdateCallback;
import com.example.firozsaifi.azoolp.interfaces.OnRegisterCallback;
import com.example.firozsaifi.azoolp.interfaces.OnRequestPartAddCallback;
import com.example.firozsaifi.azoolp.interfaces.OnRequestPartViewCallback;
import com.example.firozsaifi.azoolp.interfaces.OnShopCallback;
import com.example.firozsaifi.azoolp.interfaces.OnShopDetailsCallback;
import com.example.firozsaifi.azoolp.interfaces.OnYearCallback;
import com.example.firozsaifi.azoolp.model.Ads;
import com.example.firozsaifi.azoolp.model.Brand;
import com.example.firozsaifi.azoolp.model.Category;
import com.example.firozsaifi.azoolp.model.Model;
import com.example.firozsaifi.azoolp.model.ReqPartAds;
import com.example.firozsaifi.azoolp.model.Shop;
import com.example.firozsaifi.azoolp.model.Year;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomRequest {

    public StringRequest home(String url, final OnHomeCallback onHomeCallback)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Ads Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONArray jArr= new JSONArray(jObj.getString("alladsdata"));
                        Log.d("jArr",jArr.toString());

                        ArrayList<Ads> adsList= new ArrayList<>();

                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject eachAd = jArr.getJSONObject(i);
                            Ads ads=new Ads();
                            ads.setId(eachAd.getString("pdt_id"));
                            ads.setName(eachAd.getString("name"));
                            ads.setArabicName(eachAd.getString("arabic_name"));
                            ads.setDealerId(eachAd.getString("dealer_id"));
                            ads.setBrandId(eachAd.getString("brand_id"));
                            ads.setModelId(eachAd.getString("model_id"));
                            ads.setYearId(eachAd.getString("year_id"));
                            ads.setCategoryId(eachAd.getString("cat_id"));
                            ads.setPosted_date(eachAd.getString("date_added"));
                            ads.setImage(eachAd.getString("image"));
                            ads.setPartType(eachAd.getString("part_type"));
                            ads.setDescription(eachAd.getString("description"));
                            ads.setPrice(eachAd.getString("price"));
                            ads.setViews(eachAd.getString("views"));
                            ads.setGallery_id(eachAd.getString("id"));

                            adsList.add(ads);
                        }

                        Log.d("complaint arraylist ", adsList.toString());

                        onHomeCallback.onHomeSuccess(adsList);

                    }
                    else
                    {
                        onHomeCallback.onHomeError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onHomeCallback.onHomeError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onHomeCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onHomeCallback.onHomeError(error.getMessage());
                    } else {
                        onHomeCallback.onHomeError("");
                    }
                }
            }
        });
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }


    public StringRequest accidentCarAds(String url, final OnAccidentCarCallback onAccidentCarCallback)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Accident Ads Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONArray jArr= new JSONArray(jObj.getString("accidentadsdata"));
                        Log.d("jArr",jArr.toString());

                        ArrayList<Ads> adsList= new ArrayList<>();

                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject eachAd = jArr.getJSONObject(i);
                            Ads ads=new Ads();
                            ads.setId(eachAd.getString("pdt_id"));
                            ads.setName(eachAd.getString("name"));
                            ads.setArabicName(eachAd.getString("arabic_name"));
                            ads.setDealerId(eachAd.getString("dealer_id"));
                            ads.setBrandId(eachAd.getString("brand_id"));
                            ads.setModelId(eachAd.getString("model_id"));
                            ads.setYearId(eachAd.getString("year_id"));
                            ads.setCategoryId(eachAd.getString("cat_id"));
                            ads.setPosted_date(eachAd.getString("date_added"));
                            ads.setImage(eachAd.getString("image"));
                            ads.setPartType(eachAd.getString("part_type"));
                            ads.setDescription(eachAd.getString("description"));
                            ads.setPrice(eachAd.getString("price"));
                            ads.setViews(eachAd.getString("views"));
                            ads.setGallery_id(eachAd.getString("id"));

                            adsList.add(ads);
                        }

                        Log.d("complaint arraylist ", adsList.toString());

                        onAccidentCarCallback.onAccidentCarSuccess(adsList);

                    }
                    else
                    {
                        onAccidentCarCallback.onAccidentCarError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onAccidentCarCallback.onAccidentCarError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onAccidentCarCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onAccidentCarCallback.onAccidentCarError(error.getMessage());
                    } else {
                        onAccidentCarCallback.onAccidentCarError("");
                    }
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }

    public StringRequest partAds(String url, final OnPartCallback onPartCallback)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Part Ads Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONArray jArr= new JSONArray(jObj.getString("partadsdata"));
                        Log.d("jArr",jArr.toString());

                        ArrayList<Ads> adsList= new ArrayList<>();

                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject eachAd = jArr.getJSONObject(i);
                            Ads ads=new Ads();
                            ads.setId(eachAd.getString("pdt_id"));
                            ads.setName(eachAd.getString("name"));
                            ads.setArabicName(eachAd.getString("arabic_name"));
                            ads.setDealerId(eachAd.getString("dealer_id"));
                            ads.setBrandId(eachAd.getString("brand_id"));
                            ads.setModelId(eachAd.getString("model_id"));
                            ads.setYearId(eachAd.getString("year_id"));
                            ads.setCategoryId(eachAd.getString("cat_id"));
                            ads.setPosted_date(eachAd.getString("date_added"));
                            ads.setImage(eachAd.getString("image"));
                            ads.setPartType(eachAd.getString("part_type"));
                            ads.setDescription(eachAd.getString("description"));
                            ads.setPrice(eachAd.getString("price"));
                            ads.setViews(eachAd.getString("views"));
                            ads.setGallery_id(eachAd.getString("id"));

                            adsList.add(ads);
                        }

                        Log.d("complaint arraylist ", adsList.toString());

                        onPartCallback.onPartSuccess(adsList);

                    }
                    else
                    {
                        onPartCallback.onPartError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onPartCallback.onPartError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onPartCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onPartCallback.onPartError(error.getMessage());
                    } else {
                        onPartCallback.onPartError("");
                    }
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }

    public StringRequest shop(String url, final OnShopCallback onShopCallback)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Shop Ads Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONArray jArr= new JSONArray(jObj.getString("allshopdata"));
                        Log.d("jArr",jArr.toString());

                        ArrayList<Shop> shopList= new ArrayList<>();

                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject eachAd = jArr.getJSONObject(i);
                            Shop shop=new Shop();
                            shop.setId(eachAd.getString("id"));
                            shop.setName(eachAd.getString("fname"));
                            shop.setCompanyName(eachAd.getString("cname"));
                            shop.setCompanyNameArabic(eachAd.getString("arabic_name"));
                            shop.setImage(eachAd.getString("shop_image"));
                            shop.setTotalProducts(eachAd.getString("total_products"));
                            shop.setSparePart(eachAd.getString("spare_part"));
                            shop.setAccidentCar(eachAd.getString("accident_car"));

                            shopList.add(shop);
                        }

                        Log.d("complaint arraylist ", shopList.toString());

                        onShopCallback.onShopSuccess(shopList);

                    }
                    else
                    {
                        onShopCallback.onShopError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onShopCallback.onShopError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onShopCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onShopCallback.onShopError(error.getMessage());
                    } else {
                        onShopCallback.onShopError("");
                    }
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }

    public StringRequest adsDetail(String url, final String productid, final OnAdsDetailCallback onAdsDetailCallback)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Ads Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONObject jObjCar = jObj.getJSONObject("alladsdetailsdata");
                        //related cars
                        JSONArray jArrCar= new JSONArray(jObjCar.getString("related_car"));
                        Log.d("jArrCar",jArrCar.toString());

                        ArrayList<Ads> adsCarList= new ArrayList<>();

                        for (int i = 0; i < jArrCar.length(); i++) {
                            JSONObject eachAd = jArrCar.getJSONObject(i);
                            Ads ads=new Ads();
                            ads.setId(eachAd.getString("pdt_id"));
                            ads.setName(eachAd.getString("name"));
                            ads.setArabicName(eachAd.getString("arabic_name"));
                            ads.setDealerId(eachAd.getString("dealer_id"));
                            ads.setBrandId(eachAd.getString("brand_id"));
                            ads.setModelId(eachAd.getString("model_id"));
                            ads.setYearId(eachAd.getString("year_id"));
                            ads.setCategoryId(eachAd.getString("cat_id"));
                            ads.setPosted_date(eachAd.getString("date_added"));
                            ads.setImage(eachAd.getString("image"));
                            ads.setPartType(eachAd.getString("part_type"));
                            ads.setDescription(eachAd.getString("description"));
                            ads.setPrice(eachAd.getString("price"));
                            ads.setViews(eachAd.getString("views"));
                            ads.setGallery_id(eachAd.getString("id"));

                            adsCarList.add(ads);
                        }

                        Log.d("Related car arraylist ", adsCarList.toString());

                        //related parts
                        JSONArray jArrPart= new JSONArray(jObjCar.getString("related_part"));
                        Log.d("jArrPart",jArrPart.toString());

                        ArrayList<Ads> adsPartList= new ArrayList<>();

                        for (int i = 0; i < jArrPart.length(); i++) {
                            JSONObject eachAd = jArrPart.getJSONObject(i);
                            Ads ads=new Ads();
                            ads.setId(eachAd.getString("pdt_id"));
                            ads.setName(eachAd.getString("name"));
                            ads.setArabicName(eachAd.getString("arabic_name"));
                            ads.setDealerId(eachAd.getString("dealer_id"));
                            ads.setBrandId(eachAd.getString("brand_id"));
                            ads.setModelId(eachAd.getString("model_id"));
                            ads.setYearId(eachAd.getString("year_id"));
                            ads.setCategoryId(eachAd.getString("cat_id"));
                            ads.setPosted_date(eachAd.getString("date_added"));
                            ads.setImage(eachAd.getString("image"));
                            ads.setPartType(eachAd.getString("part_type"));
                            ads.setDescription(eachAd.getString("description"));
                            ads.setPrice(eachAd.getString("price"));
                            ads.setViews(eachAd.getString("views"));
                            ads.setGallery_id(eachAd.getString("id"));

                            adsPartList.add(ads);
                        }

                        Log.d("Related part arraylist ", adsPartList.toString());


                        onAdsDetailCallback.onAdsDetailSuccess(jObj, adsCarList, adsPartList);

                    }
                    else
                    {
                        onAdsDetailCallback.onAdsDetailError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onAdsDetailCallback.onAdsDetailError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onAdsDetailCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onAdsDetailCallback.onAdsDetailError(error.getMessage());
                    } else {
                        onAdsDetailCallback.onAdsDetailError("");
                    }
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap<String, String> parameter = new HashMap<>();
                parameter.put("productid", productid);
                Log.e("sending login request",parameter.toString());
                return parameter;


            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }


    public StringRequest shopDetail(String url, final String dealerid, final OnShopDetailsCallback onShopDetailsCallback)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Ads Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONObject jObjCar = jObj.getJSONObject("allshopdetailsdata");
                        //dealer all cars
                        JSONArray jArrCar= new JSONArray(jObjCar.getString("allaccidentcars"));
                        Log.d("jArrCar",jArrCar.toString());

                        ArrayList<Ads> adsCarList= new ArrayList<>();

                        for (int i = 0; i < jArrCar.length(); i++) {
                            JSONObject eachAd = jArrCar.getJSONObject(i);
                            Ads ads=new Ads();
                            ads.setId(eachAd.getString("pdt_id"));
                            ads.setName(eachAd.getString("name"));
                            ads.setArabicName(eachAd.getString("arabic_name"));
                            ads.setDealerId(eachAd.getString("dealer_id"));
                            ads.setBrandId(eachAd.getString("brand_id"));
                            ads.setModelId(eachAd.getString("model_id"));
                            ads.setYearId(eachAd.getString("year_id"));
                            ads.setCategoryId(eachAd.getString("cat_id"));
                            ads.setPosted_date(eachAd.getString("date_added"));
                            ads.setImage(eachAd.getString("image"));
                            ads.setPartType(eachAd.getString("part_type"));
                            ads.setDescription(eachAd.getString("description"));
                            ads.setPrice(eachAd.getString("price"));
                            ads.setViews(eachAd.getString("views"));
                            ads.setGallery_id(eachAd.getString("id"));

                            adsCarList.add(ads);
                        }

                        Log.d("Dealer car arraylist ", adsCarList.toString());

                        //dealer all parts
                        JSONArray jArrPart= new JSONArray(jObjCar.getString("allspareparts"));
                        Log.d("jArrPart",jArrPart.toString());

                        ArrayList<Ads> adsPartList= new ArrayList<>();

                        for (int i = 0; i < jArrPart.length(); i++) {
                            JSONObject eachAd = jArrPart.getJSONObject(i);
                            Ads ads=new Ads();
                            ads.setId(eachAd.getString("pdt_id"));
                            ads.setName(eachAd.getString("name"));
                            ads.setArabicName(eachAd.getString("arabic_name"));
                            ads.setDealerId(eachAd.getString("dealer_id"));
                            ads.setBrandId(eachAd.getString("brand_id"));
                            ads.setModelId(eachAd.getString("model_id"));
                            ads.setYearId(eachAd.getString("year_id"));
                            ads.setCategoryId(eachAd.getString("cat_id"));
                            ads.setPosted_date(eachAd.getString("date_added"));
                            ads.setImage(eachAd.getString("image"));
                            ads.setPartType(eachAd.getString("part_type"));
                            ads.setDescription(eachAd.getString("description"));
                            ads.setPrice(eachAd.getString("price"));
                            ads.setViews(eachAd.getString("views"));
                            ads.setGallery_id(eachAd.getString("id"));

                            adsPartList.add(ads);
                        }

                        Log.d("Dealer part arraylist ", adsPartList.toString());


                        //dealer all products
                        JSONArray jArrAllProduct= new JSONArray(jObjCar.getString("allproducts"));
                        Log.d("jArrAllProduct",jArrAllProduct.toString());

                        ArrayList<Ads> adsAllProductList= new ArrayList<>();

                        for (int i = 0; i < jArrAllProduct.length(); i++) {
                            JSONObject eachAd = jArrAllProduct.getJSONObject(i);
                            Ads ads=new Ads();
                            ads.setId(eachAd.getString("pdt_id"));
                            ads.setName(eachAd.getString("name"));
                            ads.setArabicName(eachAd.getString("arabic_name"));
                            ads.setDealerId(eachAd.getString("dealer_id"));
                            ads.setBrandId(eachAd.getString("brand_id"));
                            ads.setModelId(eachAd.getString("model_id"));
                            ads.setYearId(eachAd.getString("year_id"));
                            ads.setCategoryId(eachAd.getString("cat_id"));
                            ads.setPosted_date(eachAd.getString("date_added"));
                            ads.setImage(eachAd.getString("image"));
                            ads.setPartType(eachAd.getString("part_type"));
                            ads.setDescription(eachAd.getString("description"));
                            ads.setPrice(eachAd.getString("price"));
                            ads.setViews(eachAd.getString("views"));
                            ads.setGallery_id(eachAd.getString("id"));

                            adsAllProductList.add(ads);
                        }

                        Log.d("All Product arraylist ", adsAllProductList.toString());


                        onShopDetailsCallback.onShopDetailSuccess(jObj, adsCarList, adsPartList, adsAllProductList);

                    }
                    else
                    {
                        onShopDetailsCallback.onShopDetailError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onShopDetailsCallback.onShopDetailError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onShopDetailsCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onShopDetailsCallback.onShopDetailError(error.getMessage());
                    } else {
                        onShopDetailsCallback.onShopDetailError("");
                    }
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap<String, String> parameter = new HashMap<>();
                parameter.put("dealerid", dealerid);
                Log.e("sending login request",parameter.toString());
                return parameter;


            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }



    /*public StringRequest register(String url, final OnRegisterCallback onRegisterCallback, final String name, final String email, final String password, final String shopNameEnglish, final String shopNameArabic, final String shopPhone, final String shopWhatsapp, final String shopAddress, final String shopDescription )
    {

        StringRequest strReq= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Registration response", "Register Response: " + response);

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {

                        onRegisterCallback.onRegisterSuccess(jObj);
                    }
                    else
                    {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        onRegisterCallback.onRegisterError(errorMsg);

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    onRegisterCallback.onRegisterError("Invalid Input");
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Register Error response", "Registration Error: " + error.getMessage());
                if (error instanceof NetworkError) {
                    onRegisterCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onRegisterCallback.onRegisterError(error.getMessage());
                    } else {
                        onRegisterCallback.onRegisterError("");
                    }
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("shopNameEnglish", shopNameEnglish);
                params.put("shopNameArabic", shopNameArabic);
                params.put("shopPhone", shopPhone);
                params.put("shopWhatsapp", shopWhatsapp);
                params.put("shopAddress", shopAddress);
                params.put("shopDescription", shopDescription);

                return params;
            }
        };

        //strReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return strReq;

    }

    */

    public void register(String url, Context ctx, final OnRegisterCallback onRegisterCallback, final String path, final String path2, final String name, final String email, final String password, final String shopNameEnglish, final String shopNameArabic, final String shopPhone, final String shopWhatsapp, final String shopAddress, final String shopDescription)
    {

        try
        {
            String uploadid= UUID.randomUUID().toString();
            //uploading file to server
            new MultipartUploadRequest(ctx, uploadid, url)
                    .addFileToUpload(path,"image1")
                    .addFileToUpload(path2,"image2")
                    .addParameter("name", name)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .addParameter("shopNameEnglish", shopNameEnglish)
                    .addParameter("shopNameArabic", shopNameArabic)
                    .addParameter("shopPhone", shopPhone)
                    .addParameter("shopWhatsapp", shopWhatsapp)
                    .addParameter("shopAddress", shopAddress)
                    .addParameter("shopDescription", shopDescription)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setDelegate(new net.gotev.uploadservice.UploadStatusDelegate(){

                        @Override
                        public void onProgress(Context context, final UploadInfo uploadInfo) {
                            /*Handler handler= new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressDoalog.setProgress(uploadInfo.getProgressPercent());
                                }
                            });*/
                            //progressDoalog.setProgress((int)(uploadInfo.getUploadedBytes()/uploadInfo.getTotalBytes())*100);
                            //progressDialog.setProgress(uploadInfo.getProgressPercent());
                            Log.e("gotev","onProgress eltstr="+uploadInfo.getElapsedTimeString()+" elt="+uploadInfo.getElapsedTime()+" ratestr="+
                                    uploadInfo.getUploadRateString()+" prc="+uploadInfo.getProgressPercent()+" tb="+uploadInfo.getTotalBytes()+" ub="+uploadInfo.getUploadedBytes());
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            Log.e("gotev","onError "+exception.getMessage() + exception.getStackTrace());
                            onRegisterCallback.onRegisterError(exception.getMessage());
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Log.e("Server Response: ", serverResponse.getHttpCode()+ " "+serverResponse.getBodyAsString());
                            //Toast.makeText(getApplicationContext(),"Image uploaded Successfully",Toast.LENGTH_LONG).show();
                            try {
                                Log.e("Server Response: ",serverResponse.getBodyAsString());

                                JSONObject jObj = new JSONObject(serverResponse.getBodyAsString());
                                boolean error = jObj.getBoolean("error");

                                if(!error)
                                {
                                    onRegisterCallback.onRegisterSuccess(jObj);
                                }
                                else
                                {
                                    onRegisterCallback.onRegisterError(jObj.getString("error_msg"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                onRegisterCallback.onRegisterError("Invalid input");
                            }
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            Log.e("gotev","onCancelled "+uploadInfo);
                        }
                    })
                    .startUpload();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            onRegisterCallback.onRegisterError("Unknown Error Occurred!");
        }

    }


    public StringRequest login(String url, final OnLoginCallback onLoginCallback, final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Login Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        onLoginCallback.onLoginSuccess(jObj);
                    }
                    else
                    {
                        onLoginCallback.onLoginError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onLoginCallback.onLoginError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onLoginCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onLoginCallback.onLoginError(error.getMessage());
                    } else {
                        onLoginCallback.onLoginError("");
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap<String, String> parameter = new HashMap<>();
                parameter.put("email", email);
                parameter.put("password", password);
                Log.e("sending login request",parameter.toString());
                return parameter;


            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }

    public void requestPartAdd(String url, Context ctx, final OnRequestPartAddCallback onRequestPartAddCallback, final String path, final String brand, final String model, final String year, final String customername, final String phone, final String description)
    {

        try
        {
            String uploadid= UUID.randomUUID().toString();
            //uploading file to server
            new MultipartUploadRequest(ctx, uploadid, url)
                    .addFileToUpload(path,"imageReq")
                    .addParameter("brand", brand)
                    .addParameter("model", model)
                    .addParameter("year", year)
                    .addParameter("customername", customername)
                    .addParameter("phone", phone)
                    .addParameter("description", description)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setDelegate(new net.gotev.uploadservice.UploadStatusDelegate(){

                        @Override
                        public void onProgress(Context context, final UploadInfo uploadInfo) {
                            /*Handler handler= new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressDoalog.setProgress(uploadInfo.getProgressPercent());
                                }
                            });*/
                            //progressDoalog.setProgress((int)(uploadInfo.getUploadedBytes()/uploadInfo.getTotalBytes())*100);
                            //progressDialog.setProgress(uploadInfo.getProgressPercent());
                            Log.e("gotev","onProgress eltstr="+uploadInfo.getElapsedTimeString()+" elt="+uploadInfo.getElapsedTime()+" ratestr="+
                                    uploadInfo.getUploadRateString()+" prc="+uploadInfo.getProgressPercent()+" tb="+uploadInfo.getTotalBytes()+" ub="+uploadInfo.getUploadedBytes());
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            Log.e("gotev","onError "+exception.getMessage() + exception.getStackTrace());
                            onRequestPartAddCallback.onRequestPartAddError(exception.getMessage());
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Log.e("Server Response: ", serverResponse.getHttpCode()+ " "+serverResponse.getBodyAsString());
                            //Toast.makeText(getApplicationContext(),"Image uploaded Successfully",Toast.LENGTH_LONG).show();
                            try {
                                Log.e("Server Response: ",serverResponse.getBodyAsString());

                                JSONObject jObj = new JSONObject(serverResponse.getBodyAsString());
                                boolean error = jObj.getBoolean("error");

                                if(!error)
                                {
                                    onRequestPartAddCallback.onRequestPartAddSuccess(jObj);
                                }
                                else
                                {
                                    onRequestPartAddCallback.onRequestPartAddError(jObj.getString("error_msg"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                onRequestPartAddCallback.onRequestPartAddError("Invalid input");
                            }
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            Log.e("gotev","onCancelled "+uploadInfo);
                        }
                    })
                    .startUpload();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            onRequestPartAddCallback.onRequestPartAddError("Unknown Error Occurred!");
        }

    }


    public StringRequest requestPartView(String url, final OnRequestPartViewCallback onRequestPartViewCallback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("ReqPart view Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONArray jArr= new JSONArray(jObj.getString("reqpartdata"));
                        Log.d("jArr",jArr.toString());

                        ArrayList<ReqPartAds> reqPartAdsList= new ArrayList<>();

                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject eachAd = jArr.getJSONObject(i);
                            ReqPartAds reqPartAds=new ReqPartAds();
                            reqPartAds.setId(eachAd.getInt("id"));
                            reqPartAds.setBrand(eachAd.getString("brand"));
                            reqPartAds.setModel(eachAd.getString("model"));
                            reqPartAds.setYear(eachAd.getString("year"));
                            reqPartAds.setImage(eachAd.getString("image"));
                            reqPartAds.setCustomername(eachAd.getString("customername"));
                            reqPartAds.setPhone(eachAd.getString("phone"));
                            reqPartAds.setDescription(eachAd.getString("description"));
                            reqPartAds.setPosted_at(eachAd.getString("created_at"));

                            reqPartAdsList.add(reqPartAds);
                        }

                        Log.d("complaint arraylist ",reqPartAdsList.toString());

                        onRequestPartViewCallback.onRequestPartViewSuccess(reqPartAdsList);

                    }
                    else
                    {
                        onRequestPartViewCallback.onRequestPartViewError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onRequestPartViewCallback.onRequestPartViewError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onRequestPartViewCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onRequestPartViewCallback.onRequestPartViewError(error.getMessage());
                    } else {
                        onRequestPartViewCallback.onRequestPartViewError("");
                    }
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }

    public StringRequest brandView(String url, final OnBrandCallback onBrandCallback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Brand view Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONArray jArr= new JSONArray(jObj.getString("branddata"));
                        Log.d("jArr",jArr.toString());

                        ArrayList<Brand> brandList= new ArrayList<>();

                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject eachAd = jArr.getJSONObject(i);
                            Brand brand=new Brand();
                            brand.setId(eachAd.getInt("id"));
                            brand.setTitle(eachAd.getString("title"));
                            brand.setImage(eachAd.getString("image"));
                            brand.setCreated_at(eachAd.getString("date_added"));

                            brandList.add(brand);

                        }

                        Log.d("complaint arraylist ", brandList.toString());

                        onBrandCallback.onBrandSuccess(brandList);
                        //onRequestPartViewCallback.onRequestPartViewSuccess(reqPartAdsList);

                    }
                    else
                    {
                        onBrandCallback.onBrandError(jObj.getString("error_msg"));
                        //onRequestPartViewCallback.onRequestPartViewError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onBrandCallback.onBrandError("Invalid Input");
                    //onRequestPartViewCallback.onRequestPartViewError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onBrandCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onBrandCallback.onBrandError(error.getMessage());
                    } else {
                        onBrandCallback.onBrandError("");
                        //onRequestPartViewCallback.onRequestPartViewError("");
                    }
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }

    public StringRequest modelView(String url, final OnModelCallback onModelCallback, final String brandid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Model view Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONArray jArr= new JSONArray(jObj.getString("modeldata"));
                        Log.d("jArr",jArr.toString());

                        ArrayList<Model> modelList= new ArrayList<>();

                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject eachAd = jArr.getJSONObject(i);
                            Model model=new Model();
                            model.setId(eachAd.getInt("id"));
                            model.setBrandid(eachAd.getString("brand_id"));
                            model.setTitle(eachAd.getString("title"));
                            model.setCreated_at(eachAd.getString("date_added"));

                            modelList.add(model);

                        }

                        Log.d("complaint arraylist ", modelList.toString());

                        onModelCallback.onModelSuccess(modelList);
                        //onRequestPartViewCallback.onRequestPartViewSuccess(reqPartAdsList);

                    }
                    else
                    {
                        onModelCallback.onModelError(jObj.getString("error_msg"));
                        //onRequestPartViewCallback.onRequestPartViewError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onModelCallback.onModelError("Invalid Input");
                    //onRequestPartViewCallback.onRequestPartViewError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onModelCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onModelCallback.onModelError(error.getMessage());
                    } else {
                        onModelCallback.onModelError("");
                        //onRequestPartViewCallback.onRequestPartViewError("");
                    }
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap<String, String> parameter = new HashMap<>();
                parameter.put("brandid", brandid);
                Log.e("sending login request",parameter.toString());
                return parameter;


            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }

    public StringRequest yearView(String url, final OnYearCallback onYearCallback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Year view Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONArray jArr= new JSONArray(jObj.getString("yeardata"));
                        Log.d("jArr",jArr.toString());

                        ArrayList<Year> yearList= new ArrayList<>();

                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject eachAd = jArr.getJSONObject(i);
                            Year year=new Year();
                            year.setId(eachAd.getInt("id"));
                            year.setTitle(eachAd.getString("title"));
                            year.setCreated_at(eachAd.getString("date_added"));

                            yearList.add(year);

                        }

                        Log.d("complaint arraylist ", yearList.toString());

                        onYearCallback.onYearSuccess(yearList);
                        //onRequestPartViewCallback.onRequestPartViewSuccess(reqPartAdsList);

                    }
                    else
                    {
                        onYearCallback.onYearError(jObj.getString("error_msg"));
                        //onRequestPartViewCallback.onRequestPartViewError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onYearCallback.onYearError("Invalid Input");
                    //onRequestPartViewCallback.onRequestPartViewError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onYearCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onYearCallback.onYearError(error.getMessage());
                    } else {
                        onYearCallback.onYearError("");
                        //onRequestPartViewCallback.onRequestPartViewError("");
                    }
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }

    public StringRequest categoryView(String url, final OnCategoryCallback onCategoryCallback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Category Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONArray jArr= new JSONArray(jObj.getString("categorydata"));
                        Log.d("jArr",jArr.toString());

                        ArrayList<Category> categoryList= new ArrayList<>();

                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject eachAd = jArr.getJSONObject(i);
                            Category category=new Category();
                            category.setId(eachAd.getInt("id"));
                            category.setTitle(eachAd.getString("title"));
                            category.setCreated_at(eachAd.getString("date_added"));

                            categoryList.add(category);

                        }

                        Log.d("Category arraylist ", categoryList.toString());

                        onCategoryCallback.onCategorySuccess(categoryList);
                        //onRequestPartViewCallback.onRequestPartViewSuccess(reqPartAdsList);

                    }
                    else
                    {
                        onCategoryCallback.onCategoryError(jObj.getString("error_msg"));
                        //onRequestPartViewCallback.onRequestPartViewError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onCategoryCallback.onCategoryError("Invalid Input");
                    //onRequestPartViewCallback.onRequestPartViewError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onCategoryCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onCategoryCallback.onCategoryError(error.getMessage());
                    } else {
                        onCategoryCallback.onCategoryError("");
                        //onRequestPartViewCallback.onRequestPartViewError("");
                    }
                }
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }

 /*   public void addSpareProduct(String url, Context ctx, final OnProductAddCallback onProductAddCallback, final String path, final String path2, final String path3, final String brandid, final String modelid, final String yearid, final String categoryid, final String title, final String dealerid, final String part_type)
    {

        try
        {
            String uploadid= UUID.randomUUID().toString();
            //uploading file to server
            new MultipartUploadRequest(ctx, uploadid, url)
                    .addFileToUpload(path,"image1")
                    .addFileToUpload(path2,"image2")
                    .addFileToUpload(path3,"image3")
                    .addParameter("brandid", brandid)
                    .addParameter("modelid", modelid)
                    .addParameter("yearid", yearid)
                    .addParameter("categoryid", categoryid)
                    .addParameter("title", title)
                    .addParameter("dealerid", dealerid)
                    .addParameter("part_type", part_type)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setDelegate(new net.gotev.uploadservice.UploadStatusDelegate(){

                        @Override
                        public void onProgress(Context context, final UploadInfo uploadInfo) {
                            *//*Handler handler= new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressDoalog.setProgress(uploadInfo.getProgressPercent());
                                }
                            });*//*
                            //progressDoalog.setProgress((int)(uploadInfo.getUploadedBytes()/uploadInfo.getTotalBytes())*100);
                            //progressDialog.setProgress(uploadInfo.getProgressPercent());
                            Log.e("gotev","onProgress eltstr="+uploadInfo.getElapsedTimeString()+" elt="+uploadInfo.getElapsedTime()+" ratestr="+
                                    uploadInfo.getUploadRateString()+" prc="+uploadInfo.getProgressPercent()+" tb="+uploadInfo.getTotalBytes()+" ub="+uploadInfo.getUploadedBytes());
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            Log.e("gotev","onError "+exception.getMessage() + exception.getStackTrace());
                            onProductAddCallback.onProductAddError(exception.getMessage());
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Log.e("Server Response: ", serverResponse.getHttpCode()+ " "+serverResponse.getBodyAsString());
                            //Toast.makeText(getApplicationContext(),"Image uploaded Successfully",Toast.LENGTH_LONG).show();
                            try {
                                Log.e("Server Response: ",serverResponse.getBodyAsString());

                                JSONObject jObj = new JSONObject(serverResponse.getBodyAsString());
                                boolean error = jObj.getBoolean("error");

                                if(!error)
                                {
                                    onProductAddCallback.onProductAddSuccess(jObj);
                                }
                                else
                                {
                                    onProductAddCallback.onProductAddError(jObj.getString("error_msg"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                onProductAddCallback.onProductAddError("Invalid input");
                            }
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            Log.e("gotev","onCancelled "+uploadInfo);
                        }
                    })
                    .startUpload();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            onProductAddCallback.onProductAddError("Unknown Error Occurred!");
        }

    }*/

    public void addProduct(String url, Context ctx, final OnProductAddCallback onProductAddCallback, final String path, final String path2, final String path3, final String brandid, final String modelid, final String yearid, final String categoryid, final String title, final String description, final String price, final String dealerid, final String part_type)
    {

        try
        {
            String uploadid= UUID.randomUUID().toString();
            //uploading file to server
            new MultipartUploadRequest(ctx, uploadid, url)
                    .addFileToUpload(path,"pimageone")
                    .addFileToUpload(path2,"pimagetwo")
                    .addFileToUpload(path3,"pimagethree")
                    .addParameter("brandid", brandid)
                    .addParameter("modelid", modelid)
                    .addParameter("yearid", yearid)
                    .addParameter("categoryid", categoryid)
                    .addParameter("title", title)
                    .addParameter("description", description)
                    .addParameter("price", price)
                    .addParameter("dealerid", dealerid)
                    .addParameter("part_type", part_type)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setDelegate(new net.gotev.uploadservice.UploadStatusDelegate(){

                        @Override
                        public void onProgress(Context context, final UploadInfo uploadInfo) {
                            /*Handler handler= new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressDoalog.setProgress(uploadInfo.getProgressPercent());
                                }
                            });*/
                            //progressDoalog.setProgress((int)(uploadInfo.getUploadedBytes()/uploadInfo.getTotalBytes())*100);
                            //progressDialog.setProgress(uploadInfo.getProgressPercent());
                            Log.e("gotev","onProgress eltstr="+uploadInfo.getElapsedTimeString()+" elt="+uploadInfo.getElapsedTime()+" ratestr="+
                                    uploadInfo.getUploadRateString()+" prc="+uploadInfo.getProgressPercent()+" tb="+uploadInfo.getTotalBytes()+" ub="+uploadInfo.getUploadedBytes());
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            Log.e("gotev","onError "+exception.getMessage() + exception.getStackTrace());
                            onProductAddCallback.onProductAddError(exception.getMessage());
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Log.e("Server Response: ", serverResponse.getHttpCode()+ " "+serverResponse.getBodyAsString());
                            //Toast.makeText(getApplicationContext(),"Image uploaded Successfully",Toast.LENGTH_LONG).show();
                            try {
                                Log.e("Server Response: ",serverResponse.getBodyAsString());

                                JSONObject jObj = new JSONObject(serverResponse.getBodyAsString());
                                boolean error = jObj.getBoolean("error");

                                if(!error)
                                {
                                    onProductAddCallback.onProductAddSuccess(jObj);
                                }
                                else
                                {
                                    onProductAddCallback.onProductAddError(jObj.getString("error_msg"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                onProductAddCallback.onProductAddError("Invalid input");
                            }
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            Log.e("gotev","onCancelled "+uploadInfo);
                        }
                    })
                    .startUpload();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            onProductAddCallback.onProductAddError("Unknown Error Occurred!");
        }

    }

    public StringRequest viewProduct(String url, final OnHomeCallback onHomeCallback, final String dealerid)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Ads Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        JSONArray jArr= new JSONArray(jObj.getString("alladsdata"));
                        Log.d("jArr",jArr.toString());

                        ArrayList<Ads> adsList= new ArrayList<>();

                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject eachAd = jArr.getJSONObject(i);
                            Ads ads=new Ads();
                            ads.setId(eachAd.getString("pdt_id"));
                            ads.setName(eachAd.getString("name"));
                            ads.setArabicName(eachAd.getString("arabic_name"));
                            ads.setDealerId(eachAd.getString("dealer_id"));
                            ads.setBrandValue(eachAd.getString("brandValue"));
                            ads.setModelValue(eachAd.getString("modelValue"));
                            ads.setYearValue(eachAd.getString("yearValue"));
                            ads.setCategoryValue(eachAd.getString("categoryValue"));
                            ads.setBrandId(eachAd.getString("brand_id"));
                            ads.setModelId(eachAd.getString("model_id"));
                            ads.setYearId(eachAd.getString("year_id"));
                            ads.setCategoryId(eachAd.getString("cat_id"));
                            ads.setPosted_date(eachAd.getString("date_added"));
                            ads.setImage(eachAd.getString("image"));
                            ads.setPartType(eachAd.getString("part_type"));
                            ads.setDescription(eachAd.getString("description"));
                            ads.setPrice(eachAd.getString("price"));
                            ads.setViews(eachAd.getString("views"));
                            //ads.setGallery_id(eachAd.getString("id"));

                            adsList.add(ads);
                        }

                        Log.d("complaint arraylist ", adsList.toString());

                        onHomeCallback.onHomeSuccess(adsList);

                    }
                    else
                    {
                        onHomeCallback.onHomeError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onHomeCallback.onHomeError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onHomeCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onHomeCallback.onHomeError(error.getMessage());
                    } else {
                        onHomeCallback.onHomeError("");
                    }
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap<String, String> parameter = new HashMap<>();
                parameter.put("dealerid", dealerid);
                Log.e("sending dealer id",parameter.toString());
                return parameter;


            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }

    public StringRequest editProduct(String url, final OnProductEditCallback onProductEditCallback, final String productid)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Ads Response: ",response);

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error)
                    {
                        onProductEditCallback.onProductEditSuccess(jObj);
                    }
                    else
                    {
                        onProductEditCallback.onProductEditError(jObj.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    onProductEditCallback.onProductEditError("Invalid input");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.e("Login Error: ",error.getMessage());
                if (error instanceof NetworkError) {
                    onProductEditCallback.onNetworkError();
                } else {
                    if (error != null && error.getMessage() != null) {
                        onProductEditCallback.onProductEditError(error.getMessage());
                    } else {
                        onProductEditCallback.onProductEditError("");
                    }
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap<String, String> parameter = new HashMap<>();
                parameter.put("productid", productid);
                Log.e("sending product id",parameter.toString());
                return parameter;


            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }

    public void updateProduct(String url, Context ctx, final OnProductUpdateCallback onProductUpdateCallback, final String path, final String path2, final String path3, final String brandid, final String modelid, final String yearid, final String categoryid, final String title, final String description, final String price, final String dealerid, final String part_type, final String productid, final String gallery_id1, final String gallery_id2, final String gallery_id3, int totalImages)
    {

        if(totalImages==0)
        {

            try
            {
                String uploadid= UUID.randomUUID().toString();
                //uploading file to server
                new MultipartUploadRequest(ctx, uploadid, url)
                        .addParameter("brandid", brandid)
                        .addParameter("modelid", modelid)
                        .addParameter("yearid", yearid)
                        .addParameter("categoryid", categoryid)
                        .addParameter("title", title)
                        .addParameter("description", description)
                        .addParameter("price", price)
                        .addParameter("dealerid", dealerid)
                        .addParameter("part_type", part_type)
                        .addParameter("productid", productid)
                        .addParameter("gallery_id1", gallery_id1)
                        .addParameter("gallery_id2", gallery_id2)
                        .addParameter("gallery_id3", gallery_id3)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setDelegate(new net.gotev.uploadservice.UploadStatusDelegate(){

                            @Override
                            public void onProgress(Context context, final UploadInfo uploadInfo) {
                            /*Handler handler= new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressDoalog.setProgress(uploadInfo.getProgressPercent());
                                }
                            });*/
                                //progressDoalog.setProgress((int)(uploadInfo.getUploadedBytes()/uploadInfo.getTotalBytes())*100);
                                //progressDialog.setProgress(uploadInfo.getProgressPercent());
                                Log.e("gotev","onProgress eltstr="+uploadInfo.getElapsedTimeString()+" elt="+uploadInfo.getElapsedTime()+" ratestr="+
                                        uploadInfo.getUploadRateString()+" prc="+uploadInfo.getProgressPercent()+" tb="+uploadInfo.getTotalBytes()+" ub="+uploadInfo.getUploadedBytes());
                            }

                            @Override
                            public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                                Log.e("gotev","onError "+exception.getMessage() + exception.getStackTrace());
                                onProductUpdateCallback.onProductUpdateError(exception.getMessage());
                            }

                            @Override
                            public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                                Log.e("Server Response: ", serverResponse.getHttpCode()+ " "+serverResponse.getBodyAsString());
                                //Toast.makeText(getApplicationContext(),"Image uploaded Successfully",Toast.LENGTH_LONG).show();
                                try {
                                    Log.e("Server Response: ",serverResponse.getBodyAsString());

                                    JSONObject jObj = new JSONObject(serverResponse.getBodyAsString());
                                    boolean error = jObj.getBoolean("error");

                                    if(!error)
                                    {
                                        onProductUpdateCallback.onProductUpdateSuccess(jObj);
                                    }
                                    else
                                    {
                                        onProductUpdateCallback.onProductUpdateError(jObj.getString("error_msg"));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    onProductUpdateCallback.onProductUpdateError("Invalid input");
                                }
                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {
                                Log.e("gotev","onCancelled "+uploadInfo);
                            }
                        })
                        .startUpload();

            }
            catch(Exception e)
            {
                e.printStackTrace();
                onProductUpdateCallback.onProductUpdateError("Unknown Error Occurred!");
            }


        }
        else if(totalImages==3)
        {

            try
            {
                String uploadid= UUID.randomUUID().toString();
                //uploading file to server
                new MultipartUploadRequest(ctx, uploadid, url)
                        .addFileToUpload(path,"pupimageone")
                        .addFileToUpload(path2,"pupimagetwo")
                        .addFileToUpload(path3,"pupimagethree")
                        .addParameter("brandid", brandid)
                        .addParameter("modelid", modelid)
                        .addParameter("yearid", yearid)
                        .addParameter("categoryid", categoryid)
                        .addParameter("title", title)
                        .addParameter("description", description)
                        .addParameter("price", price)
                        .addParameter("dealerid", dealerid)
                        .addParameter("part_type", part_type)
                        .addParameter("productid", productid)
                        .addParameter("gallery_id1", gallery_id1)
                        .addParameter("gallery_id2", gallery_id2)
                        .addParameter("gallery_id3", gallery_id3)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setDelegate(new net.gotev.uploadservice.UploadStatusDelegate(){

                            @Override
                            public void onProgress(Context context, final UploadInfo uploadInfo) {
                            /*Handler handler= new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressDoalog.setProgress(uploadInfo.getProgressPercent());
                                }
                            });*/
                                //progressDoalog.setProgress((int)(uploadInfo.getUploadedBytes()/uploadInfo.getTotalBytes())*100);
                                //progressDialog.setProgress(uploadInfo.getProgressPercent());
                                Log.e("gotev","onProgress eltstr="+uploadInfo.getElapsedTimeString()+" elt="+uploadInfo.getElapsedTime()+" ratestr="+
                                        uploadInfo.getUploadRateString()+" prc="+uploadInfo.getProgressPercent()+" tb="+uploadInfo.getTotalBytes()+" ub="+uploadInfo.getUploadedBytes());
                            }

                            @Override
                            public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                                Log.e("gotev","onError "+exception.getMessage() + exception.getStackTrace());
                                onProductUpdateCallback.onProductUpdateError(exception.getMessage());
                            }

                            @Override
                            public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                                Log.e("Server Response: ", serverResponse.getHttpCode()+ " "+serverResponse.getBodyAsString());
                                //Toast.makeText(getApplicationContext(),"Image uploaded Successfully",Toast.LENGTH_LONG).show();
                                try {
                                    Log.e("Server Response: ",serverResponse.getBodyAsString());

                                    JSONObject jObj = new JSONObject(serverResponse.getBodyAsString());
                                    boolean error = jObj.getBoolean("error");

                                    if(!error)
                                    {
                                        onProductUpdateCallback.onProductUpdateSuccess(jObj);
                                    }
                                    else
                                    {
                                        onProductUpdateCallback.onProductUpdateError(jObj.getString("error_msg"));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    onProductUpdateCallback.onProductUpdateError("Invalid input");
                                }
                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {
                                Log.e("gotev","onCancelled "+uploadInfo);
                            }
                        })
                        .startUpload();

            }
            catch(Exception e)
            {
                e.printStackTrace();
                onProductUpdateCallback.onProductUpdateError("Unknown Error Occurred!");
            }

        }
        else if(totalImages==2)
        {

            try
            {
                String uploadid= UUID.randomUUID().toString();
                //uploading file to server
                new MultipartUploadRequest(ctx, uploadid, url)
                        .addFileToUpload(path,"pupimageone")
                        .addFileToUpload(path2,"pupimagetwo")
                        .addParameter("brandid", brandid)
                        .addParameter("modelid", modelid)
                        .addParameter("yearid", yearid)
                        .addParameter("categoryid", categoryid)
                        .addParameter("title", title)
                        .addParameter("description", description)
                        .addParameter("price", price)
                        .addParameter("dealerid", dealerid)
                        .addParameter("part_type", part_type)
                        .addParameter("productid", productid)
                        .addParameter("gallery_id1", gallery_id1)
                        .addParameter("gallery_id2", gallery_id2)
                        .addParameter("gallery_id3", gallery_id3)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setDelegate(new net.gotev.uploadservice.UploadStatusDelegate(){

                            @Override
                            public void onProgress(Context context, final UploadInfo uploadInfo) {
                            /*Handler handler= new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressDoalog.setProgress(uploadInfo.getProgressPercent());
                                }
                            });*/
                                //progressDoalog.setProgress((int)(uploadInfo.getUploadedBytes()/uploadInfo.getTotalBytes())*100);
                                //progressDialog.setProgress(uploadInfo.getProgressPercent());
                                Log.e("gotev","onProgress eltstr="+uploadInfo.getElapsedTimeString()+" elt="+uploadInfo.getElapsedTime()+" ratestr="+
                                        uploadInfo.getUploadRateString()+" prc="+uploadInfo.getProgressPercent()+" tb="+uploadInfo.getTotalBytes()+" ub="+uploadInfo.getUploadedBytes());
                            }

                            @Override
                            public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                                Log.e("gotev","onError "+exception.getMessage() + exception.getStackTrace());
                                onProductUpdateCallback.onProductUpdateError(exception.getMessage());
                            }

                            @Override
                            public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                                Log.e("Server Response: ", serverResponse.getHttpCode()+ " "+serverResponse.getBodyAsString());
                                //Toast.makeText(getApplicationContext(),"Image uploaded Successfully",Toast.LENGTH_LONG).show();
                                try {
                                    Log.e("Server Response: ",serverResponse.getBodyAsString());

                                    JSONObject jObj = new JSONObject(serverResponse.getBodyAsString());
                                    boolean error = jObj.getBoolean("error");

                                    if(!error)
                                    {
                                        onProductUpdateCallback.onProductUpdateSuccess(jObj);
                                    }
                                    else
                                    {
                                        onProductUpdateCallback.onProductUpdateError(jObj.getString("error_msg"));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    onProductUpdateCallback.onProductUpdateError("Invalid input");
                                }
                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {
                                Log.e("gotev","onCancelled "+uploadInfo);
                            }
                        })
                        .startUpload();

            }
            catch(Exception e)
            {
                e.printStackTrace();
                onProductUpdateCallback.onProductUpdateError("Unknown Error Occurred!");
            }

        }
        else if(totalImages==1)
        {

            try
            {
                String uploadid= UUID.randomUUID().toString();
                //uploading file to server
                new MultipartUploadRequest(ctx, uploadid, url)
                        .addFileToUpload(path,"pupimageone")
                        .addParameter("brandid", brandid)
                        .addParameter("modelid", modelid)
                        .addParameter("yearid", yearid)
                        .addParameter("categoryid", categoryid)
                        .addParameter("title", title)
                        .addParameter("description", description)
                        .addParameter("price", price)
                        .addParameter("dealerid", dealerid)
                        .addParameter("part_type", part_type)
                        .addParameter("productid", productid)
                        .addParameter("gallery_id1", gallery_id1)
                        .addParameter("gallery_id2", gallery_id2)
                        .addParameter("gallery_id3", gallery_id3)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setDelegate(new net.gotev.uploadservice.UploadStatusDelegate(){

                            @Override
                            public void onProgress(Context context, final UploadInfo uploadInfo) {
                            /*Handler handler= new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressDoalog.setProgress(uploadInfo.getProgressPercent());
                                }
                            });*/
                                //progressDoalog.setProgress((int)(uploadInfo.getUploadedBytes()/uploadInfo.getTotalBytes())*100);
                                //progressDialog.setProgress(uploadInfo.getProgressPercent());
                                Log.e("gotev","onProgress eltstr="+uploadInfo.getElapsedTimeString()+" elt="+uploadInfo.getElapsedTime()+" ratestr="+
                                        uploadInfo.getUploadRateString()+" prc="+uploadInfo.getProgressPercent()+" tb="+uploadInfo.getTotalBytes()+" ub="+uploadInfo.getUploadedBytes());
                            }

                            @Override
                            public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                                Log.e("gotev","onError "+exception.getMessage() + exception.getStackTrace());
                                onProductUpdateCallback.onProductUpdateError(exception.getMessage());
                            }

                            @Override
                            public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                                Log.e("Server Response: ", serverResponse.getHttpCode()+ " "+serverResponse.getBodyAsString());
                                //Toast.makeText(getApplicationContext(),"Image uploaded Successfully",Toast.LENGTH_LONG).show();
                                try {
                                    Log.e("Server Response: ",serverResponse.getBodyAsString());

                                    JSONObject jObj = new JSONObject(serverResponse.getBodyAsString());
                                    boolean error = jObj.getBoolean("error");

                                    if(!error)
                                    {
                                        onProductUpdateCallback.onProductUpdateSuccess(jObj);
                                    }
                                    else
                                    {
                                        onProductUpdateCallback.onProductUpdateError(jObj.getString("error_msg"));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    onProductUpdateCallback.onProductUpdateError("Invalid input");
                                }
                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {
                                Log.e("gotev","onCancelled "+uploadInfo);
                            }
                        })
                        .startUpload();

            }
            catch(Exception e)
            {
                e.printStackTrace();
                onProductUpdateCallback.onProductUpdateError("Unknown Error Occurred!");
            }

        }




        /*try
        {
            String uploadid= UUID.randomUUID().toString();
            //uploading file to server
            new MultipartUploadRequest(ctx, uploadid, url)
                    .addFileToUpload(path,"pupimageone")
                    .addFileToUpload(path2,"pupimagetwo")
                    .addFileToUpload(path3,"pupimagethree")
                    .addParameter("brandid", brandid)
                    .addParameter("modelid", modelid)
                    .addParameter("yearid", yearid)
                    .addParameter("categoryid", categoryid)
                    .addParameter("title", title)
                    .addParameter("description", description)
                    .addParameter("price", price)
                    .addParameter("dealerid", dealerid)
                    .addParameter("part_type", part_type)
                    .addParameter("productid", productid)
                    .addParameter("gallery_id1", gallery_id1)
                    .addParameter("gallery_id2", gallery_id2)
                    .addParameter("gallery_id3", gallery_id3)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setDelegate(new net.gotev.uploadservice.UploadStatusDelegate(){

                        @Override
                        public void onProgress(Context context, final UploadInfo uploadInfo) {
                            *//*Handler handler= new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressDoalog.setProgress(uploadInfo.getProgressPercent());
                                }
                            });*//*
                            //progressDoalog.setProgress((int)(uploadInfo.getUploadedBytes()/uploadInfo.getTotalBytes())*100);
                            //progressDialog.setProgress(uploadInfo.getProgressPercent());
                            Log.e("gotev","onProgress eltstr="+uploadInfo.getElapsedTimeString()+" elt="+uploadInfo.getElapsedTime()+" ratestr="+
                                    uploadInfo.getUploadRateString()+" prc="+uploadInfo.getProgressPercent()+" tb="+uploadInfo.getTotalBytes()+" ub="+uploadInfo.getUploadedBytes());
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            Log.e("gotev","onError "+exception.getMessage() + exception.getStackTrace());
                            onProductUpdateCallback.onProductUpdateError(exception.getMessage());
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Log.e("Server Response: ", serverResponse.getHttpCode()+ " "+serverResponse.getBodyAsString());
                            //Toast.makeText(getApplicationContext(),"Image uploaded Successfully",Toast.LENGTH_LONG).show();
                            try {
                                Log.e("Server Response: ",serverResponse.getBodyAsString());

                                JSONObject jObj = new JSONObject(serverResponse.getBodyAsString());
                                boolean error = jObj.getBoolean("error");

                                if(!error)
                                {
                                    onProductUpdateCallback.onProductUpdateSuccess(jObj);
                                }
                                else
                                {
                                    onProductUpdateCallback.onProductUpdateError(jObj.getString("error_msg"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                onProductUpdateCallback.onProductUpdateError("Invalid input");
                            }
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            Log.e("gotev","onCancelled "+uploadInfo);
                        }
                    })
                    .startUpload();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            onProductUpdateCallback.onProductUpdateError("Unknown Error Occurred!");
        }
*/
    }


}
