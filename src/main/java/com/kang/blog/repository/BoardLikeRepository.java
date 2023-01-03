package com.kang.blog.repository;

import com.kang.blog.model.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long>, BoardLikeCustomRepository {

    void deleteByBoardId(Long id);
}
