package com.peswa.leavemanager.repository;

import com.peswa.leavemanager.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(long id);

    Boolean existsByUsername(String username);

}
