package com.projectspring240521.mapper.comment;

import com.projectspring240521.domain.comment.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("""
            INSERT INTO comment
            (board_id, member_id, comment)
            VALUES (#{boardId}, #{memberId}, #{comment})
            """)
    int insert(Comment comment);

    @Select("""
            SELECT c.id, c.comment, c.inserted, m.nick_name
            FROM comment c JOIN member m ON c.member_id = m.id
            WHERE c.board_id = #{boardId}
            ORDER BY id DESC 
            """)
    List<Comment> selectAllByBoardId(Integer boardId);

    @Delete("""
            DELETE FROM comment
            WHERE id = #{id}
            """)
    int deleteById(Integer id);

    @Select("""
            SELECT *
            FROM comment
            WHERE id = #{id}
            """)
    Comment selectById(Integer id);
}