package com.human.web01.vo;

import lombok.Data;

/*-- 좋아요 테이블
CREATE TABLE heart(
heart NUMBER  DEFAULT 0,  --  좋아요 기능 
idx NUMBER  DEFAULT 0,   --  좋아요 개수 세기
tb_ref NUMBER NOT NULL, -- 좋아요 된 게시물의 idx번호 
login_id varchar2(20) NOT NULL -- 좋아요 한 사용자 login_id 
);*/
/*--  좋아요된 게시물의 idx번호화 같게 한다

SELECT * FROM  heart, TB_BOARD tb  WHERE tb.IDX =HEART.TB_REF; 

--  좋아요한 사용자의 loginid와  같게 한다
SELECT * FROM HEART h ,TB_MEMBER tm WHERE tm.LOGINID = h.LOGIN_ID;*/

@Data
public class HeartVO {


	private int idx;
	private int tb_ref; //게시판 번호
	private String login_id;//회원 로그인 아이디

}
