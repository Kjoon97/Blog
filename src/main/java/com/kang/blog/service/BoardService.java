package com.kang.blog.service;

import com.kang.blog.config.auth.PrincipalDetails;
import com.kang.blog.model.Board;
import com.kang.blog.model.RoleType;
import com.kang.blog.model.User;
import com.kang.blog.repository.BoardRepository;
import com.kang.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    //글 작성
    @Transactional
    public void register(Board board, User user) {
        board.setterCountAndUser(0, user);
        boardRepository.save(board);
    }

    public List<Board> boardList(){
        return boardRepository.findAll();
    }
}
