package jje.ninedoggy.service;

import jje.ninedoggy.domain.Reply;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.dto.ReplyDTO;
import jje.ninedoggy.repository.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceTest {
    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private ReplyService replyService;

    @Test
    @DisplayName("댓글 등록 테스트")
    void writeReplyTest(){
        // given
        ReplyDTO replyDTO = createReplyDTO();

        // when

        // mocking

        // then
    }

    private ReplyDTO createReplyDTO(){
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setContent("testcontent");
        replyDTO.setWriter("testwriter");
        replyDTO.setBno(1l);
    }

}
