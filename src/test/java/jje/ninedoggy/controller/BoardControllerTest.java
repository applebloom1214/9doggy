package jje.ninedoggy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jje.ninedoggy.domain.Post;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void mockSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @DisplayName("게시물 등록 테스트")
    @Test
    public void writeTest() throws Exception {
        //given
        final String url = "/posting";
        final PostDto postDto = createPostDto();

        // java object -> json
        final String requestBody = objectMapper.writeValueAsString(postDto);

        //when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());

        List<Post> posts = boardRepository.findAll();

        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getTitle()).isEqualTo(postDto.getTitle());
        assertThat(posts.get(0).getContent()).isEqualTo(postDto.getContent());
        assertThat(posts.get(0).getWriter()).isEqualTo(postDto.getWriter());

    }

    @DisplayName("글 하나 읽기 테스트")
    @Test
    public void readPostTest() throws Exception {
        //given
        final String url = "/posting/{bno}";
        final PostDto postDto = createPostDto();

        Post savedPost = boardRepository.save(Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .writer(postDto.getWriter())
                .build());

        //when
        final ResultActions result = mockMvc.perform(get(url, savedPost.getBno()));


        //then

        MvcResult mvcResult = result.andReturn();
        PostDto readPost = (PostDto) mvcResult.getModelAndView().getModel().get("post");

        result
                .andExpect(status().isOk());

        assertThat(readPost.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(readPost.getContent()).isEqualTo(savedPost.getContent());

    }

    @DisplayName("글 수정 테스트")
    @Test
    public void modifyPostTest() throws Exception {
        // given
        final String url = "/posting/{bno}";
        final PostDto postDto = createPostDto();

        Post savedPost = boardRepository.save(Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .writer(postDto.getWriter())
                .build());

        final String newTitle ="new title";
        final String newContent ="new content";

        PostDto modifyPostDto = new PostDto(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedPost.getBno())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(modifyPostDto)));


        // then
        result.andExpect(status().isOk());

        Post modifyPost = boardRepository.findById(savedPost.getBno()).get();

        assertThat(modifyPost.getTitle()).isEqualTo(newTitle);
        assertThat(modifyPost.getContent()).isEqualTo(newContent);
    }

    @DisplayName("글 삭제 테스트")
    @Test
    public void deletePostTest() throws Exception {
        // given
        final String url = "/posting/{bno}";
        final PostDto postDto = createPostDto();
        final Long bno = 1l;

        Post savedPost = boardRepository.save(Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .writer(postDto.getWriter())
                .build());

        // when
        ResultActions result = mockMvc.perform(delete(url, savedPost.getBno()));


        // then
        result.andExpect(status().isOk());

        assertThat(boardRepository.findById(bno)).isEmpty();

    }


    private PostDto createPostDto() {
        PostDto postDto = new PostDto();
        postDto.setTitle("title");
        postDto.setContent("content");
        postDto.setWriter("writer");
        return postDto;
    }

}