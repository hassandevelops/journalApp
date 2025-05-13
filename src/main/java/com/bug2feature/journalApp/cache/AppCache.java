package com.bug2feature.journalApp.cache;

import com.bug2feature.journalApp.Entity.ConfigJournalEntity;
import com.bug2feature.journalApp.Repository.ConfigJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class AppCache {

    public enum keys{
        weather_api;
    }
    @Autowired
    ConfigJournalRepository configJournalRepository;

    public Map<String, String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalEntity> all = configJournalRepository.findAll();
        for(ConfigJournalEntity configJournalEntity : all){
            appCache.put(configJournalEntity.getKey(), configJournalEntity.getValue());
        }
    }
}
