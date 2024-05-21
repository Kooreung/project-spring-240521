package com.projectspring240521.controller.board;

import com.projectspring240521.domain.board.Board;
import com.projectspring240521.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @PostMapping("add")
    public ResponseEntity add(@RequestBody Board board) throws InterruptedException {

        // 요청 보내고 1초 정도 후에 응답.
        // Thread.sleep(1000);

        // 내용이 비어있다면 badRequest Return
        if (service.validate(board)) {
            service.add(board);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("list")
    public List<Board> list() {
        return service.list();
    }
}
