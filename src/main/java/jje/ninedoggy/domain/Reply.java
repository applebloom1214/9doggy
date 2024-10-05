package jje.ninedoggy.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@ToString
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="rno", updatable = false)
    private Long rno;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "writer", nullable = false)
    private String writer;

    @Column(name = "cnt")
    private Long cnt;

    @CreatedDate
    private String date;

    @Column(name ="bno", updatable = false)
    private Long bno;

}
