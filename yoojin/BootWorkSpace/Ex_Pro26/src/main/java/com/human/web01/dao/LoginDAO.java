package com.human.web01.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.human.web01.vo.LoginVO;

@Mapper
public interface LoginDAO {

	LoginVO selectByIdx(int idx) throws SQLException;

	void insert(LoginVO vo) throws SQLException;
}
