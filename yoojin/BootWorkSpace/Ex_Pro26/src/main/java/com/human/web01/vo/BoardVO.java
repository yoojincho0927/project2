package com.human.web01.vo;

import java.util.Date;

import lombok.Data;

/*CREATE TABLE TB_BOARD(
idx NUMBER NOT NULL,  -- 글 번호
loginid varchar(100) NOT NULL, --글 작성자
title  varchar2(50) NOT NULL, -- 제목
content varchar2(1000) NOT NULL, -- 내용
create_date date NOT NULL,-- 등록일
readCount NUMBER DEFAULT 0, -- 조회수
likeCount NUMBER DEFAULT 0, -- 좋아요 수
scrapCount NUMBER DEFAULT 0 -- 스크랩 수
);*/
@Data
public class BoardVO {

	private int idx;
	private String loginid;
	private String title;
	private String content;
	private Date create_date;
	private int readCount;
	private int likeCount;
	private int scrapCount;
}
