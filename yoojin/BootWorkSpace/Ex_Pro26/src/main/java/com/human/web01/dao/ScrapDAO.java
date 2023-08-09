package com.human.web01.dao;

import org.apache.ibatis.annotations.Mapper;

import com.human.web01.vo.ScrapVO;

@Mapper
public interface ScrapDAO {
	// 스크랩 기능
	void scrap(ScrapVO scrapVO);

	// 중복 방지
	int scrapCheck(ScrapVO scrapVO);

	// 스크랩 취소 기능
	void notscrap(ScrapVO scrapVO);

}
