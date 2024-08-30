package jje.ninedoggy.dto;

import jje.ninedoggy.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostDto {
    private String title;
    private String content;
    private String writer;

    public PostDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
    }
}
