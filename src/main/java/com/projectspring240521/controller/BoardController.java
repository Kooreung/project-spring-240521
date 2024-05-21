package com.projectspring240521.controller;

import com.projectspring240521.domain.Board;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    @PostMapping("add")
    public void add(@RequestBody Board board) {
        System.out.println("board = " + board);
    }
}
