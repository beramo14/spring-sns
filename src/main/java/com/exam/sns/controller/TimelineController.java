package com.exam.sns.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exam.sns.dto.LikeDTO;
import com.exam.sns.model.Comment;
import com.exam.sns.model.Like;
import com.exam.sns.model.Member;
import com.exam.sns.model.Post;
import com.exam.sns.service.LikeService;
import com.exam.sns.service.MemberService;
import com.exam.sns.service.PostService;

@Controller
public class TimelineController {

	@Autowired
    private PostService postService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private LikeService likeService;
	

    @GetMapping("/")
    public String home(Model model, Principal principal) throws Exception {
    	if(principal != null) {
    		String loggedInUsername = principal.getName();
    		Member loggedInUser = memberService.getMemberByEmail(loggedInUsername);
    		model.addAttribute("loggedInUser", loggedInUser);
    	} else {
    		model.addAttribute("loggedInUser", new Member());
    	}
    	
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "timeline";
    }
 
    @PostMapping("/posts/create")
    public String createPost(Post post) {
        postService.createPost(post);
        return "redirect:/";
    }

    @PostMapping("/posts/{postId}/comments")
    public String createComment(@PathVariable("postId") Long postId, @ModelAttribute("comment") Comment comment) throws NotFoundException {
        postService.createComment(postId, comment);
        return "redirect:/";
    }
    
    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
    	System.out.println("#############id = "+id);
    	postService.deletePost(id);
    	
    	return "redirect:/";
    }

    @GetMapping("/post/like/{id}")
    @ResponseBody
    public ResponseEntity<?> likePost(@PathVariable("id") Long id, @RequestParam("like") boolean isLike, Principal principal) throws Exception {

        if(principal == null) {
            return new ResponseEntity<String>("not logged", HttpStatus.BAD_REQUEST);
    	}

        System.out.println("id : "+id+", isLike : "+isLike);

        Post post = postService.findById(id);
        if(isLike == true) {
        	Member member = memberService.getMemberByEmail(principal.getName());
        	if(likeService.likeDuplicationCheck(member.getId(), id) == false) {
        		likeService.addPostLike(post, member);
        	} else {
//        		return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
        	}
        } else if(isLike == false) {
        	Like findLike = likeService.findByPostId(post);
        	likeService.deletePostLike(findLike);
        }
        
        List<Like> likes = likeService.findAllLikes(id);
        List<LikeDTO> likesToView = LikeDTO.convertToDTOList(likes);

        return new ResponseEntity<List<LikeDTO>>(likesToView, HttpStatus.OK);
    }

    @GetMapping("/post/like/{id}/get")
    @ResponseBody
    public ResponseEntity<List<LikeDTO>> getPostLike(@PathVariable("id") Long id) {
    	List<Like> likes = likeService.findAllLikes(id);
    	List<LikeDTO> likesToView = LikeDTO.convertToDTOList(likes);
    	return new ResponseEntity<List<LikeDTO>>(likesToView, HttpStatus.OK);
    }
    
}
