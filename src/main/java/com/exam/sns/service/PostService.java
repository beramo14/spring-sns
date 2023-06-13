package com.exam.sns.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.exam.sns.model.Comment;
import com.exam.sns.model.Member;
import com.exam.sns.model.Post;

public interface PostService {

	List<Post> getAllPosts();

	void createPost(Post post);

	void createComment(Long postId, Comment comment) throws NotFoundException;

	List<Post> findByUserId(Long id);

	void deletePost(Long id);

	Post findById(Long id);

}
