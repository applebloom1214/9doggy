package jje.ninedoggy.service;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.domain.Reply;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.dto.ReplyDTO;
import jje.ninedoggy.repository.BoardRepository;
import jje.ninedoggy.repository.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceTest {
    @Mock
    private ReplyRepository replyRepository;
    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private ReplyService replyService;

    @Test
    @DisplayName("댓글 등록 테스트")
    void writeReplyTest(){
        // given
        ReplyDTO replyDTO = createReplyDTO();
        Reply reply = createReply(replyDTO);
        PostDto postDto = createPostDto();
        Post post = createPost(postDto);

        Long fakeRno = 1L;
        Long fakeBno = 1L;
        Long fakeRcnt = 1L;
        ReflectionTestUtils.setField(reply, "rno", fakeRno);
        ReflectionTestUtils.setField(post, "bno", fakeBno);
        ReflectionTestUtils.setField(post, "rcnt", fakeRcnt);

        // mocking
        given(replyRepository.save(any(Reply.class)))
                .willReturn(reply);
        given(boardRepository.findById(any(Long.class)))
                .willReturn(Optional.of(post));


        // when
        Long newRno = replyService.saveReply(replyDTO).getRno();


        // then
        assertThat(newRno).isEqualTo(fakeRno);
    }

    @Test
    @DisplayName("댓글 읽기 테스트")
    void readReplyTest(){
        // given
        ReplyDTO replyDTO = createReplyDTO();
        Reply reply = createReply(replyDTO);
        PostDto postDto = createPostDto();
        Post post = createPost(postDto);
        List<Reply> replies = new ArrayList<>();
        replies.add(reply);

        Long fakeRno = 1L;
        Long fakeBno = 1L;
        Long fakeRcnt = 1L;
        ReflectionTestUtils.setField(reply, "rno", fakeRno);
        ReflectionTestUtils.setField(reply, "prno", 1L);
        ReflectionTestUtils.setField(post, "bno", fakeBno);
        ReflectionTestUtils.setField(post, "rcnt", fakeRcnt);

        // mocking
//        given(replyRepository.findAllByBnoOrderByRnoAsc(any(Long.class), any(PageRequest.class)))
//                .willReturn(paging);
        given(replyRepository.findAllByBno(any(Long.class), any(Sort.class)))
                .willReturn(replies);
//        given(replyRepository.findAllByBnoOrderByRnoDesc(any(Long.class)))
//                .willReturn(replies);


        // when
        List<Reply> readReplies = replyService.readReply(fakeBno);

        // then
        assertThat(readReplies).isNotNull();
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void modifyReplyTest(){
        //given
        ReplyDTO replyDTO = createReplyDTO();
        Reply reply = createReply(replyDTO);
        PostDto postDto = createPostDto();
        Post post = createPost(postDto);
        String content = "testcontent";
        String newContent = "modified content";

        Long fakeRno = 1L;
        Long fakeBno = 1L;
        Long fakeRcnt = 1L;
        ReflectionTestUtils.setField(reply, "rno", fakeRno);
        ReflectionTestUtils.setField(post, "bno", fakeBno);
        ReflectionTestUtils.setField(post, "rcnt", fakeRcnt);

        //mocking
        given(replyRepository.findById(fakeBno)).
                willReturn(Optional.ofNullable(reply));


        //when
        ReplyDTO updateReplyDto = new ReplyDTO(fakeRno, newContent);
        Reply modifiedReply = replyService.modifyReply(updateReplyDto);

        //then
        assertNotEquals(content, modifiedReply.getContent());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void delReplyTest(){
        //given
        ReplyDTO replyDTO = createReplyDTO();
        Reply reply = createReply(replyDTO);
        PostDto postDto = createPostDto();
        Post post = createPost(postDto);

        Long fakeRno = 1L;
        Long fakeBno = 1L;
        Long fakeRcnt = 1L;
        ReflectionTestUtils.setField(reply, "rno", fakeRno);
        ReflectionTestUtils.setField(reply, "bno", fakeBno);
        ReflectionTestUtils.setField(post, "bno", fakeBno);
        ReflectionTestUtils.setField(post, "rcnt", fakeRcnt);
        replyDTO.setRno(fakeRno);

        //mocking
        given(replyRepository.findById(fakeRno)).
                willReturn(Optional.ofNullable(reply));
        given(boardRepository.findById(fakeBno)).
                willReturn(Optional.ofNullable(post));


        //when
        replyService.deleteReply(replyDTO);

        //then
        assertThat(replyRepository.findById(fakeRno).get().getDeleted()).isEqualTo("Y");
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

    private Post createPost(PostDto postDto) {
        return Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .writer(postDto.getWriter())
                .build();
    }

    private PostDto createPostDto() {
        PostDto postDto = new PostDto();
        postDto.setWriter("tester");
        postDto.setTitle("test title");
        postDto.setContent("test content");
        return postDto;
    }

}
