package br.com.threewings.wingsblog.dto;

import br.com.threewings.wingsblog.domain.post.Post;
import br.com.threewings.wingsblog.utils.SlugConversions;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePostDTO(
        @Schema(description = "Post title", example = "My first post")
        String title,
        @Schema(description = "Post content", example = "This is my first post")
        String content
) {
    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .slug(SlugConversions.convert(title))
                .build();
    }
}
