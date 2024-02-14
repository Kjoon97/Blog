package com.kang.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = @Index(name="idx_title", columnList="title"))
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob  //대용량 데이터 처리(summernote 라이브러리 사용할 때 html태그가 섞이므로)
    private String content;

    @ColumnDefault("0")
    private int viewCount;   //조회 수

    @CreatedDate
    private String createDate;

    private String category; // 카테고리

    @ColumnDefault("0")
    private long likeCount; // 좋아요 수를 별도로 기록하는 컬럼.

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

    public void updateBoard(String title, String content, String category){
        this.title =title;
        this.content =content;
        this.category=category;
    }

    public void updateViewCount(){
        viewCount++;
    }

    public void subViewCount(){
        viewCount--;
    }

    public void recordLikeCount(long likeCount){
        this.likeCount = likeCount;
    }

    @PrePersist   //DB에 해당 테이블을 insert 연산 수행하기 전에 수행
    public void onPrePersist() {
        this.createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd a KK : mm",  Locale.KOREAN));
    }
}
