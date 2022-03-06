package com.bootcamp.retoreactive.handlers;

import com.bootcamp.retoreactive.entities.User;
import com.bootcamp.retoreactive.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class UserHandler {

    @Autowired
    private UserService userService;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(user -> this.userService.save(user))
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), User.class));
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ok().contentType(APPLICATION_JSON)
                .body(userService.findAll(), User.class);
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("id");
        if (!isValidId(userId)) return badRequest().build();

        return this.userService.delete(serverRequest.pathVariable("id"))
                .then(ServerResponse.ok().build());
    }

    public boolean isValidId(String id) { return id != null;}
}
