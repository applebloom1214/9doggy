package jje.ninedoggy.dto;

import jje.ninedoggy.domain.Post;
import jje.ninedoggy.domain.Reply;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReplyDTO {
    private Long rno;
    private String content;
    private String writer;
    private String createdAt;
    private Long bno;
    private Post post;

    public Reply toEntity() {
        return Reply.builder()
                .content(content)
                .writer(writer)
                .bno(bno)
                .build();
    }

    public ReplyDTO(Reply reply) {
        this.rno = reply.getRno();
        this.content = reply.getContent();
        this.writer = reply.getWriter();
        this.createdAt = reply.getDate();
        this.bno = reply.getBno();
//        this.post = reply.getPost();
    }

    public ReplyDTO(Long rno, String content){
        this.rno = rno;
        this.content = content;
    }


}
