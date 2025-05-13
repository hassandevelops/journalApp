package com.bug2feature.journalApp.Entity;

import com.bug2feature.journalApp.enums.Sentiment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "journal_entries")
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String Content;
    private LocalDateTime date;
    private Sentiment sentiment;


}
