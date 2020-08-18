package com.jpiatkiewicz.twitter.twitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpiatkiewicz.twitter.twitter.entity.Tweat;
import com.jpiatkiewicz.twitter.twitter.entity.TweatRequest;
import com.jpiatkiewicz.twitter.twitter.entity.User;
import com.jpiatkiewicz.twitter.twitter.entity.UserToFollowRequest;
import com.jpiatkiewicz.twitter.twitter.service.TweatService;
import com.jpiatkiewicz.twitter.twitter.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class MainControllerTest {
    private static final String TEST_MESSAGE = "test message";
    private static final String USER = "someUser";
    private static final ObjectMapper om = new ObjectMapper();

    @Mock
    private UserService userService;
    @Mock
    private TweatService tweatService;

    @InjectMocks
    private MainController mainController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    void postTweatWhenUserExist() throws Exception {
        final TweatRequest tweatRequest = new TweatRequest();
        tweatRequest.setMessage(TEST_MESSAGE);
        tweatRequest.setUserName(USER);
        final User someUser = new User(USER);
        when(userService.findUserByName(anyString()))
                .thenReturn(someUser);
        final Tweat tweat = new Tweat(TEST_MESSAGE, someUser);
        when(tweatService.postTweat(any(User.class), anyString()))
                .thenReturn(tweat);

        mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(tweatRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(TEST_MESSAGE));

        verify(userService).findUserByName(anyString());
        verifyNoMoreInteractions(userService);
        verify(tweatService).postTweat(any(User.class), anyString());
        verifyNoMoreInteractions(tweatService);
    }


    @Test
    void postTweatWhenUserDoesNotExist() throws Exception {
        final TweatRequest tweatRequest = new TweatRequest();
        tweatRequest.setMessage(TEST_MESSAGE);
        tweatRequest.setUserName(USER);
        final User someUser = new User(USER);
        when(userService.findUserByName(anyString()))
                .thenReturn(null);
        when(userService.createNewUser(anyString()))
                .thenReturn(someUser);
        final Tweat tweat = new Tweat(TEST_MESSAGE, someUser);
        when(tweatService.postTweat(any(User.class), anyString()))
                .thenReturn(tweat);

        mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(tweatRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(TEST_MESSAGE));

        verify(userService).findUserByName(anyString());
        verify(userService).createNewUser(anyString());
        verifyNoMoreInteractions(userService);
        verify(tweatService).postTweat(any(User.class), anyString());
        verifyNoMoreInteractions(tweatService);
    }

    @Test
    void followUserWhenUserDoesNotExist() throws Exception {
        final UserToFollowRequest userToFollowRequest = new UserToFollowRequest();
        userToFollowRequest.setUserName(USER);
        userToFollowRequest.setUserToFollow("userToFollowName");
        when(userService.findUserByName(anyString()))
                .thenReturn(null);

        mockMvc.perform(post("/follow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userToFollowRequest)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("There is no such User - create account first"));

        verify(userService).findUserByName(anyString());
        verifyNoMoreInteractions(userService);
        verifyNoInteractions(tweatService);
    }


    @Test
    void followUserWhenUserExist() throws Exception {
        final UserToFollowRequest userToFollowRequest = new UserToFollowRequest();
        userToFollowRequest.setUserName(USER);
        userToFollowRequest.setUserToFollow("userToFollowName");
        final User someUser = new User(USER);
        when(userService.findUserByName(anyString()))
                .thenReturn(someUser);
        doNothing().when(userService).addFollower(any(User.class), anyString());

        mockMvc.perform(post("/follow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userToFollowRequest)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("User someUser is now following userToFollowName"));

        verify(userService).findUserByName(anyString());
        verify(userService).addFollower(any(User.class), anyString());
        verifyNoMoreInteractions(userService);
        verifyNoInteractions(tweatService);
    }

    @Test
    void getWallWhenUserDoesNotExist() throws Exception {
        when(userService.findUserByName(anyString())).thenReturn(null);

        mockMvc.perform(get("/wall")
                .param("userName", "notExistingName"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        verify(userService).findUserByName(anyString());
        verifyNoMoreInteractions(userService);
        verifyNoInteractions(tweatService);
    }

    @Test
    void getWallWhenUserDoesExist() throws Exception {
        final User someUser = new User(USER);
        when(userService.findUserByName(anyString()))
                .thenReturn(someUser);
        final Tweat tweat = new Tweat(TEST_MESSAGE, someUser);
        when(tweatService.getWallTweats(anyString()))
                .thenReturn(Collections.singletonList(tweat));

        mockMvc.perform(get("/wall")
                .param("userName", USER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message")
                        .value(TEST_MESSAGE));

        verify(userService).findUserByName(anyString());
        verifyNoMoreInteractions(userService);
        verify(tweatService).getWallTweats(anyString());
        verifyNoMoreInteractions(tweatService);
    }

    @Test
    void getTimelineWhenUserExistButNonFollowing() throws Exception {
        final User someUser = new User(USER);
        when(userService.findUserByName(anyString()))
                .thenReturn(someUser);
        final Tweat tweat = new Tweat(TEST_MESSAGE, someUser);
        when(tweatService.getTimeLine(anyList()))
                .thenReturn(Collections.singletonList(tweat));

        mockMvc.perform(get("/timeline")
                .param("userName", USER))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        verify(userService).findUserByName(anyString());
        verifyNoMoreInteractions(userService);
        verifyNoInteractions(tweatService);
    }


    @Test
    void getTimelineWhenUserDoesNotExist() throws Exception {
        when(userService.findUserByName(anyString())).thenReturn(null);

        mockMvc.perform(get("/timeline")
                .param("userName", "notExistingName"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        verify(userService).findUserByName(anyString());
        verifyNoMoreInteractions(userService);
        verifyNoInteractions(tweatService);
    }
}