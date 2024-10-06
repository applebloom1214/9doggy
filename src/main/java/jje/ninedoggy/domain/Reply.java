package jje.ninedoggy.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
//@ToString
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="rno", updatable = false)
    private Long rno;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "writer", nullable = false)
    private String writer;

    @CreatedDate
    private String date;

    @Column(name ="bno", updatable = false, nullable = false)
    private Long bno;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Builder
    public Reply(String content, String writer, Long bno) {
        this.content = content;
        this.writer = writer;
        this.bno = bno;
    }

    public void updatePost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "rno=" + rno +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", date='" + date + '\'' +
                ", bno=" + bno +
                '}';
    }
}