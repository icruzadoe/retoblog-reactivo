package com.bootcamp.retoreactive.repositories;

import com.bootcamp.retoreactive.entities.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User,String> {

}
