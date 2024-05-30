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
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int insert(Board board);

    @Select("""
            <script>
            SELECT b.id, 
                   b.title,
                   m.nick_name writer,
                   COUNT(f.name) number_of_images,
                    COUNT(DISTINCT l.member_id) number_of_like,
                    COUNT(DISTINCT c.id) number_of_comments
            FROM board b JOIN member m ON b.member_id = m.id
                         LEFT JOIN board_file f ON b.id = f.board_id
                         LEFT JOIN board_like l ON b.id = f.board_id
                         LEFT JOIN comment c ON b.id = c.board_id
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
            GROUP BY b.id
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
            WHERE id=#{id}
            """)
    Integer deleteById(Integer id);

    @Update("""
            UPDATE board
            SET title=#{title}, content=#{content}
            WHERE id=#{id}
            """)
    void update(Board board);

    @Select("""
            <script>
            SELECT COUNT(b.id)
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
            </script>
            """)
    Integer countAllWithSearch(String searchType, String keyword);

    @Insert("""
            INSERT INTO board_file (board_id, name)
            VALUES (#{boardId}, #{name})
            """)
    int insertFileName(Integer boardId, String name);

    @Select("""
            SELECT name 
            FROM board_file
            WHERE board_id=#{boardId}
            """)
    List<String> selectFileNameByBoardId(Integer boardId);

    @Delete("""
            DELETE FROM board_file
            WHERE board_id=#{boardId}
            """)
    int deleteFileByBoardId(Integer boardId);

    @Select("""
            SELECT id
            FROM board
            WHERE member_id=#{memberId}
            """)
    List<Board> selectByMemberId(Integer memberId);

    @Delete("""
            DELETE FROM board_file
            WHERE board_id=#{boardId}
            AND name=#{fileName}
            """)
    void deleteFileByBoardIdAndName(Integer boardId, String fileName);

    @Delete("""
            DELETE FROM board_like
            WHERE board_id=#{boardId}
            AND member_id=#{memberId}
            """)
    int deleteLikeByBoardInAndMemberId(Integer boardId, Integer memberId);

    @Insert("""
            INSERT INTO board_like (board_id, member_id)
            VALUES (#{boardId}, #{memberId})
            """)
    int insertLikeByBoardInAndMemberId(Integer boardId, Integer memberId);

    @Select("""
            SELECT COUNT(*)
            FROM board_like
            WHERE board_id=#{boardId}
            """)
    int selectCountLikeByBoardId(Integer boardId);

    @Select("""
            SELECT COUNT(*) FROM board_like
            WHERE board_id=#{boardId}
            AND member_id=#{memberId}
            """)
    int selectLikeByBoardIdAndMemberId(Integer boardId, String memberId);
}
