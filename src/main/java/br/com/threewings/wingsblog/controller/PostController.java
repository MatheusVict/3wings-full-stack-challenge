package br.com.threewings.wingsblog.controller;

import br.com.threewings.wingsblog.domain.post.Post;
import br.com.threewings.wingsblog.dto.CreatePostDTO;
import br.com.threewings.wingsblog.dto.UpdatePostDTO;
import br.com.threewings.wingsblog.service.PostService;
import br.com.threewings.wingsblog.service.impl.PostServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<Post> findAll() {
        return this.postService.findAll();
    }

    @PostMapping
    public void save(@RequestBody @Valid CreatePostDTO postDTO) {
        Post post = postDTO.toEntity();
        this.postService.save(post);
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable Long id) {
        return this.postService.findById(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody UpdatePostDTO postDTO, @PathVariable Long id) {
        Post post = postDTO.toEntity();
        this.postService.update(post, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.postService.delete(id);
    }
}
