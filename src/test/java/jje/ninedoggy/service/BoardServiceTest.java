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
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

        //when
        Long newBno = boardService.save(postDto);

        //then
        assertThat(newBno).isNotNull();

    }

    @Test
    @DisplayName("페이징 테스트")
    void pagingTest(){
        //given
        PostDto postDto = createPostDto();
        Post post = createPost(postDto);
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        Page<Post> page = new PageImpl<>(postList);
        Long fakeBno = 1L;
        ReflectionTestUtils.setField(post, "bno", fakeBno);
        ReflectionTestUtils.setField(post, "hit", fakeBno);
        Specification<Post> spec;


        //mocking
        given(boardRepository.findAll((Specification<Post>) any(), (Pageable) any()))
        .willReturn(page); // boardService.listPaging 에 spec과 Pageable이 이미 정의 되어 있어
                           // 테스트에 정의된 지역변수와 충돌이 일어나 any() 로 처리함

//        given(boardRepository.findAll(pageRequest))  // contion이 null 일때
//                .willReturn(page);



        //when
        List<PostDto> postDTOList = boardService.listPaging(0,"TC","",null).getContent()
                .stream().map(PostDto::new).toList();


        //then

        assertThat(postDTOList).isNotNull();

    }

    @Test
    @DisplayName("게시물 읽기 테스트")
    void readTest(){
        //given
        PostDto postDto = createPostDto();
        Post post = createPost(postDto);
        Long fakeBno = 1L;
        ReflectionTestUtils.setField(post, "bno", fakeBno);
        ReflectionTestUtils.setField(post, "hit", fakeBno);

        //mocking
        given(boardRepository.findById(fakeBno)).
                willReturn(Optional.ofNullable(post));

        //when
        Post findPost = boardService.findById(fakeBno);


        //then

        assertThat(findPost).isNotNull();

    }

    @Test
    @DisplayName("게시물 업데이트 테스트")
    void updateTest(){
        //given
        PostDto postDto = createPostDto();
        Post post = createPost(postDto);
        String title = post.getTitle();
        String content = post.getContent();

        Long fakeBno = 1L;
        ReflectionTestUtils.setField(post, "bno", fakeBno);

        //mocking
        given(boardRepository.findById(fakeBno)).
                willReturn(Optional.ofNullable(post));

        //when
        PostDto updatePostDto = new PostDto("수정", "수정된 내용");
        Post findPost = boardService.update(fakeBno, updatePostDto);

        //then
        System.out.println(title);
        System.out.println(findPost.getTitle());

        assertNotEquals(title, findPost.getTitle());
        assertNotEquals(content, findPost.getContent());

    }


    @Test
    @DisplayName("게시물 삭제 테스트")
    void deleteTest(){
        //given
        Long bno = 1L;

        //mocking

        //when
        boardService.delete(bno);

        //then
        verify(boardRepository, atLeastOnce()).deleteById(any());

    }

    @Test
    @DisplayName("좋아요 기능 테스트")
    void likesTest() throws Exception {
        //given

        PostDto postDto = createPostDto();
        Post post = createPost(postDto);
        Long fakeLikes = 0l;
        Long fakeBno = 1L;
        int likeflag = 1;
        ReflectionTestUtils.setField(post, "bno", fakeBno);
        ReflectionTestUtils.setField(post, "likes", fakeLikes);


        //mocking
        given(boardRepository.findById(fakeBno)).
                willReturn(Optional.ofNullable(post));

        //when
        Long likes  =  boardService.handleLikes(fakeBno, likeflag);

        //then
        assertThat(likes.equals(1l));

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