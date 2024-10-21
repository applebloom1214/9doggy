package jje.ninedoggy.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bno", updatable = false)
    private Long bno;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "writer", nullable = false)
    private String writer;

    @CreatedDate
    private String date;

    @Column(name = "hit")
    private Long hit;

    @Column(name = "likes")
    private Long likes;

    @Column(name = "rcnt")
    private Long rcnt;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("rno asc")
    private List<Reply> replies = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.hit= this.hit == null ? 0 : this.hit;
        this.likes= this.likes == null ? 0 : this.likes;
        this.rcnt= this.rcnt == null ? 0 : this.rcnt;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void plusHit(){
        this.hit += 1;
    }

    public void plusRcnt(){
        this.rcnt += 1;
    }

    public void minusRcnt(){
        this.rcnt -= 1;
    }

    public void changeLikes(int likeFlag){
        this.likes += likeFlag;
    }


    public void changePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Builder
    public Post(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void addReply(Reply reply){
        this.replies.add(reply);
        plusRcnt();
        reply.updatePost(this);
    }

}
