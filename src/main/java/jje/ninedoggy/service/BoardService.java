package jje.ninedoggy.service;

import jakarta.transaction.Transactional;
import jje.ninedoggy.domain.Post;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public void createPostDummy(){
        for (int i = 0; i < 10; i++) {
            Post post = new Post("test"+i, "contentcontent"+i, "tester"+i);
            boardRepository.save(Post.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter())
                    .build());
        }
    }

    public Page<Post> listPaging(int page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("bno").descending());
        return boardRepository.findAll(pageRequest);

    }

    public Long save(PostDto dto) {
        return boardRepository.save(dto.toEntity()).getBno();
    }

    public Post findByIdPlain(Long id) {
        return boardRepository.findById(id).get();
    }

    @Transactional
    public Post findById(Long id) {
        plusHit(id);
        return boardRepository.findById(id).get();
    }

    @Transactional
    public Post update(Long bno, PostDto dto) {
        Post post= boardRepository.findById(bno)
                .orElseThrow(()->new IllegalArgumentException("not found: "+bno));
        post.changePost(dto.getTitle(), dto.getContent());
        return post;
    }

    public void delete(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Transactional
    public void plusHit(Long bno) {
        Post post= boardRepository.findById(bno)
                .orElseThrow(()->new IllegalArgumentException("not found: "+bno));
        post.plusHit();
    }

    @Transactional
    public Long handleLikes(Long bno, int likesFlag){
        Post post = findByIdPlain(bno);
        post.changeLikes(likesFlag);
        return post.getLikes();
    }




}
