package com.exam.sns.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Table(name = "members")
@Data
@DynamicUpdate
public class Member {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(nullable = false)
	private String password;
	
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;
    
    private String bio;
    
    private String profilePhoto;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false, columnDefinition = "varchar(12) DEFAULT 'ROLE_USER'")
    private Role role;
  
}
