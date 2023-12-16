package br.com.threewings.wingsblog.service;

import br.com.threewings.wingsblog.domain.post.Post;
import br.com.threewings.wingsblog.exceptions.PostNotFoundException;
import br.com.threewings.wingsblog.exceptions.SlugAlreadyExistsException;
import br.com.threewings.wingsblog.repository.PostRepository;
import br.com.threewings.wingsblog.service.impl.PostServiceImpl;
import br.com.threewings.wingsblog.utils.PostCreation;
import br.com.threewings.wingsblog.utils.SlugConversions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Post Service Test")
class PostServiceTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        Post postSaved = PostCreation.createValidPost();
        BDDMockito.when(this.postRepository.save(ArgumentMatchers.any(Post.class)))
                .thenReturn(postSaved);
        BDDMockito.when(this.postRepository.findBySlug(ArgumentMatchers.anyString()))
                .thenReturn(postSaved);
        BDDMockito.when(this.postRepository.findAll())
                .thenReturn(List.of(postSaved));
        BDDMockito.when(this.postRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(postSaved));
        BDDMockito.doNothing().when(this.postRepository).delete(ArgumentMatchers.any(Post.class));
    }

    @AfterEach
    void tearDown() {
        this.postRepository.deleteAll();
    }

    @Test
    @DisplayName("Save post when successful")
    void saveWhenSuccessful() {
        BDDMockito.when(this.postRepository.findBySlug(ArgumentMatchers.anyString()))
                .thenReturn(null);

        Post postToBeSaved = PostCreation.createValidPostToBeSaved();
        assertDoesNotThrow(() -> this.postService.save(postToBeSaved));
    }

    @Test
    @DisplayName("Save post when slug already exists")
    void saveWhenSlugAlreadyExists() {
        Post postToBeSaved = PostCreation.createValidPostToBeSaved();
        assertThrows(SlugAlreadyExistsException.class, () -> this.postService.save(postToBeSaved));
    }

    @Test
    @DisplayName("Find all posts when successful")
    void findAllWhenSuccessful() {
        String expectedTitle = PostCreation.createValidPost().getTitle();
        List<Post> posts = this.postService.findAll();
        assertNotNull(posts);
        assertFalse(posts.isEmpty());
        assertEquals(expectedTitle, posts.get(0).getTitle());
        assertEquals(posts.get(0).getSlug(), SlugConversions.convert(expectedTitle));
    }

    @Test
    @DisplayName("Find post by id when successful")
    void findByIdWhenSuccessful() {
        Long expectedId = PostCreation.createValidPost().getId();
        Post post = this.postService.findById(expectedId);
        assertNotNull(post);
        assertEquals(expectedId, post.getId());
    }

    @Test
    @DisplayName("Find post by id when post not found")
    void findByIdWhenPostNotFound() {
        BDDMockito.when(this.postRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> this.postService.findById(1L));
    }

    @Test
    @DisplayName("Update post when successful")
    void updateWhenSuccessful() {
        Long expectedId = PostCreation.createValidPost().getId();
        Post postToBeUpdated = PostCreation.createValidPostToBeSaved();
        assertDoesNotThrow(() -> this.postService.update(postToBeUpdated, expectedId));
    }

    @Test
    @DisplayName("Update post when post not found")
    void updateWhenPostNotFound() {
        BDDMockito.when(this.postRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        Post postToBeUpdated = PostCreation.createValidPostToBeSaved();
        assertThrows(PostNotFoundException.class, () -> this.postService.update(postToBeUpdated, 1L));
    }

    @Test
    @DisplayName("Delete post when successful")
    void deleteWhenSuccessful() {
        Long expectedId = PostCreation.createValidPost().getId();
        assertDoesNotThrow(() -> this.postService.delete(expectedId));
    }

    @Test
    @DisplayName("Delete post when post not found")
    void deleteWhenPostNotFound() {
        BDDMockito.when(this.postRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> this.postService.delete(1L));
    }

}