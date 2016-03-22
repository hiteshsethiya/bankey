package com.fartans.bankey.commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fartans.bankey.model.AuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by hitesh on 14/03/16.
 * @author hitesh.sethiya
 *
 * A wrapper - Singleton on accessing the shared preference for the app.
 * PRIVATE Shared preferences for the app.
 */
public class BanKeySharedPreferences {

    private static final String TAG = BanKeySharedPreferences.class.getName();
    private static final String BANKEY_SHARED_PREFERENCE_NAME = "bankey_shared_preference_name";
    private static final String AUTH_TOKEN_KEY = "auth_token";
    private static final String LAST_AUTH_GENERATED_AT_KEY = "last_auth_generated_at_key";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static List<AuthToken> mAuthTokenList;
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

    public void saveAuthToken(List<AuthToken> authTokenList) {
        if(authTokenList == null) {
            throw new NullPointerException("Auth token cannot be null for saving onto the shared preferences!");
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        try {
            editor.putString(AUTH_TOKEN_KEY,OBJECT_MAPPER.writeValueAsString(authTokenList));
            editor.putLong(LAST_AUTH_GENERATED_AT_KEY,System.currentTimeMillis());
        } catch (JsonProcessingException e) {
            Log.e(TAG,"Exception while saving authtoken {}",e);
        }
        editor.apply();
    }

    /**
     *
     * @return auth token if available in memory, else from the shared preferences or null if not present in both
     */
    public List<AuthToken> getAuthToken() {
        if(mAuthTokenList == null) {
            String authTokenString = getString(AUTH_TOKEN_KEY);
            try {
                if(authTokenString != null) {
                    mAuthTokenList = OBJECT_MAPPER.readValue(authTokenString,OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, AuthToken.class));
                }
            } catch (IOException e) {
                Log.e(TAG,"EXCEPTION while getting authtoken, e => {}",e);
            }
        }
        return mAuthTokenList;
    }

    public Long getLastAuthGeneratedTime() {
        return mSharedPreferences.getLong(LAST_AUTH_GENERATED_AT_KEY,0L);
    }
}
