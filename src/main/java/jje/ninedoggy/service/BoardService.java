package jje.ninedoggy.service;

import jakarta.transaction.Transactional;
import jje.ninedoggy.domain.Post;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.repository.BoardRepository;
import jje.ninedoggy.repository.PostSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<Post> listPaging(int page, String condition, String keyword) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("bno").descending());
        Specification<Post> spec = (root, query, criteriaBuilder) -> null;
        if(condition.isEmpty()){
            return boardRepository.findAll(spec,pageRequest);
        } else if (condition.equals("TC")) {
            spec = PostSpecification.searchByTitle(keyword).or(PostSpecification.searchByContent(keyword));
        } else if (condition.equals("T")) {
            spec = PostSpecification.searchByTitle(keyword);
        } else if (condition.equals("C")) {
            spec = PostSpecification.searchByContent(keyword);
        } else if (condition.equals("W")) {
            spec = PostSpecification.searchByWriter(keyword);
        }
        return boardRepository.findAll(spec, pageRequest);
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
