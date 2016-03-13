package com.fartans.bankey.commons;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hitesh on 14/03/16.
 * @author hitesh.sethiya
 *
 * A wrapper - Singleton on accessing the shared preference for the app.
 * PRIVATE Shared preferences for the app.
 */
public class BanKeySharedPreferences {

    private static final String BANKEY_SHARED_PREFERENCE_NAME = "bankey_shared_preference_name";

    private Context mContext;
    private static SharedPreferences mSharedPreferences;
    private static BanKeySharedPreferences mBanKeySharedPreferences;

    private BanKeySharedPreferences(Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(BANKEY_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }


    /**
     * Synchronized to avoid multiple initializations by 2 different threads.
     * @mBanKeySharedPreferences static instance, must be initialized by one thread and used by all.
     * @param context - app context to grab the shared preferences
     * @return
     */
    public static synchronized BanKeySharedPreferences getInstance(Context context) {
        if(mBanKeySharedPreferences == null) {
            mBanKeySharedPreferences = new BanKeySharedPreferences(context);
        }
        return mBanKeySharedPreferences;
    }

    /**
     * Default value will be null.
     * @param key - NOT NULL, key from which the string has to be fetched
     * @return
     */
    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    /**
     * Default value will be 0.
     * @param key NOT NULL, key from which the long has to be fetched
     * @return
     */
    public Long getLong(String key) {
        return mSharedPreferences.getLong(key, 0L);
    }

    /**
     * clear all the key value pairs in the shared preferences.
     */
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}
