package com.jpiatkiewicz.twitter.twitter.entity;

import javax.validation.constraints.NotBlank;

public class UserToFollowRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String userToFollow;

    public UserToFollowRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserToFollow() {
        return userToFollow;
    }

    public void setUserToFollow(String userToFollow) {
        this.userToFollow = userToFollow;
    }
}
