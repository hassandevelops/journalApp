package com.bug2feature.journalApp.Controller;

import com.bug2feature.journalApp.Entity.JournalEntry;
import com.bug2feature.journalApp.Entity.User;
import com.bug2feature.journalApp.Service.UserService;
import com.bug2feature.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    AppCache appCache;

    @GetMapping("/all-users")
    ResponseEntity<?> getAllUsers(){
       List<User> all = userService.getAll();

       if(all!=null && !all.isEmpty()){
           return new ResponseEntity<>(all,HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-admin")
    ResponseEntity<?> createAdmin(@RequestBody User user){
            userService.saveAdmin(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
