package br.com.threewings.wingsblog.service;

import br.com.threewings.wingsblog.domain.post.Post;

import java.util.List;

public interface PostService {
    void save(Post post);

    List<Post> findAll();

    Post findById(Long id);

    void update(Post post, Long id);

    void delete(Long id);
}
