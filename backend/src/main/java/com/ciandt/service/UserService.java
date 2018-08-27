package com.ciandt.service;

import com.ciandt.model.User;

import java.util.List;

public interface UserService {

    User getUser(Integer userId);

    void createUser(User user);

    List<User> listUsers();

    void deleteUser(User user);

}
