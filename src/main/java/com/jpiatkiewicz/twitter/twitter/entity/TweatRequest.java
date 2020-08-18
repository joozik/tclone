package com.jpiatkiewicz.twitter.twitter.entity;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class TweatRequest {

    @NotBlank
    private String userName;
    @NotBlank
    @Max(140)
    @Min(1)
    private String message;

    public TweatRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
