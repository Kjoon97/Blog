package com.kang.blog.service;

import com.kang.blog.dto.BoardLikeDto;
import com.kang.blog.dto.BoardLikeResponseDto;
import com.kang.blog.model.Board;
import com.kang.blog.model.BoardLike;
import com.kang.blog.model.User;
import com.kang.blog.repository.BoardLikeRepository;
import com.kang.blog.repository.BoardRepository;
import com.kang.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //좋아요, 취소
    public Boolean pushLikeButton(BoardLikeDto boardLikeDto){
        Board board = getBoard(boardLikeDto);
        boardLikeRepository.exist(boardLikeDto.getUserId(), boardLikeDto.getBoardId())
                .ifPresentOrElse(
                        //좋아요 있을 경우 삭제
                        boardLike -> boardLikeRepository.deleteById(boardLike.getId()),
                        //좋아요 없을 경우 생성
                        () ->{
                            User user = getUser(boardLikeDto);
                            boardLikeRepository.save(new BoardLike(user,board));
                        });
        //좋아요 버튼 클릭으로 인한 새로고침으로 조회수가 자동으로 증가해서 1씩 직접 차감함.(update문으로 동시성 제어)
        boardRepository.updateViewCount(boardLikeDto.getBoardId());
        //이왕 board 조회하는 김에 board테이블에 최신화된 좋아요 수 기록하기.
        Long boardLikeNum = boardLikeRepository.findBoardLikeNum(boardLikeDto.getBoardId());
        board.recordLikeCount(boardLikeNum);   //영속된 객체 변경 -> 변경 감지.
        return true;
    }

    //게시물 조회
    @Transactional(readOnly = true)
    public Board getBoard(BoardLikeDto boardLikeDto){
        return boardRepository.findById(boardLikeDto.getBoardId()).orElseThrow(()->new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }

    //유저 조회
    @Transactional(readOnly = true)
    public User getUser(BoardLikeDto boardLikeDto){
        return userRepository.findById(boardLikeDto.getUserId()).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
    }

    //좋아요 정보 조회
    @Transactional(readOnly = true)
    public BoardLikeResponseDto getBoardLikeInfo(BoardLikeDto boardLikeDto){
        long boardLikeNum = getBoardLikeNum(boardLikeDto);
        boolean check = checkPushedLike(boardLikeDto);

        return new BoardLikeResponseDto(boardLikeNum,check);
    }

    //누른적 있는지 확인
    @Transactional(readOnly = true)
    public boolean checkPushedLike(BoardLikeDto boardLikeDto){
        return boardLikeRepository.exist(boardLikeDto.getUserId(),boardLikeDto.getBoardId())
                .isPresent();
    }

    //좋아요 개수 확인
    @Transactional(readOnly = true)
    public long getBoardLikeNum(BoardLikeDto boardLikeDto){
        return boardLikeRepository.findBoardLikeNum(boardLikeDto.getBoardId());
    }

    //게시물 삭제 전에 좋아요 삭제.
    public void deleteBoardLike(Long id){
        boardLikeRepository.deleteByBoardId(id);
    }
}
