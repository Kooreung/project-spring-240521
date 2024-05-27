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
            <script>
            SELECT b.id, 
                   b.title,
                   m.nick_name writer
            FROM board b JOIN member m ON b.member_id = m.id
               <trim prefix="WHERE" prefixOverrides="OR">
                   <if test="searchType != null">
                       <bind name="pattern" value="'%' + keyword + '%'" />
                       <if test="searchType == 'all' || searchType == 'text'">
                           OR b.title LIKE #{pattern}
                           OR b.content LIKE #{pattern}
                       </if>
                       <if test="searchType == 'all' || searchType == 'nickName'">
                           OR m.nick_name LIKE #{pattern}
                       </if>
                   </if>
               </trim>
            ORDER BY b.id DESC
            LIMIT #{offset}, 10
            </script>
            """)
    List<Board> selectAllPaging(Integer offset, String searchType, String keyword);

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

    @Select("""
            SELECT COUNT(*) FROM board
            """)
    Integer countAll();
}
