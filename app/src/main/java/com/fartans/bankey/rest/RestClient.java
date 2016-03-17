package com.fartans.bankey.rest;

import android.content.Context;

import com.fartans.bankey.commons.BanKeySharedPreferences;
import com.fartans.bankey.commons.BankeyConstants;
import com.fartans.bankey.commons.Validator;
import com.fartans.bankey.rest.service.IAuthenticationService;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by hitesh on 14/03/16.
 * @author hitesh.sethiya
 *
 * This is a singleton.
 *
 * A participant id and participant access code will be provided to all the participants.
 * Using the participant id and parti cipant access code, the
 * participants can use Participant Authentication API which will return unique access token in
 * response to access all ICICI Bank & Group companies APIs. .
 * When accessing the all APIs, participants need to include their participant access code and the access token in the request.
 * Without an access token, the API requests will be denied. Participants need to securely store access tokens
 * on their server and should ensure that it does not get shared with anybody else. Access token will be valid for 60 days.
 * Incase token needs to be
 * changed, Participant Authentication API needs to be used again.
 * • All requests must be sent to over https. Calls made over plain HTTP will fail.
 * ￼Authentication to the API is via HTTP Basic Auth.
 */
public class RestClient {

    public static final String acceptHeader = "application/json";
    public static final String AUTHORIZATION = "authorization";
    public static final String ACCEPT_CONTENT = "Accept";
    public static final String CLIENT_ID = "pavan.gonagur92@gmail.com";
    public static final String ACCESS_CODE = "ICIC8357";

    private static RestClient restClient;

    private Retrofit retrofit;
    private Context mContext;

    private IAuthenticationService authenticationService;

    private RestClient(Context context) {
        mContext = context;
        OkHttpClient okHttpClient = new OkHttpClient();

        //Follow all redirects
        okHttpClient.setFollowRedirects(true);
        okHttpClient.setFollowSslRedirects(true);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        okHttpClient.interceptors().add(requestInterceptor);

        //TODO intercept response

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.interceptors().add(logging);

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BankeyConstants.CORPORATE_BANK_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        authenticationService = getRetrofit().create(IAuthenticationService.class);

    }

    Interceptor requestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder requestBuilder = chain.request().newBuilder();
            BanKeySharedPreferences bankeySharedPreference = BanKeySharedPreferences.getInstance(mContext);
            if(!Validator.isNullOrEmpty(bankeySharedPreference.getAuthToken())) {
                String authToken = bankeySharedPreference.getAuthToken().get(0).getToken();
                requestBuilder
                        .addHeader(AUTHORIZATION,authToken);
            }
            Request newRequest = requestBuilder
                    //Retrofit by default adds accept encoding gzip
                    //.addHeader(ACCEPT_ENCODING,"gzip, deflate")
                    .addHeader(ACCEPT_CONTENT, acceptHeader).build();
            return chain.proceed(newRequest);
        }
    };

    /**
     * RestClient is a singleton class,
     * creating the object to call an api everytime is a costly operation.
     * Hence this class should be a singleton.
     * @param context - app context
     * @return returns the only instance of the
     * @RestClient
     */
    public static synchronized RestClient getInstance(Context context) {
        if(restClient == null) {
            restClient = new RestClient(context);
        }
        return restClient;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Context getContext() {
        return mContext;
    }

    public IAuthenticationService getAuthenticationService() {
        return authenticationService;
    }
}
