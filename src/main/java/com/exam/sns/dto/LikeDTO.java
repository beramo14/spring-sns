package com.exam.sns.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.exam.sns.model.Like;
import com.exam.sns.model.LikeType;

import lombok.Data;

@Data
public class LikeDTO {
	private Long id;
	private Long commentId;
	private Long postId;
	private Long userId;
	private String userName;
	private String userProfilePhoto;
	private LikeType type;
	
	public LikeDTO(){}
	public LikeDTO(Like like) {
		this.id = like.getId();
		this.commentId = like.getComment() != null ? like.getComment().getId() : null;
        this.postId = like.getPost() != null ? like.getPost().getId() : null;
        this.userId = like.getUser() != null ? like.getUser().getId() : null;
		this.userName = like.getUser().getName();
		this.userProfilePhoto = like.getUser() != null ? like.getUser().getProfilePhoto() : null;
		this.type = like.getType();
	}
	
	public static List<LikeDTO> convertToDTOList(List<Like> likes) {
        return likes.stream()
                    .map(LikeDTO::new)
                    .collect(Collectors.toList());
    }
	
}

//@Data
//class PostDTO {
//	private Long id;
//	private Long userId;
//	private Long ;
//}