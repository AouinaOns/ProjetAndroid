package com.example.espaceetudiant;

public class Login {
    private String grant_type;
    private String scope;
    private String Client_id;
    private String Client_secret;
    private String username;
    private String password;

    public Login(String grant_type, String scope, String client_id, String client_secret, String username, String password) {
        this.grant_type = grant_type;
        this.scope = scope;
        this.Client_id = client_id;
        this.Client_secret = client_secret;
        this.username = username;
        this.password = password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClient_id() {
        return Client_id;
    }

    public void setClient_id(String client_id) {
        Client_id = client_id;
    }

    public String getClient_secret() {
        return Client_secret;
    }

    public void setClient_secret(String client_secret) {
        Client_secret = client_secret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
