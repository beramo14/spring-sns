package com.exam.sns.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.exam.sns.model.Comment;
import com.exam.sns.model.Member;
import com.exam.sns.model.Post;
import com.exam.sns.service.MemberService;
import com.exam.sns.service.PostService;

@Controller
public class TimelineController {

	@Autowired
    private PostService postService;
	
	@Autowired
	private MemberService memberService;
	

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
    
}
