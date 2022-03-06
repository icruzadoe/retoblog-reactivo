package com.bootcamp.retoreactive.repositories;

import com.bootcamp.retoreactive.entities.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {

    Mono<Post> findByBlogId(String blogId);
}
