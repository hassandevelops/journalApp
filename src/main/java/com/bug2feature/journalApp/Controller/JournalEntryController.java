package com.bug2feature.journalApp.Controller;

import com.bug2feature.journalApp.Entity.JournalEntry;
import com.bug2feature.journalApp.Entity.User;
import com.bug2feature.journalApp.Service.JournalEntryService;
import com.bug2feature.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;
    @Autowired
    UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUserName(username);
      List<JournalEntry> All = user.getJournalEntries();

      if(All!=null && !All.isEmpty()){
          return new ResponseEntity<>(All, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

       User user = userService.findByUserName(username);
       List<JournalEntry> collect = user.getJournalEntries().stream().filter(e->e.getId().equals(myId)).collect(Collectors.toList());

       if (!collect.isEmpty()){
           Optional<JournalEntry> journalEntry =  journalEntryService.getById(myId);

           if(journalEntry.isPresent()){
               return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
           }
       }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/add")
   public ResponseEntity<?> addJournal(@RequestBody JournalEntry myEntry){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
         journalEntryService.saveEntry(myEntry, username);
         Optional<JournalEntry> entry = journalEntryService.getById(myEntry.getId());
         if(entry.isPresent()){
             return new ResponseEntity<>(entry.get(),HttpStatus.CREATED);
         }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> deleteEntry(@PathVariable ObjectId myId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean removed = journalEntryService.deleteEntry(myId, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(e->e.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            JournalEntry old = journalEntryService.getById(myId).orElse(null);

            if(old!=null){
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);

                return new ResponseEntity<>(old, HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
