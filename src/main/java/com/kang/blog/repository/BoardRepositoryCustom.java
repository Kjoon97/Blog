package com.kang.blog.repository;

import com.kang.blog.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<Board> searchPage(Pageable pageable, String searchText, String category);
}
