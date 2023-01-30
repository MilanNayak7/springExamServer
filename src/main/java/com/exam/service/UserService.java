package com.exam.service;

import com.exam.models.User;
import com.exam.models.UserRole;

import java.util.Set;

public interface UserService {
    public User createUser(User user, Set<UserRole> userRole) throws Exception;


    //get user by name
    public User getUser(String username);


    //delete user by id
    public void deleteUser(Long userId);
}
