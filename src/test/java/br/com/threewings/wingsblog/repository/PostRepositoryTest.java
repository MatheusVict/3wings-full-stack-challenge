package br.com.threewings.wingsblog.repository;

import br.com.threewings.wingsblog.domain.post.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Post Repository Tests")
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository.save(new Post(
                1L,
                "post-1",
                "Post 1",
                "Lorem ipsum dolor sit amet, ies aliquam nisl nunc quis nisl.",
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now())
        ));
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("Find by slug")
    void findBySlug() {
        Post post = postRepository.findBySlug("post-1");
        assertNotNull(post);
        assertEquals("Post 1", post.getTitle());
    }

    @Test
    @DisplayName("Find by slug not found")
    void findBySlugNotFound() {
        Post post = postRepository.findBySlug("post-2");
        assertNull(post);
    }

}