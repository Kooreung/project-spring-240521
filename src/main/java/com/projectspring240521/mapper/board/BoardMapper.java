package com.projectspring240521.mapper.board;

import com.projectspring240521.domain.board.Board;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Insert("""
            INSERT INTO board (title,content,writer)
            VALUES (#{title}, #{content}, #{writer})
            """)
    public int insert(Board board);

    @Select("""
            SELECT id, title, writer
            FROM board
            ORDER BY id DESC
            """)
    List<Board> selectAll();

    @Select("""
            SELECT *
            FROM board
            WHERE id= #{id}
            """)
    Board selectById(Integer id);

    @Delete("""
            DELETE FROM board
            WHERE id = #{id}
            """)
    Integer deleteById(Integer id);
}
