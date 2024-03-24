package com.example.demo.mapper;


import com.example.demo.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("Insert Into comment(comment,comment_date,commentLikes,userId,songId,userName) values (#{comment}, #{comment_date}, #{commentLikes}, #{userId}, #{songId}, #{userName})")
    public int insertComment(Comment comment);

    @Select("Select commentId,comment,comment_date,commentLikes,userId,songId,userName From comment Where songId = #{songId}")
    public List<Comment> getSongComment(String songId);

    @Update("Update comment set commentLikes = #{commentLikes} where userId = #{userId} and commentId = #{commentId}")
    public int updateLikes(Comment comment);

}
