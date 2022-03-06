package com.bootcamp.retoreactive.services.impl;

import com.bootcamp.retoreactive.entities.User;
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
        return null;
    }
}
