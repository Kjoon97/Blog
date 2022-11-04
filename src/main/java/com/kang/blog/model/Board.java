package com.kang.blog.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

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

    private int count;   //조회 수

    @CreationTimestamp
    private Timestamp createDate;

    //외래 키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;     //한명의 유저는 다수의 게시글 쓴다.

    @OneToMany(mappedBy = "board")
    private List<Reply> replies;

    public void setterCountAndUser(int count, User user){
        this.count = count;
        this.user = user;
    }
}
