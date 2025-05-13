package com.bug2feature.journalApp;

import com.bug2feature.journalApp.scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTest {

    @Autowired
    UserScheduler userScheduler;

    @Test
    public void fetchUsersAndSendsSaMail(){
        userScheduler.fetchUsersAndSendEmail();
    }
}
