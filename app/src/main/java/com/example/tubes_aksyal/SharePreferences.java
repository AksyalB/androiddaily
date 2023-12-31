package com.example.tubes_aksyal;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferences {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "sessionPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_SET_IMAGE = "setimage";


    public SharePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setLoginSession(boolean isLoggedIn, String username) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
    public void setUsername(String username){
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    // Metode untuk menyimpan path gambar ke SharedPreferences
    public void setImagePath(String imagePath) {
        editor.putString(KEY_SET_IMAGE, imagePath);
        editor.apply();
    }

    // Metode untuk mendapatkan path gambar dari SharedPreferences
    public String getImagePath() {
        return sharedPreferences.getString(KEY_SET_IMAGE, "");
    }


}
