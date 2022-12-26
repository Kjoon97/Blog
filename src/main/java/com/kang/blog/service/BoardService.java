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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        board.setterUser(user);
        boardRepository.save(board);
    }

    //글 목록
    @Transactional(readOnly = true)
    public List<Board> boardList(){
        return boardRepository.findAll();
    }

    //게시물 상세보기(+조회 수 증가)
    @Transactional
    public Board readDetail(int id){
        //게시물 찾기.
        Board board = boardRepository.findByIdForUpdate(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글 상세보기 실패: 해당 게시물을 찾을 수 없습니다.");
                });
        //조회 수 증가
        board.updateViewCount();
//        boardRepository.updateViewCount(id);
        System.out.println("게시물 조회수 = " + board.getViewCount());
        return board;
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

    //댓글 삭제하기
    @Transactional
    public void deleteReply(int replyId){
        replyRepository.deleteById(replyId);
    }

    //게시물 페이징, 검색
    public Page<Board> findBoardPage(Pageable pageable, String searchText) {
        Page<Board> boardPage = boardRepository.searchPage(pageable, searchText);
        return boardPage;
    }

    //게시글 조회
    public Board findBoard(int id){
        Board board = boardRepository.findById(id).get();
        return board;
    }
}
