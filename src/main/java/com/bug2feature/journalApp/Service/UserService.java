package com.bug2feature.journalApp.Service;

import com.bug2feature.journalApp.Entity.User;
import com.bug2feature.journalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public List<User> getAll(){
       return userRepository.findAll();
    }
    public User saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        return userRepository.save(user);
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }
    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public User saveAdmin(User  user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        return userRepository.save(user);
    }
}
