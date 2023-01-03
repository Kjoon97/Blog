package com.kang.blog.repository;

import com.kang.blog.model.BoardLike;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.Optional;

import static com.kang.blog.model.QBoardLike.*;

public class BoardLikeCustomRepositoryImpl implements BoardLikeCustomRepository{

    JPAQueryFactory jpaQueryFactory;

    public BoardLikeCustomRepositoryImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    //좋아요 누른 적 있는지 확인
    @Override
    public Optional<BoardLike> exist(Long userId, Long boardId) {
        BoardLike bLike = jpaQueryFactory
                .selectFrom(boardLike)
                .where(boardLike.user.id.eq(userId),
                        boardLike.board.id.eq(boardId))
                .fetchOne();

        return Optional.ofNullable(bLike);
    }

    //해당 게시물의 좋아요 총 개수
    @Override
    public Long findBoardLikeNum(Long boardId) {
        Long count = jpaQueryFactory
                .select(Wildcard.count)
                .from(boardLike)
                .where(boardLike.board.id.eq(boardId))
                .fetchOne();

        return count;
    }
}
