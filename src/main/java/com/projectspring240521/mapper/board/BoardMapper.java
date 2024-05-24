package com.projectspring240521.mapper.board;

import com.projectspring240521.domain.board.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Insert("""
            INSERT INTO board (title,content,member_id)
            VALUES (#{title}, #{content}, #{memberId})
            """)
    public int insert(Board board);

    @Select("""
            SELECT board.id, title, member.nick_name writer
            FROM board JOIN member
            ON board.member_id = member.id
            ORDER BY board.id DESC
            """)
    List<Board> selectAll();

    @Select("""
            SELECT board.id, board.title, board.content, board.inserted, member.nick_name writer, board.member_id
            FROM board JOIN member
            ON board.member_id = member.id
            WHERE board.id= #{id}
            """)
    Board selectById(Integer id);

    @Delete("""
            DELETE FROM board
            WHERE member_id=#{memberId}
            """)
    Integer deleteById(Integer id);

    @Update("""
            UPDATE board
            SET title=#{title}, content=#{content}
            WHERE id=#{id}
            """)
    void update(Board board);
}
