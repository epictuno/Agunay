package com.project.agunay.application.repository;

import com.project.agunay.adapter.firebase.ErrorCallback;
import com.project.agunay.adapter.firebase.SuccessCallback;
import com.project.agunay.domain.User;

public interface UserRepository {

    void createUser(User user, SuccessCallback<User> callback, ErrorCallback callError);

    void login(String email, String password, SuccessCallback<User> callback, ErrorCallback callError);

    void updateUser(User user, SuccessCallback<User> callback, ErrorCallback callError);

    void getUserByUsername(String username, SuccessCallback<User> callback, ErrorCallback callError);

    void getUserByEmail(String email,SuccessCallback<User> callback, ErrorCallback callError);

    void getUser(String id,SuccessCallback<User> callback, ErrorCallback callError);
}
