package com.human.web01.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.web01.dao.TestDAO;
import com.human.web01.vo.TestVO;

@Service("testService")
public class TestServiceImpl implements TestService{

	@Autowired
	private TestDAO testDAO;

	@Override
	public String selectToday1() {
		return testDAO.selectToday1();
	}

	@Override
	public Date selectToday2() {
		return testDAO.selectToday2();
	}

	@Override
	public TestVO selectToday3() {
		return testDAO.selectToday3();
	}
	
}
