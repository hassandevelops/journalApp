package com.bug2feature.journalApp;

import com.bug2feature.journalApp.Service.WeatherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}
	@Bean
	public PlatformTransactionManager idk(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public WeatherService weatherService(){
		return new WeatherService();
	}


}
