package com.project.agunay.application.repository;

import com.project.agunay.adapter.firebase.Callback;
import com.project.agunay.adapter.firebase.ErrorCallback;
import com.project.agunay.adapter.firebase.Result;
import com.project.agunay.adapter.firebase.SuccessCallback;
import com.project.agunay.domain.User;

import org.jetbrains.annotations.NotNull;

public interface UserRepository {

    void createUser(User user, SuccessCallback<User> callback, ErrorCallback callError);

    void login(String email, String password, SuccessCallback<User> callback, ErrorCallback callError);

    void updateUser(User user, SuccessCallback<User> callback, ErrorCallback callError);

    void getUserByUsername(String username, SuccessCallback<User> callback);

    void getUserByEmail(String email,SuccessCallback<User> callback, ErrorCallback callError);

    void getUser(String id,SuccessCallback<User> callback, ErrorCallback callError);
}
