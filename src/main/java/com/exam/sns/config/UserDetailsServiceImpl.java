package com.exam.sns.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exam.sns.model.Member;
import com.exam.sns.repository.MemberRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private MemberRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Member> findMember = userRepo.findByEmail(email);
		
		if(findMember.isPresent()) {
			return new SecurityUser(findMember.get());
		} else {
			throw new UsernameNotFoundException(email+" : x");
		}
		
	}

}
