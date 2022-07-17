package com.example.myspace.util;

// Returned Object when Login is realized
public class TokenResultUtil {

    private String tokenType = "Bearer";

    private String token;

    private String tokenResult;


    public TokenResultUtil() {

    }

    public TokenResultUtil(String token){

        this.token = token;
        this.tokenResult = tokenType.concat(" ").concat(token);
    }

    public TokenResultUtil(String token, boolean isFirstLogin){

        this.token = token;
        this.tokenResult = tokenType.concat(" ").concat(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenResult() {
        return tokenResult;
    }

    public void setTokenResult(String tokenResult) {
        this.tokenResult = tokenResult;
    }

}