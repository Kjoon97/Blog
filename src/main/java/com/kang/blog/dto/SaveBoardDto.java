package com.kang.blog.dto;

import com.kang.blog.model.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveBoardDto {

    private String title;
    private String content;
    private String category;

    @Builder
    public SaveBoardDto(String title, String content, String category){
        this.title = title;
        this.content =content;
        this.category=category;
    }

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .category(category)
                .build();
    }
}
