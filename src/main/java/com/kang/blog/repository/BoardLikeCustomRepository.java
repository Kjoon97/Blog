package com.kang.blog.repository;

import com.kang.blog.model.BoardLike;

import java.util.Optional;

public interface BoardLikeCustomRepository {

    //좋아요 누른 적 있는지 확인
    public Optional<BoardLike> exist(Long userId, Long boardId);

    //해당 게시물의 좋아요 총 개수
    public Long findBoardLikeNum(Long boardId);
}
