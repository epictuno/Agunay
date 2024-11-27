package com.project.agunay.application.repository;

import com.project.agunay.domain.User;

public interface UserRepository {

    void createUser(User user, String password);

    User login(String email, String password);

    void updateUser(User user);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUser(String id);
}
