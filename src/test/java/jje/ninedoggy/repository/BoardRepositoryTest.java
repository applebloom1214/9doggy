package jje.ninedoggy.repository;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.dto.PostDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void setUp() {
        Post post = new Post("test","contentcontent","tester");
        boardRepository.save(Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter())
                .build());

    }

    @Test
    public void readAll(){
        List<PostDto> posts = boardRepository.findAll()
                .stream()
                .map(PostDto::new)
                .toList();

        posts.forEach(System.out::println);
    }

}