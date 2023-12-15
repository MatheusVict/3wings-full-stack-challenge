package br.com.threewings.wingsblog.dto;

import br.com.threewings.wingsblog.domain.post.Post;
import br.com.threewings.wingsblog.utils.SlugConversions;
import jakarta.validation.constraints.NotBlank;

public record CreatePostDTO(
        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Content is required")
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
