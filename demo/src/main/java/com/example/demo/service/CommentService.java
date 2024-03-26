package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Result;
import org.springframework.stereotype.Service;


public interface CommentService {

    public Result SubmitComment(Comment comment);
}
