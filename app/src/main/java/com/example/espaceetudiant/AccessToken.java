package com.example.espaceetudiant;

public class AccessToken {
    private String access_token;
    private String expires_in;
    private String token_type;
    private String scope;
    private String refresh_token;


    public AccessToken(String access_token, String expires_in, String token_type, String scope, String refresh_token) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.token_type = token_type;
        this.scope = scope;
        this.refresh_token = refresh_token;
    }

    public String getAccessToken() {
        return access_token;
    }

    public String getTokenType() {
        // OAuth requires uppercase Authorization HTTP header value for token type
        if (! Character.isUpperCase(token_type.charAt(0))) {
            token_type =
                    Character
                            .toString(token_type.charAt(0))
                            .toUpperCase() + token_type.substring(1);
        }

        return token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
