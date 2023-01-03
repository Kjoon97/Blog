package com.kang.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardLikeResponseDto {

    private long BoardLikeNum;  //해당 게시물 좋아요 수
    private boolean check;      //체크 사항 true, false.
}
