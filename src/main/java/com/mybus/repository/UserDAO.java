package com.mybus.repository;

import com.mybus.login.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends CrudRepository<User, String> {
    User findByEmail(String email);
    User findOneByUserName(String username);
}