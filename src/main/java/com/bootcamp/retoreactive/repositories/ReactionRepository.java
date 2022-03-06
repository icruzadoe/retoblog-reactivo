package com.bootcamp.retoreactive.repositories;

import com.bootcamp.retoreactive.entities.Reaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactionRepository extends ReactiveMongoRepository<Reaction,String>{
}
