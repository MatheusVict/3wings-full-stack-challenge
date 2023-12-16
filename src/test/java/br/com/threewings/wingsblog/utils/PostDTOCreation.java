package br.com.threewings.wingsblog.utils;

import br.com.threewings.wingsblog.dto.CreatePostDTO;
import br.com.threewings.wingsblog.dto.UpdatePostDTO;

public class PostDTOCreation {
    public static CreatePostDTO createCreatePostDTO() {

        return new CreatePostDTO(
                "Post Title",
                "Post Subtitle"
        );
    }

    public static UpdatePostDTO createUpdatePostDTO() {

        return new UpdatePostDTO(
                "Post Title",
                "Post Subtitle"
        );
    }

    public static CreatePostDTO createCreatePostDTOWithInvalidTitle() {

        return new CreatePostDTO(
                "",
                "Post Subtitle"
        );
    }

    public static CreatePostDTO createCreatePostDTOWithInvalidSubtitle() {

        return new CreatePostDTO(
                "Post Title",
                ""
        );
    }
}
