package com.fartans.bankey.rest;

import android.content.Context;

import com.fartans.bankey.commons.BanKeySharedPreferences;
import com.fartans.bankey.commons.BankeyConstants;
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
 */
public class RestClient {

    public static final String acceptHeader = "application/json";
    public static final String AUTHORIZATION = "authorization";
    public static final String ACCEPT_CONTENT = "Accept";

    private static RestClient restClient;
    private Retrofit retrofit;
    private Context mContext;

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
                .baseUrl(BankeyConstants.BASE_URL_RETROFIT)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    Interceptor requestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder requestBuilder = chain.request().newBuilder();
            BanKeySharedPreferences bankeySharedPreference = BanKeySharedPreferences.getInstance(mContext);
            final String authToken = bankeySharedPreference.getAuthToken();
            if(authToken != null) {
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
}
