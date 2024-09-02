package jje.ninedoggy.service;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Post> findAll() {
        for (int i = 0; i < 10; i++) {
            Post post = new Post("test"+i, "contentcontent"+i, "tester"+i);
            boardRepository.save(Post.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter())
                    .build());
        }

        return boardRepository.findAll();
    }


}
