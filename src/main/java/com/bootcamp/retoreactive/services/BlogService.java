package com.bootcamp.retoreactive.services;

import com.bootcamp.retoreactive.entities.Blog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlogService {
    Mono<Blog> findById(String id);
    Flux<Blog> findAll();
    Flux<Blog> findAllAuthorId(String authorId);
    Mono<Blog> save(Blog blog);
    Mono<Void> delete(String id);
}
