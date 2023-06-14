package com.exam.sns.service;

import com.exam.sns.model.Comment;

public interface CommentService {
	Comment findById(Long id);

	void updateComment(Comment findComment);
}
