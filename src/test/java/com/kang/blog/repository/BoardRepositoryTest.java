package com.kang.blog.repository;

import com.kang.blog.model.Board;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    //테스트 종료 후 db 초기화
    @AfterEach
    public void cleanup(){
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 게시물 조회 테스트")
    public void FindAll_Test(){
        //given
        String title= "테스트 글";
        String content= "테스트 본문";

        boardRepository.save(Board.builder()
                .title(title)
                .content(content).build());

        //when
        List<Board> boards = boardRepository.findAll();

        //then
        Board board = boards.get(0);
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);
    }

    @Test
    @Transactional
    @DisplayName("비관적 락 게시물 조회 테스트")
    public void FindByIdForUpdate_Test(){
        //given
        String title= "테스트 글";
        String content= "테스트 본문";

        Board savedBoard = boardRepository.save(Board.builder()
                .title(title)
                .content(content).build());

        //when
        Board testBoard = boardRepository.findByIdForUpdate(savedBoard.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("글 상세보기 실패: 해당 게시물을 찾을 수 없습니다.");
        });

        //then
        assertThat(testBoard.getTitle()).isEqualTo(title);
        assertThat(testBoard.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("게시물 찾기 테스트")
    public void FindBoardById_Test(){
        //given
        String title= "테스트 글";
        String content= "테스트 본문";

        Board savedBoard = boardRepository.save(Board.builder()
                .title(title)
                .content(content).build());
        //when
        Board testBoard = boardRepository.findById(savedBoard.getId()).get();
        //then
        assertThat(testBoard.getId()).isEqualTo(savedBoard.getId());
        assertThat(testBoard.getTitle()).isEqualTo(title);
        assertThat(testBoard.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("게시물 생성 시간 테스트")
    public void Time_Test(){
        //given
        boardRepository.save(Board.builder()
                .title("title")
                .content("content")
                .build());

        //when
        List<Board> boards = boardRepository.findAll();

        //then
        Board board = boards.get(0);
        System.out.println("게시물 생성 시간 = " + board.getCreateDate());
    }
}