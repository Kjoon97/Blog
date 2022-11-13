package com.kang.blog.controller.api;

import com.kang.blog.config.auth.PrincipalDetails;
import com.kang.blog.dto.ReplySaveRequestDto;
import com.kang.blog.dto.ResponseDto;
import com.kang.blog.model.Board;
import com.kang.blog.model.Reply;
import com.kang.blog.model.User;
import com.kang.blog.service.BoardService;
import com.kang.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    //글 작성
    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetails principal){
        boardService.register(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //글 삭제
    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id){
        boardService.delete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //글 수정하기
    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
        boardService.updateBoard(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    //댓글 작성
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto, @AuthenticationPrincipal PrincipalDetails principal){
        boardService.registerReply(replySaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //댓글 삭제
    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
        boardService.deleteReply(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
