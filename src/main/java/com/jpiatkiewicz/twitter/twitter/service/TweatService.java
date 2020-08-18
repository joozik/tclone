package com.jpiatkiewicz.twitter.twitter.service;

import com.jpiatkiewicz.twitter.twitter.entity.Tweat;
import com.jpiatkiewicz.twitter.twitter.entity.User;
import com.jpiatkiewicz.twitter.twitter.repository.TweatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweatService {
    private final TweatRepository tweatRepository;

    @Autowired
    public TweatService(TweatRepository tweatRepository) {
        this.tweatRepository = tweatRepository;
    }

    public List<Tweat> getWallTweats(String ownerName) {
        return tweatRepository.findAllByOwner_NameEqualsOrderByCreatedAtDesc(ownerName);
    }

    public Tweat postTweat(User owner, String message) {
        return tweatRepository.save(new Tweat(message, owner));
    }

    public List<Tweat> getTimeLine(List<String> following) {
        return tweatRepository.findAllByOwner_NameInOrderByCreatedAtDesc(following);

    }
}
