package jje.ninedoggy.service;

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

    public List<Post> findAll() {
        return boardRepository.findAll();
    }

    public Long save(PostDto dto) {
        return boardRepository.save(dto.toEntity()).getBno();
    }



}
