package com.human.web01.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

//CREATE SEQUENCE tb_member_idx_seq;

/*CREATE TABLE tb_member(
idx number(20) NOT NULL PRIMARY KEY,
loginid varchar2(20) NOT NULL, -- 아이디
password varchar2(30) NOT NULL, , -- 비밀번호
name varchar2(20) NOT NULL,  -- 이름
gender varchar(8) check(gender IN('남','여')) NOT NULL,
birthday DATE NOT NULL, -- 생년월일
delete_yn NUMBER NOT NULL, -- 탈퇴여부
create_date date NOT NULL,  -- 최초 생성일
modified_date DATE DEFAULT NULL -- 마지막 수정일
);*/
@Data
public class LoginVO {

	
	
	private int idx; //idx값
	private String loginid;  //실제 로그인 아이디
	private String password; //비밀번호
	private String name;   //이름
	private int gender;  //남,여
	//Date 로 받을때는 @DateTimeformat 을 써줘야 함.
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;  //생년월일
	private int delete_yn; //탈퇴여부
	private Date create_date;  //생성일
	private String email; //이메일
	private String phonenumber; //전화번호
	

}
