package com.bootcamp.retoreactive.repositories;

import com.bootcamp.retoreactive.entities.Blog;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface BlogRepository extends ReactiveMongoRepository<Blog, String> {
    @Query("select count(a) from Blog b  where b.authorId =  ?1")
    Mono<Long> countByAuthor(String authorId);

    @Query(value = "{authorId : ?0}", count = true)
    Mono<Long> findBlogCountByAuthor(String authorId);

    Flux<Blog> findByAuthorId(String authorId);

    Mono<Void> deleteByAuthorId(String authorId);
}