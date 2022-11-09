package com.docto.protechdoctolib.logging;

public class LoggingRequest {

    private final String email;
    private final String password;

    public LoggingRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}
