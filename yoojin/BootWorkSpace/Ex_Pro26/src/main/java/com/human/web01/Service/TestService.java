package com.human.web01.Service;

import java.util.Date;

import com.human.web01.vo.TestVO;

public interface TestService {
	String selectToday1();
	Date   selectToday2();
	TestVO selectToday3();
}
