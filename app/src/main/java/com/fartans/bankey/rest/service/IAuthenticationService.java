package com.fartans.bankey.rest.service;

import com.fartans.bankey.model.AuthToken;

import org.json.JSONArray;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by hitesh on 16/03/16.
 * @author Hitesh Sethiya
 */
public interface IAuthenticationService {

    String AUTHENTICATOR_ENDPOINT = "corporate_banking/mybank/authenticate_client/";

    @GET(AUTHENTICATOR_ENDPOINT)
    Call<List<AuthToken>> getAuthToken(@Query("client_id") String clientId, @Query("password") String accessCode);

}
