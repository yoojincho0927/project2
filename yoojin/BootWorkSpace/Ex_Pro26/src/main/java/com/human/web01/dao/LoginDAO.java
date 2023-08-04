package com.human.web01.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.human.web01.vo.LoginVO;

@Mapper
public interface LoginDAO {

//회원가입
	void insert(LoginVO vo);

//목록보기
	List<LoginVO> selectAll();

//DB에 authKey저장
	void createAuthKey(HashMap<String, String> map);

//이메일인증 후 mem_auth 1로 변경
	void updateAuth(String email);

//아이디 중복확인	
	int selectByUseridCount(String loginid);

//이메일 중복확인	
	int selectByEmail(String email);

//1개 갖고오기
	LoginVO selectByIdx(int idx);

//아이디 갖고오기
	LoginVO selectByUserid(String loginid);

	// 이메일로 아이디 찾기
	LoginVO selectFindId(String email);

//이메일이 일치하면 비밀번호 업데이트
	void updatePassword(HashMap<String, String> map);

//현재 비밀번호 와 일치하면 업데이트
	void updatePass(HashMap<String, String> map);

//나머지 정보 수정
	void updateInfo(LoginVO loginVO);

//로그아웃
	void delete(LoginVO loginVO);
}
