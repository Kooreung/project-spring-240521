package com.projectspring240521.controller.comment;

import com.projectspring240521.domain.comment.Comment;
import com.projectspring240521.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    final CommentService service;

    @PostMapping("add")
    @PreAuthorize("isAuthenticated()")
    public void addComment(@RequestBody Comment comment,
                           Authentication authentication) {
        service.add(comment, authentication);
    }

    @GetMapping("list/{boardId}")
    public List<Comment> list(@PathVariable Integer boardId) {
        return service.list(boardId);
    }
}
