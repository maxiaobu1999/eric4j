package com.eric.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *BearerAuthToken
 */
public class BearerAuthToken extends UsernamePasswordToken {
    private final String token;

    public BearerAuthToken(String token){
        this.token = token;
    }

    public BearerAuthToken(String username, char[] password, boolean rememberMe, String host, String token) {
        setRememberMe(rememberMe);
        if (null != host) {
            setHost(host);
        }
        if (null != password) {
            setPassword(password);
        }
        if (null != username) {
            setUsername(username);
        }
        this.token = token;
    }

    public BearerAuthToken(String username, String password, String host, String token) {
        this(username,password.toCharArray(),false,host,token);
    }
    public BearerAuthToken(String username, String password, String token) {
        this(username,password,null,token);
    }
    public BearerAuthToken(String host, String token) {
        this(null,null,host,token);
    }

    public String getPrincipal() {
        if(null==super.getPrincipal()){
            return null;
        }
        return (String) super.getPrincipal();
    }

    public String getCredentials() {
        if(null==super.getCredentials()){
            return null;
        }
        return (String) super.getCredentials();
    }

    public String getToken() {

        return this.token;
    }
}
