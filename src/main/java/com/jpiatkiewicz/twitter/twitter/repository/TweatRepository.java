package com.jpiatkiewicz.twitter.twitter.repository;

import com.jpiatkiewicz.twitter.twitter.entity.Tweat;
import com.jpiatkiewicz.twitter.twitter.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweatRepository extends CrudRepository<Tweat, Long> {
    List<Tweat> findAllByOwner_NameInOrderByCreatedAtDesc(List<String> userNames);
    List<Tweat> findAllByOwner_NameEqualsOrderByCreatedAtDesc(String ownerName);
}
