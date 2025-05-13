package com.bug2feature.journalApp;

import com.bug2feature.journalApp.Repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.util.Assert;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    UserRepositoryImpl userRepositoryImpl;
    @Test
    public void testSaveUser(){
        Assertions.assertNotNull(userRepositoryImpl.getUsersForSA());
    }


}
