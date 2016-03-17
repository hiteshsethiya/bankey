package com.fartans.bankey.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hitesh on 16/03/16.
 */
public class AuthToken extends Model {

    @JsonProperty("token")
    private String token;

    public AuthToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
