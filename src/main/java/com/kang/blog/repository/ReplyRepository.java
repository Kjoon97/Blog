package com.kang.blog.repository;

import com.kang.blog.dto.ReplySaveRequestDto;
import com.kang.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?,?,?,now())", nativeQuery = true)
    void commentSave(Long userId, Long boardId, String content);
}
