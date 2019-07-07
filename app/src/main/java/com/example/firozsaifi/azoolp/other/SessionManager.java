package com.example.firozsaifi.azoolp.other;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

public class SessionManager {

    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "MyLoginData";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_DEALER_ID = "dealerid";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_COMPANY_NAME_ENGLISH = "companynameenglish";
    private static final String KEY_COMPANY_NAME_ARABIC = "companynamearabic";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_WHATSAPP = "whatsapp";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_SHOP_IMAGES = "shopimages";
    private static final String KEY_FIREBASE_NOTIFICATION = "firebasenotification";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn)
    {
        editor.putBoolean(KEY_IS_LOGGEDIN,isLoggedIn);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }
    public boolean isLoggedIn()
    {
        return pref.getBoolean(KEY_IS_LOGGEDIN,false);
    }


    public void setDealerId(int dealerid)
    {
        editor.putInt(KEY_DEALER_ID,dealerid);
        // commit changes
        editor.commit();
        Log.d(TAG, "Dealer Id");
    }

    public int getDealerId()
    {
        return pref.getInt(KEY_DEALER_ID,0);
    }

    public void setName(String name)
    {
        editor.putString(KEY_NAME,name);
        // commit changes
        editor.commit();
        Log.d(TAG, "User Name");
    }

    public String getName()
    {
        return pref.getString(KEY_NAME,null);
    }

    public void setCompanyEnglishName(String cnameenglish)
    {
        editor.putString(KEY_COMPANY_NAME_ENGLISH,cnameenglish);
        // commit changes
        editor.commit();
        Log.d(TAG, "Company Name English");
    }

    public String getCompanyEnglishName()
    {
        return pref.getString(KEY_COMPANY_NAME_ENGLISH,null);
    }

    public void setCompanyArabicName(String cnamearabic)
    {
        editor.putString(KEY_COMPANY_NAME_ARABIC,cnamearabic);
        // commit changes
        editor.commit();
        Log.d(TAG, "Company Name Arabic");
    }

    public String getCompanyArabicName()
    {
        return pref.getString(KEY_COMPANY_NAME_ARABIC,null);
    }


    public void setEmail(String email)
    {
        editor.putString(KEY_EMAIL,email);
        // commit changes
        editor.commit();
        Log.d(TAG, "Email");
    }

    public String getEmail()
    {
        return pref.getString(KEY_EMAIL,null);
    }

    public void setPhone(String phone)
    {
        editor.putString(KEY_PHONE, phone);
        // commit changes
        editor.commit();
        Log.d(TAG, "User Phone");
    }

    public String getPhone()
    {
        return pref.getString(KEY_PHONE,null);
    }

    public void setWhatsapp(String whatsapp)
    {
        editor.putString(KEY_WHATSAPP, whatsapp);
        // commit changes
        editor.commit();
        Log.d(TAG, "User Whatsapp");
    }

    public String getWhatsapp()
    {
        return pref.getString(KEY_WHATSAPP,null);
    }

    public void setAddress(String address)
    {
        editor.putString(KEY_ADDRESS,address);
        // commit changes
        editor.commit();
        Log.d(TAG, "Company Address");
    }

    public String getAddress()
    {
        return pref.getString(KEY_ADDRESS,null);
    }

    public void setDescription(String description)
    {
        editor.putString(KEY_DESCRIPTION, description);
        // commit changes
        editor.commit();
        Log.d(TAG, "Company Description");
    }

    public String getDescription()
    {
        return pref.getString(KEY_DESCRIPTION,null);
    }

    public void setFirebaseNotification(String notification)
    {
        editor.putString(KEY_FIREBASE_NOTIFICATION, notification);
        // commit changes
        editor.commit();
        Log.d(TAG, "Firebase Notification");
    }

    public String getFirebaseNotification()
    {
        return pref.getString(KEY_FIREBASE_NOTIFICATION,null);
    }

    public void setImages(String imagelist)
    {
        editor.putString(KEY_SHOP_IMAGES, imagelist);
        // commit changes
        editor.commit();
        Log.d(TAG, "Company Images");
    }

    public String getImages()
    {
        return pref.getString(KEY_SHOP_IMAGES,null);
    }

    public void clear()
    {
        //editor.remove("name");  //use this to remove specific key value
        //use clear to remove all the value from the shared preferences
        editor.clear();
        editor.commit();
    }

}
