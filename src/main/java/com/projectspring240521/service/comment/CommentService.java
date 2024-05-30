package com.projectspring240521.service.comment;

import com.projectspring240521.domain.comment.Comment;
import com.projectspring240521.mapper.comment.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CommentService {
    final CommentMapper mapper;

    public void add(Comment comment, Authentication authentication) {
        comment.setMemberId(Integer.valueOf(authentication.getName()));

        mapper.insert(comment);
    }

    public List<Comment> list(Integer boardId) {
        return mapper.selectAllByBoardId(boardId);
    }
}
