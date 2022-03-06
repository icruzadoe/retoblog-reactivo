package com.bootcamp.retoreactive.repositories;

import com.bootcamp.retoreactive.entities.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {
}
