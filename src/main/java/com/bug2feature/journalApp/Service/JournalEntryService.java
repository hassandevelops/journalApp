package com.bug2feature.journalApp.Service;

import com.bug2feature.journalApp.Entity.JournalEntry;
import com.bug2feature.journalApp.Entity.User;
import com.bug2feature.journalApp.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;
    @Autowired
    UserService userService;

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntry myEntry, String username){
        try {
            myEntry.setDate(LocalDateTime.now());
            User user = userService.findByUserName(username);
            JournalEntry saved = journalEntryRepository.save(myEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An error has occured",e);
        }

    }

    public void saveEntry(JournalEntry myEntry){
        journalEntryRepository.save(myEntry);
    }

    public Optional<JournalEntry> getById(ObjectId id){
       return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteEntry(ObjectId id, String username){
        boolean removed = false;
        try {
            User user = userService.findByUserName(username);
           removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);

            }

        }
        catch (Exception e){
            throw new RuntimeException("An error occured why deleting the entry", e);
        }
        return removed;
    }

//    public JournalEntry updateEntry(ObjectId id, JournalEntry newEntry){
//        JournalEntry old = journalEntryRepository.findById(id).orElse(null);
//
//        if(old!=null){
//            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
//            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
//        }
//        journalEntryRepository.save(old);
//       return old;
//    }
}
