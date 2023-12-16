package br.com.threewings.wingsblog.controller;

import br.com.threewings.wingsblog.domain.post.Post;
import br.com.threewings.wingsblog.exceptions.PostNotFoundException;
import br.com.threewings.wingsblog.exceptions.SlugAlreadyExistsException;
import br.com.threewings.wingsblog.service.PostService;
import br.com.threewings.wingsblog.utils.PostCreation;
import br.com.threewings.wingsblog.utils.PostDTOCreation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Post Controller Test")
class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @BeforeEach
    void setUp() {
        Post postSaved = PostCreation.createValidPost();

        BDDMockito.when(this.postService.findAll())
                .thenReturn(List.of(postSaved));

        BDDMockito.when(this.postService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(postSaved);

        BDDMockito.doNothing().when(this.postService).delete(ArgumentMatchers.anyLong());

        BDDMockito.doNothing().when(this.postService)
                .update(ArgumentMatchers.any(Post.class), ArgumentMatchers.anyLong());

        BDDMockito.doNothing().when(this.postService)
                .update(ArgumentMatchers.any(Post.class), ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Find all posts when successful")
    void findAllWhenSuccessful() {
        String expectedTitle = PostCreation.createValidPost().getTitle();
        List<Post> posts = this.postController.findAll().getBody();
        assertNotNull(posts);
        assertFalse(posts.isEmpty());
        assertEquals(expectedTitle, posts.get(0).getTitle());
    }

    @Test
    @DisplayName("Find post by id when successful")
    void findByIdWhenSuccessful() {
        Long expectedId = PostCreation.createValidPost().getId();
        Post post = this.postController.findById(1L).getBody();
        assertNotNull(post);
        assertEquals(expectedId, post.getId());
    }

    @Test
    @DisplayName("Find post by id when post is not found")
    void findByIdWhenPostIsNotFound() {
        BDDMockito.when(this.postService.findById(ArgumentMatchers.anyLong()))
                .thenThrow(PostNotFoundException.class);
        assertThrows(PostNotFoundException.class, () -> this.postController.findById(1L));
    }

    @Test
    @DisplayName("Save post when successful")
    void saveWhenSuccessful() {
        assertDoesNotThrow(() -> this.postController.save(PostDTOCreation.createCreatePostDTO()));
    }


    @Test
    @DisplayName("Update post when successful")
    void updateWhenSuccessful() {
        assertDoesNotThrow(() -> this.postController.update(PostDTOCreation.createUpdatePostDTO(), 1L));
    }

    @Test
    @DisplayName("Delete post when successful")
    void deleteWhenSuccessful() {
        assertDoesNotThrow(() -> this.postController.delete(1L));
    }

}