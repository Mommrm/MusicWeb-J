package com.example.demo.controller;


import com.example.demo.entity.Comment;
import com.example.demo.entity.Result;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/submitComment")
    public Result submitComment(@RequestBody Comment comment){
        return commentService.SubmitComment(comment);
    }
}
