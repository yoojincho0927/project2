package com.human.web01.dao;

import org.apache.ibatis.annotations.Mapper;

import com.human.web01.vo.HeartVO;

@Mapper
public interface HeartDAO {
	// 좋아요 기능
	void like(HeartVO heartVO);

	// 중복 방지
	int likeCheck(HeartVO heartVO);

	// 좋아요 취소 기능
	void deleteLike(HeartVO heartVO);


}
