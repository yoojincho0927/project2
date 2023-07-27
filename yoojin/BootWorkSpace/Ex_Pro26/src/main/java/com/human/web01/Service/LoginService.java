package com.human.web01.Service;

import com.human.web01.vo.LoginVO;

public interface LoginService {
	
	LoginVO selectByIdx(int idx);

	void insert(LoginVO vo);

}
