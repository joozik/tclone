package com.jpiatkiewicz.twitter.twitter.service;

import com.jpiatkiewicz.twitter.twitter.entity.User;
import com.jpiatkiewicz.twitter.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByName(String userName) {
        return userRepository.findByNameEquals(userName);
    }

    public User createNewUser(String userName) {
        return userRepository.save(new User(userName));
    }

    public void addFollower(User user, String userToFollow) {
        final List<String> following = user.getFollowing();
        following.add(userToFollow);
        user.setFollowing(following);
        userRepository.save(user);
    }
}
