package jje.ninedoggy.repository;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.domain.Reply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void setUp() {
        for (int i = 0; i < 10; i++) {
            Post post = new Post("testcontent"+i, "contentcontent"+i, "tester"+i);
            boardRepository.save(Post.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter())
                    .build());
        }

        for (int i = 0; i < 150; i++) {
            Long bno = (long)(Math.random()*10+1);
            Reply reply = new Reply("replycontent"+i, "writer"+i, bno);
            Post post = boardRepository.findById(bno).get();
            post.addReply(reply);
            replyRepository.save(reply);
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

}
