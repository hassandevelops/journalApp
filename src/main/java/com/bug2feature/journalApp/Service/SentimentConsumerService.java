package com.bug2feature.journalApp.Service;

import com.bug2feature.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class SentimentConsumerService {
    @Autowired
    EmailService emailService;

    @KafkaListener(topics="weekly-sentiment", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData){
        sendEmail(sentimentData);
    }

    private void sendEmail(SentimentData sentimentData) {
        emailService.sendMail(sentimentData.getEmail(),"Sentiment for previous week", sentimentData.getSentiment());
    }


}
