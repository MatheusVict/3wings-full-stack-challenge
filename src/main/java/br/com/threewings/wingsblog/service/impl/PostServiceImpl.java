package br.com.threewings.wingsblog.service.impl;

import br.com.threewings.wingsblog.domain.post.Post;
import br.com.threewings.wingsblog.exceptions.PostNotFoundException;
import br.com.threewings.wingsblog.exceptions.SlugAlreadyExistsException;
import br.com.threewings.wingsblog.repository.PostRepository;
import br.com.threewings.wingsblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public void save(Post post) {
        if (this.slugExists(post.getSlug()))
            throw new SlugAlreadyExistsException("Slug already exists");
        this.postRepository.save(post);
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Post findById(Long id) {
        return this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    @Override
    public void update(Post post, Long id) {
        Post postFound = this.findById(id);
        BeanUtils.copyProperties(post, postFound, "id");
        this.postRepository.save(postFound);
    }

    @Override
    public void delete(Long id) {
        Post postFound = this.findById(id);
        this.postRepository.delete(postFound);
    }

    private boolean slugExists(String slug) {
        return this.postRepository.findBySlug(slug) != null;
    }

    private boolean isNotBlank(String string) {
        return string != null || !string.trim().isEmpty();
    }
}
