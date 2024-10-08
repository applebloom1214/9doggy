package jje.ninedoggy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jje.ninedoggy.dto.ReplyDTO;
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

        //when
        ResultActions result = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());
    }

    private ReplyDTO createReplyDTO(){
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setContent("reply content");
        replyDTO.setWriter("writer");
        replyDTO.setBno(1L);
        return replyDTO;
    }

}
