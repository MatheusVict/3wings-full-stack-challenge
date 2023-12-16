package br.com.threewings.wingsblog.integration;

import br.com.threewings.wingsblog.domain.post.Post;
import br.com.threewings.wingsblog.repository.PostRepository;
import br.com.threewings.wingsblog.utils.PostCreation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("Post controller Integration Tests")
public class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        List<Post> postsReceived = PostCreation.createValidPostList();

        given(postRepository.findAll())
                .willReturn(postsReceived);
        given(postRepository.findById(ArgumentMatchers.anyLong()))
                .willReturn(java.util.Optional.ofNullable(PostCreation.createValidPost()));
        given(postRepository.save(ArgumentMatchers.any(Post.class)))
                .willReturn(PostCreation.createValidPost());
        doNothing().when(postRepository).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Find all posts")
    void findAll() throws Exception {
        String postTitleExpected = PostCreation.createValidPost().getTitle();
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(postTitleExpected));
    }

    @Test
    @DisplayName("Find by id")
    void findById() throws Exception {
        Long postIdExpected = PostCreation.createValidPost().getId();
        mockMvc.perform(get("/posts/{id}", postIdExpected))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postIdExpected))
                .andExpect(jsonPath("$.title").value(PostCreation.createValidPost().getTitle()));
    }

    @Test
    @DisplayName("Find by id not found")
    void findByIdNotFound() throws Exception {
        given(postRepository.findById(ArgumentMatchers.anyLong()))
                .willReturn(java.util.Optional.empty());

        Long postIdExpected = PostCreation.createValidPost().getId();
        mockMvc.perform(get("/posts/{id}", postIdExpected + 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Save post")
    void savePost() throws Exception {
        Post postToBeSaved = PostCreation.createValidPostToBeSaved();

        String postAsJson = mapper.writeValueAsString(postToBeSaved);

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postAsJson)
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Update post")
    void updatePost() throws Exception {
        Long postIdExpected = PostCreation.createValidPost().getId();
        Post postToBeUpdated = PostCreation.createValidPost();
        postToBeUpdated.setTitle("Post Title Updated");

        String postAsJson = mapper.writeValueAsString(postToBeUpdated);

        mockMvc.perform(MockMvcRequestBuilders.put("/posts/{id}", postIdExpected)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postAsJson)
        ).andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Delete post")
    void deletePost() throws Exception {
        Long postIdExpected = PostCreation.createValidPost().getId();
        mockMvc.perform(delete("/posts/{id}", postIdExpected))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Save post with invalid title")
    void savePostWithInvalidTitle() throws Exception {
        Post postToBeSaved = PostCreation.createValidPostToBeSaved();
        postToBeSaved.setTitle(null);

        String postAsJson = mapper.writeValueAsString(postToBeSaved);

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postAsJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Save post with invalid content")
    void savePostWithInvalidContent() throws Exception {
        Post postToBeSaved = PostCreation.createValidPostToBeSaved();
        postToBeSaved.setContent(null);

        String postAsJson = mapper.writeValueAsString(postToBeSaved);

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postAsJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Save post with already existent slug")
    void savePostWithAlreadyExistentSlug() throws Exception {
        Post postToBeSaved = PostCreation.createValidPostToBeSaved();

        String postAsJson = mapper.writeValueAsString(postToBeSaved);

        given(postRepository.findBySlug(ArgumentMatchers.anyString()))
                .willReturn(PostCreation.createValidPost());

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postAsJson)
        ).andExpect(status().isConflict());
    }



}
