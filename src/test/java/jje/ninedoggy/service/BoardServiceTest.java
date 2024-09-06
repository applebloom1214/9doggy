package jje.ninedoggy.service;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.dto.PostDto;
import jje.ninedoggy.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @Mock
    BoardRepository boardRepository;

    @InjectMocks
    BoardService boardService;

    @Test
    @DisplayName("게시물 등록 테스트")
    void writeTest(){
        //given
        PostDto postDto = createPostDto();
        Post post = createPost(postDto);

        Long fakeBno = 1L;
        ReflectionTestUtils.setField(post, "bno", fakeBno);

        //mocking
        given(boardRepository.save(any()))
                .willReturn(post);
        given(boardRepository.findById(fakeBno)).
                willReturn(Optional.ofNullable(post));

        //when
        Long newBno = boardService.save(postDto);


        //then
        Post findPost = boardRepository.findById(newBno).get();

        assertEquals(post.getBno(), findPost.getBno());
        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getContent(), findPost.getContent());

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