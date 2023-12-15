package br.com.threewings.wingsblog.service.impl;

import br.com.threewings.wingsblog.domain.post.Post;
import br.com.threewings.wingsblog.repository.PostRepository;
import br.com.threewings.wingsblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public void save(Post post) {
        if (this.slugExists(post.getSlug())) {
            throw new RuntimeException("Slug already exists");
        }
        this.postRepository.save(post);
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Post findById(Long id) {
        return this.postRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void update(Post post, Long id) {
        Optional<Post> postFound = this.postRepository.findById(id);
        if (postFound.isEmpty()) {
            throw new RuntimeException("Post not found");
        }
        BeanUtils.copyProperties(post, postFound.get(), "id");
        this.postRepository.save(postFound.get());
    }

    @Override
    public void delete(Long id) {
        this.postRepository.findById(id)
                .map(post -> {
                    this.postRepository.delete(post);
                    return post;
                })
                .orElseThrow(RuntimeException::new);
    }

    private boolean slugExists(String slug) {
        return this.postRepository.findBySlug(slug) != null;
    }

    //isNotBlank(post.getTitle()) ? postToUpdate.getTitle() : post.getTitle()
    private boolean isNotBlank(String string) {
        return string != null || !string.trim().isEmpty();
    }
}
