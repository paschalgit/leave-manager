package com.peswa.leavemanager.repository;

import com.peswa.leavemanager.enums.RoleEnum;
import com.peswa.leavemanager.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Boolean> existsByName(RoleEnum name);

    Optional<Role> findByName(RoleEnum name);

    Optional<Role> findById(Long id);

    Optional<Role> deleteById(Long id);

}
