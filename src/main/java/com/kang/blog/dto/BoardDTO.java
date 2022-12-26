package com.kang.blog.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardDTO {

    private String title;
    private Timestamp createDate;
    private int countView;

    public BoardDTO(String title, Timestamp createDate, int countView) {
        this.title = title;
        this.createDate = createDate;
        this.countView = countView;
    }
}
