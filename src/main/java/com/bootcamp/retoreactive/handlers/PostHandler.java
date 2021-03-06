package com.bootcamp.retoreactive.handlers;

import com.bootcamp.retoreactive.entities.Blog;
import com.bootcamp.retoreactive.entities.Post;
import com.bootcamp.retoreactive.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class PostHandler {

    @Autowired
    private PostService postService;

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Post.class)
                .flatMap(post -> this.postService.save(post))
                .flatMap(post -> ServerResponse.ok().body(Mono.just(post), Post.class));
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ok().contentType(APPLICATION_JSON)
                .body(postService.findAll(), Post.class);
    }

    public Mono<ServerResponse> deleteReaction(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("id");
        if (!isValidId(userId)) return badRequest().build();

        return this.postService.deleteReaction(serverRequest.pathVariable("id"))
                .then(ServerResponse.noContent().build());
    }

    public boolean isValidId(String id) { return id != null;}
}
