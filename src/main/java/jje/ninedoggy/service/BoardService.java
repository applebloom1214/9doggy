package jje.ninedoggy.service;

import jakarta.transaction.Transactional;
import jje.ninedoggy.domain.Post;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public List<PostDto> findAll() {
        return boardRepository.findAll().stream()
                .map(PostDto::new)
                .toList();
    }

    public Long save(PostDto dto) {
        return boardRepository.save(dto.toEntity()).getBno();
    }

    public Post findById(Long id) {
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





}
