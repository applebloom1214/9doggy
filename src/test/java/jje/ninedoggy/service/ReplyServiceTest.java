package jje.ninedoggy.service;

import jje.ninedoggy.domain.Reply;
import jje.ninedoggy.dto.ReplyDTO;
import jje.ninedoggy.repository.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
        Reply reply = createReply(replyDTO);

        Long fakeRno = 1L;
        ReflectionTestUtils.setField(reply, "rno", fakeRno);

        // mocking
        given(replyRepository.save(any(Reply.class)))
                .willReturn(reply);


        // when
        Long newRno = replyService.saveReply(replyDTO);


        // then
        assertThat(newRno).isEqualTo(fakeRno);
    }

    private Reply createReply(ReplyDTO replyDTO){
        return Reply.builder()
                .content(replyDTO.getContent())
                .writer(replyDTO.getWriter())
                .bno(replyDTO.getBno())
                .build();
    }

    private ReplyDTO createReplyDTO(){
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setContent("testcontent");
        replyDTO.setWriter("testwriter");
        replyDTO.setBno(1l);
        return replyDTO;
    }

}
