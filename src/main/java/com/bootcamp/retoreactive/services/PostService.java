package com.bootcamp.retoreactive.services;

import com.bootcamp.retoreactive.entities.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
    Mono<Post> save(Post post);
    Flux<Post> findAll();
}
