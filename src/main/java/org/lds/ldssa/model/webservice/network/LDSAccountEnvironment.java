package org.lds.ldssa.model.webservice.network;

public enum LDSAccountEnvironment {
    PROD("https://signin.lds.org/login.html", "wh=www.lds.org wu=/header wo=1 rh=http://www.lds.org ru=/header"),
    BETA("https://signin.lds.org/login.html", "wh=www.lds.org wu=/header wo=1 rh=http://www.lds.org ru=/header"),
    UAT("https://signin-uat.lds.org/login.html", "wh=uat.lds.org wu=/header wo=1 rh=http://uat.lds.org ru=/header"),
    DEV("https://signin-dev.lds.org/login.html", "wh=www.lds.org wu=/header wo=1 rh=http://www.lds.org ru=/header");

    private final String authUrl;
    private final String authCookies;

    LDSAccountEnvironment(String authUrl, String authCookies) {
        this.authUrl = authUrl;
        this.authCookies = authCookies;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public String getAuthCookies() {
        return authCookies;
    }
}
