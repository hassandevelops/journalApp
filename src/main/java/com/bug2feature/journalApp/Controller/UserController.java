package com.bug2feature.journalApp.Controller;

import com.bug2feature.journalApp.Entity.User;
import com.bug2feature.journalApp.Repository.UserRepository;
import com.bug2feature.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;




    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User userInDb = userService.findByUserName(username);

        if(userInDb!=null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(userInDb, HttpStatus.NOT_FOUND);

    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUserById(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(auth.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
