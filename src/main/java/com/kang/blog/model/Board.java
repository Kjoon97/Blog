package com.kang.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob  //대용량 데이터 처리(summernote 라이브러리 사용할 때 html태그가 섞이므로)
    private String content;

    @ColumnDefault("0")
    private int viewCount;   //조회 수

    @CreationTimestamp
    private Timestamp createDate;

    //외래 키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;     //한명의 유저는 다수의 게시글 쓴다.

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)    //CascadeType.REMOVE - board 게시물 삭제 시, 댓글도 모두 삭제
    @JsonIgnoreProperties({"board"})  //board select할 때 reply의 board는 조회하지 않음.- > 무한 참조 방지.
    @OrderBy("id desc")               //id 내림차순 정렬 조회.
    private List<Reply> replies;

    public void setterUser(User user){
        this.user = user;
    }

    public void updateBoard(String title, String content){
        this.title =title;
        this.content =content;
    }

    public Board updateViewCount(int viewCount){
        this.viewCount = viewCount+1;
        return this;
    }
}
