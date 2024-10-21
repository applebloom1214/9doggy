package jje.ninedoggy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jje.ninedoggy.domain.Post;
import jje.ninedoggy.domain.Reply;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.dto.ReplyDTO;
import jje.ninedoggy.repository.BoardRepository;
import jje.ninedoggy.repository.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ReplyControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void mockSetUp() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @DisplayName("댓글 등록 테스트")
    @Test
    public void replyWriteTest() throws Exception {
        //given
        final String url = "/posting/reply";
        final ReplyDTO replyDTO = createReplyDTO();

        final String requestBody = objectMapper.writeValueAsString(replyDTO);
        Post post = createPostDto().toEntity();
        boardRepository.save(Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter())
                .build());


        //when
        ResultActions result = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    public void modifyReplyTest() throws Exception {
        // given
        final String url = "/posting/reply";
        final ReplyDTO replyDTO = createReplyDTO();

        Reply savedReply = replyRepository.save(Reply.builder()
                .content(replyDTO.getContent())
                .writer(replyDTO.getWriter())
                .bno(replyDTO.getBno())
                .build());

        final String newContent ="new content";

        ReplyDTO modifyReplyDto = new ReplyDTO(1l, newContent);

        // when
        ResultActions result = mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(modifyReplyDto)));


        // then
        result.andExpect(status().isOk());

        Reply modifyReply =
                replyRepository.findById(1l).get();

        assertThat(modifyReply.getContent()).isEqualTo(newContent);
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    public void delReplyTest() throws Exception {
        // given
        final String url = "/posting/reply";
        ReplyDTO replyDTO = createReplyDTO();

        Reply savedReply = replyRepository.save(Reply.builder()
                .content(replyDTO.getContent())
                .writer(replyDTO.getWriter())
                .bno(replyDTO.getBno())
                .build());
        replyDTO.setRno(1L);

        PostDto postDto = createPostDto();
        Post savedPost = boardRepository.save(Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .writer(postDto.getWriter())
                .build());
        // when
        ResultActions result = mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(replyDTO)));


        // then
        result.andExpect(status().isOk());


        assertThat(replyRepository.findById(1l).orElse(null)).isNull();
    }



    private ReplyDTO createReplyDTO(){
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setContent("reply content");
        replyDTO.setWriter("writer");
        replyDTO.setBno(1L);
        return replyDTO;
    }

    private PostDto createPostDto() {
        PostDto postDto = new PostDto();
        postDto.setTitle("title");
        postDto.setContent("content");
        postDto.setWriter("writer");
        return postDto;
    }

}
