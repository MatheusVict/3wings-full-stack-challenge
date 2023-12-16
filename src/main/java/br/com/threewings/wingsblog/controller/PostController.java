package br.com.threewings.wingsblog.controller;

import br.com.threewings.wingsblog.domain.post.Post;
import br.com.threewings.wingsblog.dto.CreatePostDTO;
import br.com.threewings.wingsblog.dto.UpdatePostDTO;
import br.com.threewings.wingsblog.service.PostService;
import br.com.threewings.wingsblog.service.impl.PostServiceImpl;
import br.com.threewings.wingsblog.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @Tag(name = "Post")
    @OkResponse
    public ResponseEntity<List<Post>> findAll() {
        return ResponseEntity.ok(this.postService.findAll());
    }

    @PostMapping
    @Tag(name = "Post")
    @CreatedResponse
    @ConflictResponse
    public ResponseEntity<?> save(@RequestBody @Valid CreatePostDTO postDTO) {
        Post post = postDTO.toEntity();
        this.postService.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    @Tag(name = "Post")
    @OkResponse
    @NotFoundResponse
    public ResponseEntity<Post> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.postService.findById(id));
    }

    @PutMapping("/{id}")
    @Tag(name = "Post")
    @NoContentResponse
    @NotFoundResponse
    public ResponseEntity<?> update(@RequestBody UpdatePostDTO postDTO, @PathVariable Long id) {
        Post post = postDTO.toEntity();
        this.postService.update(post, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @Tag(name = "Post")
    @NoContentResponse
    @NotFoundResponse
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.postService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
