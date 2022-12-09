package com.peswa.leavemanager.service.impl;

import com.peswa.leavemanager.model.DatabaseSequence;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import com.peswa.leavemanager.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {
   @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String name) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(name)),
                new Update().inc("sequence",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSequence() : 1;

    }
}
