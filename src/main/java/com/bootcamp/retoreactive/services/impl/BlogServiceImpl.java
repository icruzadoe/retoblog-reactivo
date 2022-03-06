package com.bootcamp.retoreactive.services.impl;

import com.bootcamp.retoreactive.core.exception.AuthorExistsException;
import com.bootcamp.retoreactive.core.exception.BlogAgeException;
import com.bootcamp.retoreactive.core.exception.BlogMaxException;
import com.bootcamp.retoreactive.entities.Blog;
import com.bootcamp.retoreactive.repositories.BlogRepository;
import com.bootcamp.retoreactive.services.BlogService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Mono<Blog> findById(String id) {
        return  this.blogRepository.findById(id);
    }

    @Override
    public Flux<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Flux<Blog> findAllAuthorId(String authorId) {
        return this.blogRepository.findByAuthorId(authorId)
                .doOnNext(b->{
            System.out.println("doOnNext blog todos = " + b);
        });
    }

    @Override
    public Mono<Blog> save(Blog blog) {
        return blog.getAge() > 18 ? this.blogRepository.save(blog): Mono.error(new BlogAgeException("Author ya tiene usuario"));
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.blogRepository.findById(id)
                .doOnNext(b->{
                    System.out.println("doOnNext b = " + b);
                })
                .flatMap(blog-> this.blogRepository.delete(blog));

    }
}
