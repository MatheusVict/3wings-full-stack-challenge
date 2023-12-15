package br.com.threewings.wingsblog.dto;

import br.com.threewings.wingsblog.domain.post.Post;
import br.com.threewings.wingsblog.utils.SlugConversions;

public record UpdatePostDTO(
        String title,
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
