package org.lds.ldssa.model.prefs.model;

public enum AnnotationServerType {
    PROD("https://www.lds.org", "androidGospelLibrary", "b3f68440-5db8-4992-ba9f-f57cad623b41"),
    BETA("https://beta.lds.org", "androidGospelLibrary", "openTheScripturesToMe");

    private final String baseUrl;
    private final String username;
    private final String password;

    AnnotationServerType(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}