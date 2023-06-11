package com.exam.sns.service;

import com.exam.sns.model.Member;

public interface MemberService {

	void insertMember(Member member);

	Member getMemberByEmail(String loggedInUsername) throws Exception;

	void updateMember(Member member) throws Exception;

}
