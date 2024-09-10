package jje.ninedoggy.dto;

import jje.ninedoggy.domain.Post;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostDto {
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private String createdAt;
    private Long hit;
    private Long likes;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }

    public PostDto(Post post) {
        this.bno = post.getBno();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.createdAt = post.getDate();
        this.hit = post.getHit();
        this.likes = post.getLikes();
    }

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
