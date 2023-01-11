package com.kang.blog.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardDTO {

    private String title;
    private String createDate;
    private int countView;

    public BoardDTO(String title, String createDate, int countView) {
        this.title = title;
        this.createDate = createDate;
        this.countView = countView;
    }
}
