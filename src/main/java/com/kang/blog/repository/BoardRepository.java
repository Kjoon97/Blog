package com.kang.blog.repository;

import com.kang.blog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    //조회수 차감 - 동시성 문제 해결법- Udate 쿼리 방법( '좋아요' 버튼 클릭으로 인한 새로고침으로 조회수가 자동으로 증가해서 1씩 직접 차감함.)
    @Modifying   // Read가 아닌 C,U,D의 경우 @Modifying이 필요.
    @Query(value = "UPDATE Board b SET b.viewCount = b.viewCount - 1 WHERE b.id = :id", nativeQuery = true)
    void updateViewCount(@Param("id") Long id);

    //조회수 증가 - 동시성 문제 해결법- 비관적 락 방법
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT b FROM Board b WHERE b.id = :id")
    Optional<Board> findByIdForUpdate(@Param("id") Long id);
}
