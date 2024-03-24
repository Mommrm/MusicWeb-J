package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Result;

public interface CommentService {

    public Result SubmitComment(Comment comment);
}
