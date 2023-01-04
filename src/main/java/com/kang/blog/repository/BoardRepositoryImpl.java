package com.kang.blog.repository;

import com.kang.blog.model.Board;
import com.kang.blog.model.QBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    public Page<Board> searchPage(Pageable pageable, String searchText, String category) {

        //게시물 조회, 페이징, 검색, 카테고리
        List<Board> content = queryFactory
                .select(board)
                .from(board)
                .where(searchContain(searchText), findByCategory(category))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //countquery 최적화
        JPAQuery<Board> countQuery = queryFactory
                .select(board)
                .from(board)
                .where(searchContain(searchText), findByCategory(category));

        return PageableExecutionUtils.getPage(content, pageable, ()-> countQuery.fetch().size());
    }

    //검색어가 존재하면 contains 작동, 없으면 null 반환.
    private BooleanExpression searchContain(String searchText) {
        return searchText != null ? board.title.contains(searchText) :null;
    }

    //category 존재하면 eq()작동, 없으면 null 반환.
    private BooleanExpression findByCategory(String category) {
        return category != null ? board.category.eq(category) :null;
    }
}
