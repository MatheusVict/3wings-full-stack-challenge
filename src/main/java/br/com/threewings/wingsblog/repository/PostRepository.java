package br.com.threewings.wingsblog.repository;

import br.com.threewings.wingsblog.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findBySlug(String slug);
}
