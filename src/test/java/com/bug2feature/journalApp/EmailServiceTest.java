package com.bug2feature.journalApp;

import com.bug2feature.journalApp.Service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

//    @Test
//    void testSendEmail(){
//        emailService.sendMain("mahamzaka.123@gmail.com","This is Automated Email","Hi");
//    }
}
