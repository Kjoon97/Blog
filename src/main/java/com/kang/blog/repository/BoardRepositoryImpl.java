package com.kang.blog.repository;

import com.kang.blog.model.Board;
import com.kang.blog.model.QBoard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kang.blog.model.QBoard.board;

public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Board> searchPage(Pageable pageable) {

        //게시물 리스트, 페이징
        List<Board> content = queryFactory
                .select(board)
                .from(board)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //countquery 최적화
        JPAQuery<Board> countQuery = queryFactory
                .select(board)
                .from(board);

        return PageableExecutionUtils.getPage(content, pageable, ()-> countQuery.fetch().size());
    }
}
