package com.kang.blog.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    @CreationTimestamp
    private Timestamp createDate;

    //외래 키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    //외래 키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="userId")
    private User user;

    //댓글 쓰기 용 매소드
    public void setterUserAndBoard(User user, Board board){
        this.user = user;
        this.board = board;
    }
}
