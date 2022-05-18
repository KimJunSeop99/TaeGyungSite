package com.example.taegyungsite.controller;

import com.example.taegyungsite.model.BoardDto;
import com.example.taegyungsite.service.BoardService;
import com.example.taegyungsite.service.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName : com.example.taegyungsite.controller
 * fileName : BoardApiController
 * author : ds
 * date : 2022-05-18
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-05-18         ds          최초 생성
 */
// @RestController : json / text 로 프론트엔드로 전송 Vue , React
// 비교 ) @Controller : html / jsp / 타임리프로 파일로 바꿔서 바로 출력
// 샘플 ) http://localhost:8000/api/board/
// @RequestMapping("/api") : 위의 api를 자동으로 설정해줌
@RestController
@RequestMapping("/api")
public class BoardApiController {
    // 스프링에서 객체를 받아 아래 멤버변수에 넣을 수 있음
    @Autowired
    BoardServiceImpl boardService;

    @GetMapping("/board/write/{idx}")
    public BoardDto openBoardWrite(@PathVariable("idx")Long idx) {
        BoardDto detail = boardService.getBoardDetail(idx);
        return detail;
    }
// @PostMapping : insert 할 때 사용하는 어노테이션
// @RequestBody : 입력테스트시 JSON 형태로 데이터를 전송 할 수 있음
    @PostMapping("/board/register")
    public List<BoardDto> registerBoard(@RequestBody BoardDto boardDto) {
        try{
            System.out.println("Controller boardDto : " + boardDto);
            // insert 문 실행
            boolean isRegistered = boardService.registerBoard(boardDto);

        }catch (DataAccessException e){
            // DB관련된 에러는 여기로 들어옴
            // TODO => DB 처리 과정에 문제가 발생했다는 메세지를 출력

        }catch (Exception e) {
            // DB 외의 에러는 여기로 들어옴
            // TODO => 시스템에 문제가 발생했다는 메세지를 출력
        }

        // insert 완료 후 데이터 확인을 위한 전체 조회 ( select )
        return boardService.getBoardAllList();
    }

    @PutMapping(value = "/board/delete/{idx}")
    public List<BoardDto> deleteBoard(@PathVariable("idx") Long idx){
        boolean isDeleted = boardService.deleteBoard(idx);

        // 삭제 되었는지 전체 조회를 함
        return boardService.getBoardAllList();
    };

    // 페이징 처리를 위한 게시물 검색 메뉴
    @GetMapping("/board/list/cur-page-no/{currentPageNo}/records-per-page{recordsPerPage}")
    public List<BoardDto> openBoardList(BoardDto params) {
        return boardService.getBoardList(params);
    }
}
