package com.bootcamp.retoreactive.services.impl;

import com.bootcamp.retoreactive.core.exception.PostException;
import com.bootcamp.retoreactive.entities.Comment;
import com.bootcamp.retoreactive.entities.Post;
import com.bootcamp.retoreactive.entities.Reaction;
import com.bootcamp.retoreactive.repositories.BlogRepository;
import com.bootcamp.retoreactive.repositories.PostRepository;
import com.bootcamp.retoreactive.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Mono<Post> save(Post post) {
        return  this.blogRepository.findById(post.getBlogId()).doOnNext(b->{
                    System.out.println("doOnNext author = " + b);
                })
                .flatMap(blog ->
                {
                    if(blog.getStatus().equals("activo")){
                        if(post.getStatus().equals("publicado") && post.getComments().size() > 0 ){
                            return this.postRepository.save(post);
                        }else{
                            List<Comment> commentEmpty = new ArrayList<Comment>();
                            post.setComments(commentEmpty);
                            return this.postRepository.save(post);
                        }
                    }else
                        return Mono.error(new PostException("El blog no esta activo"));

                    //return b.getStatus().equals("activo") ? this.postRepository.save(post) : Mono.error(new PostException("El blog no esta activo"));
                });
    }

    @Override
    public Flux<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Mono<Post> deleteReaction(String userId) {
        return this.postRepository.findById(userId)
                .doOnNext(b->{
                    System.out.println("doOnNext b = " + b);
                })
                .flatMap(post -> {
                    Reaction reaction = new Reaction();
                    post.setReaction(reaction);
                    return  this.postRepository.save(post);
                });
    }
}
