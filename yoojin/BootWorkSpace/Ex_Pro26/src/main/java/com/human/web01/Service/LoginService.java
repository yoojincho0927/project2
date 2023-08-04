package com.human.web01.Service;

import java.util.List;

import com.human.web01.vo.LoginVO;

public interface LoginService {

	// 가입하기
	void insert(LoginVO vo);

	// 모두얻기
	List<LoginVO> selectList();

	// 이메일인증 후 mem_auth 1로 변경
	public void updateAuth(String email);

	// 아이디 중복확인
	int selectByUseridCount(String loginid);

	// 이메일 중복확인
	int selectByEmail(String email);

	// 1개 갖고오기
	LoginVO selectByIdx(int idx);

	// 아이디 갖고오기
	LoginVO selectByUserid(String loginid);

	// 로그인 체크
	boolean loginCheck(String loginid, String password);

	// 이메일 있으면 이메일로 아이디 전송, 없으면
	boolean emailCheck(String email);

	// 이메일 있으면 이메일로 임시비밀번호 전송, 없으면
	boolean emailCheck2(String email);

	// 이메일로 아이디 찾기
	LoginVO selectFindId(String email);

	// hashmap으로 넘긴값
	boolean passwordCheck(LoginVO loginVO, String newPassword);

	// 나머지 정보 수정
	void updateInfo(LoginVO loginVO);

	// hashmap으로 넘긴값
	boolean passwordCheck2(LoginVO loginVO);

}
