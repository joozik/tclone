package com.jpiatkiewicz.twitter.twitter.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String name;
    @ElementCollection
    private List<String> following;
    @OneToMany(mappedBy = "owner",
            orphanRemoval = true)
    private List<Tweat> tweatList;

    public User() {
        following = new ArrayList<>();
    }

    public User(String name) {
        following = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tweat> getTweatList() {
        return tweatList;
    }

    public void setTweatList(List<Tweat> tweatList) {
        this.tweatList = tweatList;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> followers) {
        this.following = followers;
    }
}
