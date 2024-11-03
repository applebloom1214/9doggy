package jje.ninedoggy.repository;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.domain.Reply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void setUp() {
        for (int i = 0; i < 1; i++) {
            Post post = new Post("testcontent"+i, "contentcontent"+i, "tester"+i);
            boardRepository.save(Post.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter())
                    .build());
        }

        for (int i = 0; i < 10; i++) {
            Long bno = 1L;
            Long prno = (long)i+1;
            Reply reply = new Reply("replycontent"+i, "writer"+i, bno, prno);
            replyRepository.save(reply);
            Post post = boardRepository.findById(bno).get();
            post.addReply(reply);
        }

        for (int i = 0; i < 150; i++) {
            Long prno = (long)(Math.random()*10+1);
            Reply reply = new Reply("replycontent"+i, "writer"+i, 1l, prno);
            replyRepository.save(reply);
            Post post = boardRepository.findById(1l).get();
            post.addReply(reply);
        }
    }

    @DisplayName("댓글 기본 테스트")
    @Test
    public void ReplyTest(){
        List<Post> posts = boardRepository.findAll();
        for (Post post : posts) {
            System.out.println(post);
        }
    }

    @DisplayName("댓글 가져오기")
    @Test
    public void readReplyTest(){
        List<Reply> replies = replyRepository.findAllByBnoOrderByRnoDesc(1l);
        for (Reply reply : replies) {
            System.out.println(reply);
        }
    }

    @DisplayName("대댓글 가져오기")
    @Test
    public void readNestedReplyTest(){
        List<Reply> replies = replyRepository
                .findAllByBno(1L, Sort.by(Sort.Order.desc("prno"),
                        Sort.Order.desc("rno")));
        for (Reply reply : replies) {
            System.out.println(reply);
        }
    }

    @DisplayName("댓글 수정하기")
    @Test
    public void modifyReplyTest(){
        // given
        Reply reply = replyRepository.findById(1L).get();
        String content = "modified!!";

        // when
        reply.modifyReply(content);

        // then
        assertThat(replyRepository.findById(1l).get().getContent())
                .isEqualTo("modified!!");

    }

    @DisplayName("댓글 삭제하기")
    @Test
    public void delReplyTest(){
        // given
        Long rno = 1L;

        // when
        replyRepository.deleteById(rno);

        // then
        assertThat(replyRepository.findById(1l)
                .isEmpty());

    }



}
