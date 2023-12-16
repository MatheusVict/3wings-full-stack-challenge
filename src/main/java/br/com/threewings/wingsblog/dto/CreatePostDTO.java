package br.com.threewings.wingsblog.dto;

import br.com.threewings.wingsblog.domain.post.Post;
import br.com.threewings.wingsblog.utils.SlugConversions;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreatePostDTO(
        @NotBlank(message = "Title is required")
        @Schema(description = "Post title", example = "My first post")
        String title,
        @NotBlank(message = "Content is required")
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
