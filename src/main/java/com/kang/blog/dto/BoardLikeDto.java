package com.kang.blog.dto;

import com.kang.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardLikeDto {
    //ajax로 부터 받는 좋아요 관련 정보.

    private Long userId;      //사용자 user id
    private Long boardId;     //좋아요 한 게시물 id
}
