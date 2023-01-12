package com.kang.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ResponseDto<T> {
    int statusCode;
    T data;

    //상태 코드만 리턴하고 싶을 때 사용.
    public ResponseDto(int statusCode){
        super();
        this.statusCode = statusCode;
    }

    //상태 코드, 데이터를 리턴 하고 싶을 때 사용.
    public ResponseDto(int statusCode, T data){
        super();
        this.statusCode =statusCode;
        this.data =data;
    }
}
