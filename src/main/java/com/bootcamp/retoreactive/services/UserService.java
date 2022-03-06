package com.bootcamp.retoreactive.services;

import com.bootcamp.retoreactive.entities.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> save(User user);
    Flux<User> findAll();
    Mono<Void> delete(String id);
}
