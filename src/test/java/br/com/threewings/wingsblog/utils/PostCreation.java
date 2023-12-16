package br.com.threewings.wingsblog.utils;

import br.com.threewings.wingsblog.domain.post.Post;

import java.sql.Timestamp;
import java.time.Instant;

public class PostCreation {
    public static Post createValidPost() {
        return Post.builder()
                .id(1L)
                .title("Post Title")
                .slug("post-title")
                .content("Post Content")
                .createdAt(Timestamp.from(Instant.now()))
                .build();
    }

    public static Post createValidPostToBeSaved() {
        return Post.builder()
                .title("Post Title")
                .slug("post-title")
                .content("Post Content")
                .build();
    }
}
