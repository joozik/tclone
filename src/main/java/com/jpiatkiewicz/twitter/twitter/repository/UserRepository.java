package com.jpiatkiewicz.twitter.twitter.repository;

import com.jpiatkiewicz.twitter.twitter.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByNameEquals(String name);
}
