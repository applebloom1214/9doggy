package jje.ninedoggy.dummy;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.domain.Reply;
import jje.ninedoggy.repository.BoardRepository;
import jje.ninedoggy.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test") // test 그룹을 활성화시키지 않음
@Component
public class BoardDummy implements ApplicationRunner {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public BoardDummy(BoardRepository boardRepository, ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 170; i++) {
            Post post = new Post("test"+i, "contentcontent"+i, "tester"+i);
            boardRepository.save(Post.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter())
                    .build());
        }

        for (int i = 1; i <= 1000; i++) {
            Long bno = 170L;
            Reply reply = new Reply("replycontent"+i, "writer"+i, bno);
            Post post = boardRepository.findById(bno).get();
            post.addReply(reply);
            replyRepository.save(reply);
        }
    }
}
