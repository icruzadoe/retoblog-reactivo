package com.bootcamp.retoreactive.services.impl;

import com.bootcamp.retoreactive.core.exception.AuthorExistsException;
import com.bootcamp.retoreactive.core.exception.AuthorNotFoundException;
import com.bootcamp.retoreactive.entities.Author;
import com.bootcamp.retoreactive.repositories.AuthorRepository;
import com.bootcamp.retoreactive.repositories.UserRepository;
import com.bootcamp.retoreactive.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<Author> findById(String id) {
        return this.authorRepository.findById(id);
    }

    @Override
    public Flux<Author> findByUserId(String userId) { return null; }


    @Override
    public Flux<Author> findByName(String name) {
        return this.authorRepository.findByName(name);
    }

    @Override
    public Flux<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Mono<Author> save(Author author) {
        return this.authorRepository.save(author);
    }

    @Override
    public Mono<Author> saveWithValidation(Author author) {

//        return this.authorRepository.existsByEmail(author.getEmail())
//                .flatMap(exists->
//                        {
//                            return exists ? Mono.empty():this.authorRepository.save(author);
//                        });

        return this.authorRepository.existsByUserId(author.getUserId())
                .doOnNext(b->{
                    System.out.println("doOnNext author = " + b);
                })
                .flatMap(exists->
                {
                    return !exists ? this.authorRepository.save(author): Mono.error(new AuthorExistsException("Author ya tiene usuario"));
                });

    }

    @Override
    public Mono<Void> delete(String id) {

        return this.authorRepository.findById(id)
            .switchIfEmpty(Mono.error(new AuthorNotFoundException("Author no encontrado")))
            .flatMap(author-> {
                return this.authorRepository.delete(author);
            });



//        return this.authorRepository.findById(id)
//                .flatMap(author-> this.authorRepository.delete(author));


//        return this.authorRepository.deleteById(id);

    }

}
