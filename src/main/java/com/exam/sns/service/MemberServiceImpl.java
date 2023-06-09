package com.exam.sns.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.sns.model.Member;
import com.exam.sns.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void insertMember(Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		memberRepo.save(member);
	}

	@Override
	public Member getMemberByEmail(String loggedInUsername) {
		Optional<Member> findMember = memberRepo.findByEmail(loggedInUsername);
		
		if(findMember.isPresent()) {
			return findMember.get();
		} else {
			throw new UsernameNotFoundException(loggedInUsername + " : x");
		}
		
	}

}
