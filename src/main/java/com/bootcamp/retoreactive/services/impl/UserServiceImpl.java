package com.bootcamp.retoreactive.services.impl;

import com.bootcamp.retoreactive.entities.Reaction;
import com.bootcamp.retoreactive.entities.User;
import com.bootcamp.retoreactive.repositories.AuthorRepository;
import com.bootcamp.retoreactive.repositories.BlogRepository;
import com.bootcamp.retoreactive.repositories.PostRepository;
import com.bootcamp.retoreactive.repositories.UserRepository;
import com.bootcamp.retoreactive.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public Mono<User> save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Flux<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Mono<Void> delete(String id) {

        this.authorRepository.findByUserId(id)
                .doOnNext(b->{
                    System.out.println("doOnNext author = " + b);
                })
                .flatMap(author -> {
                    this.blogRepository.findByAuthorId(author.getId())
                            .flatMap(blog -> {
                                this.postRepository.findByBlogId(blog.getId())
                                        .flatMap(post -> {
                                            return this.postRepository.delete(post);
                                        });
                                return this.blogRepository.delete(blog);
                            });
                    return this.authorRepository.delete(author);
                });


        return this.userRepository.deleteById(id);
    }
}
