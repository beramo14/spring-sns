package com.exam.sns.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exam.sns.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
	
	@Modifying
	@Transactional
	@Query("update Member m set m.password = :password where m.email = :email")
	void updatePassword(@Param("email") String email, @Param("password") String newPassword);

	Optional<Member> findByName(String name);
}
