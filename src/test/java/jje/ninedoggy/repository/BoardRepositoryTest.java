package jje.ninedoggy.repository;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.dto.PostDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void setUp() {
        for (int i = 0; i < 17; i++) {
            Post post = new Post("test"+i, "contentcontent"+i, "tester"+i);
            boardRepository.save(Post.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter())
                    .build());
        }
    }


    @Test
    @DisplayName("페이징 테스트")
    public void pagingTest(){
        // given
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.by("bno").descending());

        // when
        List<Post> postList = boardRepository.findAll(pageRequest).getContent();
        for (Post post : postList) {
            System.out.println(post);
        }

        // then
        assertThat(postList.size()).isEqualTo(7);
    }

    @Test
    @DisplayName("게시글 등록 테스트")
    public void writeTest(){
        // given
        PostDto postDto = createPostDto();
        Post post = createPost(postDto);

        // when
        boardRepository.save(post);

        // then
        assertThat(boardRepository.findById(post.getBno()).get().getWriter()).isEqualTo("tester");
    }

    @Test
    @DisplayName("게시글 읽기 테스트")
    public void readTest(){
        // given
        Long readId = 1L;

        // when
        Post post = boardRepository.findById(readId).get();

        // then
        assertThat(post.getBno()).isEqualTo(readId);
        assertThat(post.getTitle()).isEqualTo("test0");
        assertThat(post.getContent()).isEqualTo("contentcontent0");
        assertThat(post.getWriter()).isEqualTo("tester0");
    }

    @Test
    @DisplayName("게시글 업데이트 테스트")
    public void updateTest(){
        // given
        Post post = boardRepository.findById(1l).get();

        // when
        post.changePost("modified","update content");

        // then
        assertThat(boardRepository.findById(post.getBno()).get().getTitle()).isEqualTo("modified");
        assertThat(boardRepository.findById(post.getBno()).get().getContent()).isEqualTo("update content");
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deleteTest(){
        // given
        Post post = boardRepository.findById(1l).get();

        // when
        boardRepository.delete(post);

        // then
        assertThat(boardRepository.findById(post.getBno()).isEmpty()).isTrue();
    }
    

    @Test
    public void readAll(){
        List<PostDto> posts = boardRepository.findAll()
                .stream()
                .map(PostDto::new)
                .toList();

        posts.forEach(System.out::println);
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