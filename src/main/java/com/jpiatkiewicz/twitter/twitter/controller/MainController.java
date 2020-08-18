package com.jpiatkiewicz.twitter.twitter.controller;

import com.jpiatkiewicz.twitter.twitter.entity.Tweat;
import com.jpiatkiewicz.twitter.twitter.entity.TweatRequest;
import com.jpiatkiewicz.twitter.twitter.entity.User;
import com.jpiatkiewicz.twitter.twitter.entity.UserToFollowRequest;
import com.jpiatkiewicz.twitter.twitter.service.TweatService;
import com.jpiatkiewicz.twitter.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
public class MainController {
    private final UserService userService;
    private final TweatService tweatService;

    @Autowired
    public MainController(UserService userService,
                          TweatService tweatService) {
        this.userService = userService;
        this.tweatService = tweatService;
    }

    @PostMapping("/post")
    public Tweat postTweat(@RequestBody TweatRequest tweatRequest) {
        final User user = userService.findUserByName(tweatRequest.getUserName());
        if (Objects.nonNull(user)) {
           return tweatService.postTweat(user, tweatRequest.getMessage());
        } else {
            final User newUser = userService.createNewUser(tweatRequest.getUserName());
            return tweatService.postTweat(newUser, tweatRequest.getMessage());
        }
    }

    @PostMapping("/follow")
    public String followUser(@RequestBody UserToFollowRequest userToFollowRequest) {
        final User user = userService.findUserByName(userToFollowRequest.getUserName());
        if (Objects.nonNull(user)) {
            userService.addFollower(user, userToFollowRequest.getUserToFollow());
            return "User " + userToFollowRequest.getUserName()
                    + " is now following " + userToFollowRequest.getUserToFollow();
        }
        return "There is no such User - create account first";
    }

    @GetMapping("/wall")
    public List<Tweat> getWall(@RequestParam("userName") String userName) {
        final User user = userService.findUserByName(userName);
        if (Objects.nonNull(user)) {
            return tweatService.getWallTweats(userName);
        } else {
            return Collections.emptyList();
        }
    }

    @GetMapping("/timeline")
    public List<Tweat> getTimeline(@RequestParam("userName") String userName) {
        final User user = userService.findUserByName(userName);
        if (Objects.nonNull(user) && !user.getFollowing().isEmpty()) {
            return tweatService.getTimeLine(user.getFollowing());
        } else {
            return Collections.emptyList();
        }
    }

}
