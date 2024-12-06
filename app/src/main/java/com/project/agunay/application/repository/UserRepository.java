package com.project.agunay.application.repository;

import com.project.agunay.adapter.firebase.Callback;
import com.project.agunay.adapter.firebase.Result;
import com.project.agunay.domain.User;

import org.jetbrains.annotations.NotNull;

public interface UserRepository {

    void createUser(User user, Callback<User> callback);

    User login(String email, String password);

    void updateUser(User user);

    void getUserByUsername(String username, Callback<User> callback);

    User getUserByEmail(String email);

    User getUser(String id);

    void createUser(@NotNull User user, @NotNull Object any);
}
