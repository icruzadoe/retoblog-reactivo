package com.bootcamp.retoreactive.services;

import com.bootcamp.retoreactive.entities.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<Author> findById(String id);
    Flux<Author> findByUserId(String userId);
    Flux<Author> findByName(String name);
    Flux<Author> findAll();
    Mono<Author> save(Author author);
    Mono<Author> saveWithValidation(Author author);
    Mono<Void> delete(String id);
    
}
