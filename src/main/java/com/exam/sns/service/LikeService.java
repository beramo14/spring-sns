package com.exam.sns.service;

import java.util.List;

import com.exam.sns.model.Like;
import com.exam.sns.model.Member;
import com.exam.sns.model.Post;

public interface LikeService {

	Like findByPostId(Post post);

	void addPostLike(Post post, Member member);

	void deletePostLike(Like like);

	List<Like> findAllLikes(Long id);

	boolean likeDuplicationCheck(Long userId, Long postId);

	Like findByPostIdAndMemberId(Post post, Member member) throws Exception;

}
