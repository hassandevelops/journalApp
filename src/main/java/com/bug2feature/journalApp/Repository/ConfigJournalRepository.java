package com.bug2feature.journalApp.Repository;

import com.bug2feature.journalApp.Entity.ConfigJournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalRepository extends MongoRepository<ConfigJournalEntity, ObjectId> {
}
