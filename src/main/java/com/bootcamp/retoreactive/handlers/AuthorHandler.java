package com.bootcamp.retoreactive.handlers;

import com.bootcamp.retoreactive.core.exception.AuthorExistsException;
import com.bootcamp.retoreactive.core.exception.BlogNotFoundException;
import com.bootcamp.retoreactive.entities.Author;
import com.bootcamp.retoreactive.entities.Blog;
import com.bootcamp.retoreactive.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;

@Component
public class AuthorHandler {

    @Autowired
    private AuthorService authorService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(authorService.findAll(), Author.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        var id = request.pathVariable("id");
//        return ServerResponse.ok()
//                .contentType(APPLICATION_JSON)
//                .body(authorService.findById(id), Author.class);

        return this.authorService.findById(id)
                .flatMap(a-> ServerResponse.ok().body(Mono.just(a), Author.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }




    public Mono<ServerResponse> save(ServerRequest request){

        var authorInput= request.bodyToMono(Author.class);



        return authorInput
                .flatMap(author-> this.authorService.saveWithValidation(author))
                .flatMap(a-> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(a), Author.class));
//                .switchIfEmpty(Mono.error(new AuthorExistsException("Author exists")));

    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String authorId = serverRequest.pathVariable("id");

        return this.authorService.delete(serverRequest.pathVariable("id"))
                .then(ServerResponse.ok().build());

//
//        return ServerResponse.ok()
//                .contentType(APPLICATION_JSON)
//                .body(authorService.delete(authorId),Author.class);

    }
}
