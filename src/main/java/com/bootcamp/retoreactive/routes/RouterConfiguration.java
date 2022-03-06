package com.bootcamp.retoreactive.routes;

import com.bootcamp.retoreactive.handlers.AuthorHandler;
import com.bootcamp.retoreactive.handlers.BlogHandler;
import com.bootcamp.retoreactive.handlers.PostHandler;
import com.bootcamp.retoreactive.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> blogRoutes(BlogHandler blogHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/blogs"),
                RouterFunctions
                        .route(GET(""), blogHandler::findAll)
                        .andRoute(GET("/{id}"), blogHandler::findById)
                        .andRoute(GET("/find-authors/{authorId}"), blogHandler::findAllAuthorId)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), blogHandler::save)
//						.andRoute(PUT("/{id}").and(contentType(APPLICATION_JSON)), blogHandler::update)
                        .andRoute(DELETE("/{id}"), blogHandler::delete)
            );
    }

    @Bean
    public RouterFunction<ServerResponse> authorRoutes(AuthorHandler authorHandler){
        return RouterFunctions.nest(RequestPredicates.path("/authors"),
                RouterFunctions
                .route(GET(""), authorHandler::findAll)
//                .andRoute(GET("/query/{email}"), authorHandler::findByEmail)
                .andRoute(GET("/{id}"), authorHandler::findById)
                .andRoute(POST("").and(accept(APPLICATION_JSON)),authorHandler::save)
                .andRoute(DELETE("/{id}"), authorHandler::delete)
            );
    }

    @Bean
    public RouterFunction<ServerResponse> postRoutes(PostHandler postHandler){
        return RouterFunctions.nest(RequestPredicates.path("/posts"),
                RouterFunctions.route(GET(""), postHandler::findAll)
                .andRoute(POST("").and(accept(APPLICATION_JSON)),postHandler::save)
                        .andRoute(PUT("/delete-reaction/{id}"),postHandler::deleteReaction)
                );
    }

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler){
        return RouterFunctions.nest(RequestPredicates.path("/users"),
                RouterFunctions
                        .route(GET(""), userHandler::findAll)
                        .andRoute(POST("").and(accept(APPLICATION_JSON)),userHandler::save)
                        .andRoute(DELETE("/{id}"), userHandler::delete)
        );
    }

}
