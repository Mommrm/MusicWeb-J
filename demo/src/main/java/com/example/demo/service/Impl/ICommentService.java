package com.example.demo.service.Impl;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Result;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

public class ICommentService implements CommentService {
    @Autowired
    private CommentMapper commentMapper;


    @Override
    public Result SubmitComment(Comment comment) {
        System.out.println(comment.getComment());
        comment.setComment_date();
        comment.setCommentLikes(0);
        comment.setUserName(comment.getUserName());
        try {
            int i = commentMapper.insertComment(comment);
            if (i>0) {
                return Result.ok("发送评论成功");
            }
            return Result.fail("发送评论失败");
        }catch (Exception e){
            return Result.fail("发送评论异常");
        }
    }
}
