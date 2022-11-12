package com.kang.blog.service;

import com.kang.blog.config.auth.PrincipalDetails;
import com.kang.blog.dto.ReplySaveRequestDto;
import com.kang.blog.model.Board;
import com.kang.blog.model.Reply;
import com.kang.blog.model.RoleType;
import com.kang.blog.model.User;
import com.kang.blog.repository.BoardRepository;
import com.kang.blog.repository.ReplyRepository;
import com.kang.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

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

    //게시물 수정하기
    @Transactional
    public void updateBoard(int id, Board requestBoard){
        Board findBoard = boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("게시글 수정 실패: 해당 게시물을 찾을 수 없습니다.");
                });;  //조회해서 영속성 컨텍스트 1차 캐시에 영속화 됨.

        findBoard.updateBoard(requestBoard.getTitle(),requestBoard.getContent()); //영속화 객체 변경.
        //해당 함수 종료 -> 트랜잭션 종료(커밋) -> 더티체킹 -> 자동 업데이트
    }

    //댓글 작성하기
    @Transactional
    public void registerReply(ReplySaveRequestDto replySaveRequestDto){
        replyRepository.commentSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
    }
}
