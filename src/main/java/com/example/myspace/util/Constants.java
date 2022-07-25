package com.example.myspace.util;

public class Constants {

    // Authorization key in the Header
    public static final String HEADER_AUTHORIZACION_KEY  = "Authorization";

    // Token prefix
    public static final String TOKEN_BEARER_PREFIX  = "Bearer ";

    // Token expiration time (864_000_000L = One day)
    public static final long EXPIRATION_TIME = 864_000_000L;

    // Key suitable for signing the JWT using the HMAC-SHA-256 algorithm
    public static final String SIGNING_KEY = "secre"; // 1234

    // URL for login
    public static final String LOGIN_URL = "/login";

    // URL for register in the app
    public static final String REGISTER_URL = "/register";

    // Constant used for adding the userGroup to the token
    public static final String AUTHORITIES_USER_GROUP = "userGroup";

}
