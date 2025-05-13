package com.bug2feature.journalApp.scheduler;

import com.bug2feature.journalApp.Entity.JournalEntry;
import com.bug2feature.journalApp.Entity.User;
import com.bug2feature.journalApp.Repository.UserRepositoryImpl;
import com.bug2feature.journalApp.Service.EmailService;
import com.bug2feature.journalApp.enums.Sentiment;
import com.bug2feature.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    UserRepositoryImpl userRepositoryImpl;
    @Autowired
    EmailService emailService;
    @Autowired
    KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendEmail(){
        List<User> users = userRepositoryImpl.getUsersForSA();
        for (User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());

            HashMap<Sentiment, Integer> sentimentCount = new HashMap<>();

            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
                }
            }

                int max=0;
                Sentiment mostFrequentSentiment = null;

            for (Map.Entry<Sentiment, Integer> sentiment : sentimentCount.entrySet()){
                if(sentiment.getValue()>max){
                    max = sentiment.getValue();
                    mostFrequentSentiment = sentiment.getKey();
                }
            }
                if(mostFrequentSentiment!=null) {
                    SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days "+mostFrequentSentiment).build();
//                    emailService.sendMail("mahamzaka.123@gmail.com", "You sentiment analysis on the basis of las 7 days", mostFrequentSentiment.toString());
                    kafkaTemplate.send("weekly-sentiments",sentimentData.getEmail(),sentimentData);
                }


            }

            


        }
    }

