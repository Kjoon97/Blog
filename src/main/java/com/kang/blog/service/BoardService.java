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

    //글 목록
    @Transactional(readOnly = true)
    public List<Board> boardList(){
        return boardRepository.findAll();
    }

    //게시물 상세보기
    @Transactional(readOnly = true)
    public Board readDetail(int id){
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패: 해당 게시물을 찾을 수 없습니다.");
                });
    }

    //게시물 삭제하기
    @Transactional
    public void delete(int id){
        boardRepository.deleteById(id);
    }
}
