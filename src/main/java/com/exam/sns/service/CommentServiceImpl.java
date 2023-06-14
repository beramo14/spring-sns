package com.exam.sns.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.sns.model.Comment;
import com.exam.sns.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository commentRepo;

	@Override
	public Comment findById(Long id) {
		Optional<Comment> findComment = commentRepo.findById(id);
				
		if(findComment.isPresent()) {
			return findComment.get();
		}
		return null;
	}

	@Override
	public void updateComment(Comment findComment) {
		commentRepo.save(findComment);
	}
	
	

}
