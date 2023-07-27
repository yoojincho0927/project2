package com.human.web01.Service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.web01.dao.LoginDAO;
import com.human.web01.vo.LoginVO;

import lombok.extern.slf4j.Slf4j;

@Service("loginService")
@Slf4j
public class LoginServiceImpl implements LoginService {

	
	@Autowired
	LoginDAO loginDAO;

	@Override
	public LoginVO selectByIdx(int idx) {
		LoginVO vo = null;

		try {
			vo = loginDAO.selectByIdx(idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;
	}

	@Override
	public void insert(LoginVO vo) {
		
		try {
			loginDAO.insert(vo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
