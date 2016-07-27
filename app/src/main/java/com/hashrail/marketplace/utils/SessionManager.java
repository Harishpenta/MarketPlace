package com.hashrail.marketplace.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

public class SessionManager {

    // Shared preferences file name
    public static final String PREF_NAME = "Seemora";
    public static final String KEY_IMAGE_PATH = "path";
    // Shared Preferences
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setImagePath(String path) {


        editor.putString(KEY_IMAGE_PATH, path);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name

        user.put(KEY_IMAGE_PATH, pref.getString(KEY_IMAGE_PATH, null));
        // user email id
        // return user
        return user;
    }


}
