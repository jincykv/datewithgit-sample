package com.business.supermarket;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedpreference {


    private String DeviceToken="devicetoken_key";
    private String PREFERENCE_NAME;
    private Context context;
    private SharedPreferences sharedPreferences;
    public static String useridKey = "userid_key";
    public static String shopkey="Shopname";


    public AppSharedpreference(Context mcontext) {
        this.context = mcontext;
        this.PREFERENCE_NAME = "AppSharedpreference";
        sharedPreferences = mcontext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }


    //data storing to sharedpref
    public Boolean saveinprefernceString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public Boolean saveinprefernceInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }



    public String UserPhoNumber(){

        String ShopName=sharedPreferences.getString(shopkey,"");
        return ShopName;
    }



        public Boolean ClearUserPhoNumber() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(useridKey);
        return editor.commit();
    }






}
