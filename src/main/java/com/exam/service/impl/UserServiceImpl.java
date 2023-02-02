package com.exam.service.impl;

import com.exam.models.User;
import com.exam.models.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    //creating user
    @Override
    public User createUser(User user, Set<UserRole> userRole) throws Exception {
        User local =this.userRepository.findByuserName(user.getUsername());
        if(local !=null)
        {
            System.out.println("user is already there");
            throw  new Exception("User already present");
        }else {
         for(UserRole ur:userRole){
             roleRepository.save(ur.getRole());
         }

         user.getUserRoles().addAll(userRole);
            local = this.userRepository.save(user);
        }
        return local;
    }


    //getting user by username
    @Override
    public User getUser(String username) {

        return this.userRepository.findByuserName(username);
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }
}
