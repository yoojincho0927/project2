package com.human.web01.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;

import com.human.web01.vo.TestVO;

@Mapper // 이것을 적어야 내부적으로 DAOImpl이 생기면서 testDAO를 만들어 등록해 준다.
public interface TestDAO {
	String selectToday1();
	Date   selectToday2();
	TestVO selectToday3();
}
