package com.human.web01.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

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

	@Autowired
	MailService mailService;

	// 랜덤 인증키 생성
	public class Tempkey {
		private boolean lowerCheck;
		private int size;

		public String getKey(int size, boolean lowerCheck) {
			this.size = size;
			this.lowerCheck = lowerCheck;
			return init();
		}

		private String init() {
			Random ran = new Random();
			StringBuffer sb = new StringBuffer();
			int num = 0;
			do {
				num = ran.nextInt(75) + 48;
				if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
					sb.append((char) num);
				} else {
					continue;
				}
			} while (sb.length() < size);
			if (lowerCheck) {
				return sb.toString().toLowerCase();
			}
			return sb.toString();
		}

	}

	@Override
	public LoginVO selectByIdx(int idx) {
		LoginVO vo = null;

		try {
			vo = loginDAO.selectByIdx(idx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;
	}

	@Override
	public void insert(LoginVO vo) {

		try {
			// DB에 회원가입 저장
			loginDAO.insert(vo);

			// 인증키 생성
			String key = new Tempkey().getKey(10, false);

			// 인증키 DB에 저장
			HashMap<String, String> map = new HashMap<>();
			map.put("email", vo.getEmail());
			map.put("authkey", key);
			loginDAO.createAuthKey(map);

			// 메일 보내기
			// from , to, subject ,content
			String from = "yudinjo@gmail.com";
			String toMail = vo.getEmail();
			String subject = "홈페이지를 방문해주셔서 감사합니다";

			String sb = "<h1>가입 이메일 인증입니다.</h1>" + "<a href='http://localhost:8080/member/emailConfirm?email="
					+ vo.getEmail() + "&key=" + key + "target='_blank'>가입완료를 위해 이곳을 눌러주세요</a>";

			mailService.mailSend(from, toMail, subject, sb);

			log.info("{} 저장 성공!!!", vo);

		} catch (Exception e) {
			log.info("{} 저장 실패!!!", vo);
			e.printStackTrace();
		}

	}

	@Override
	public List<LoginVO> selectList() {
		// TODO Auto-generated method
		List<LoginVO> list = null;
		try {
			list = loginDAO.selectAll();
			log.info("모두 얻기 성공 : {}", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("모두 얻기 실패 : {}", list);

			e.printStackTrace();
		}
		return list;
	}

	// 이메일인증 후 mem_auth 1로 변경
	@Override
	public void updateAuth(String email) {
		loginDAO.updateAuth(email);

	}

	// 아이디 중복확인
	@Override
	public int selectByUseridCount(String loginid) {
		int count = 0;
		try {
			count = loginDAO.selectByUseridCount(loginid);
			log.info("id개수  얻기 성공 : {}", count);

		} catch (Exception e) {
			log.info("id개수  얻기실패");
			e.printStackTrace();
		}
		return count;
	}

	// 이메일 중복확인
	@Override
	public int selectByEmail(String email) {

		int count = 0;
		try {
			count = loginDAO.selectByEmail(email);
			log.info("id개수  얻기 성공 : {}", count);

		} catch (Exception e) {
			log.info("id개수  얻기실패");
			e.printStackTrace();
		}
		return count;
	}

	// 로그인 아이디 갖고 오기
	@Override
	public LoginVO selectByUserid(String loginid) {
		LoginVO vo = null;
		try {
			vo = loginDAO.selectByUserid(loginid);
			log.info("id  얻기 성공 : {}", vo);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("id  얻기 실패 : {}", vo);
		}
		return vo;
	}

	// 아이디,비밀번호 체크
	@Override
	public boolean loginCheck(String loginid, String password) {
		boolean isLogin = false;
		try {
			LoginVO vo = loginDAO.selectByUserid(loginid); // 아이디 1개 넘어온 값
			if (vo != null && vo.getPassword().equals(password)) {
				// 아이디와 패스워드가 같으면 true 로 반환
				isLogin = true;
				log.info("로그인하기 성공 : {}", vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("로그인하기 실패: {}", isLogin);
		}
		return isLogin;
	}

//이메일로 아이디찾기
	@Override
	public LoginVO selectFindId(String email) {

		LoginVO vo = null;
		try {
			vo = loginDAO.selectFindId(email);

			// 메일 보내기

			log.info("모두 얻기 성공 : {}", vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("모두 얻기 실패 : {}", vo);

			e.printStackTrace();
		}
		return vo;
	}

	// 이메일 체크 -아이디
	@Override
	public boolean emailCheck(String email) {
		boolean isEmail = false;
		try {
			LoginVO vo = loginDAO.selectFindId(email); // 이메일 1개 넘어온 값
			if (vo != null && vo.getEmail().equals(email)) {
				// 이메일이 같으면 true 로 반환
				isEmail = true;

				// 메일 보내기
				// from , to, subject ,content
				String from = "yudinjo@gmail.com";
				String toMail = vo.getEmail();
				String subject = "홈페이지를 방문해주셔서 감사합니다";

				String sb = "<h1>찾으시는 아이디 입니다.</h1>" + vo.getLoginid();

				mailService.mailSend(from, toMail, subject, sb);

				log.info("이메일찾기 성공 : {}", vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("로그인하기 실패: {}", isEmail);
		}
		return isEmail;

	}

	// 이메일 체크 -비밀번호
	@Override
	public boolean emailCheck2(String email) {

		boolean isEmail = false;
		try {
			LoginVO vo = loginDAO.selectFindId(email); // 이메일 1개 넘어온 값
			if (vo != null && vo.getEmail().equals(email)) {
				// 이메일이 같으면 true 로 반환
				isEmail = true;

				// 인증키 생성
				String key = new Tempkey().getKey(10, false);

				// 인증키 DB에 저장
				HashMap<String, String> map = new HashMap<>();
				map.put("loginid", vo.getLoginid());
				map.put("password", key);
				// 임시 비밀번호로 업데이트
				loginDAO.updatePassword(map);

				// 메일 보내기
				// from , to, subject ,content
				String from = "yudinjo@gmail.com";
				String toMail = vo.getEmail();
				String subject = "홈페이지를 방문해주셔서 감사합니다";

				String sb = "<h1>임시비밀번호입니다.</h1>" + key + "로그인 후에 비밀번호를 변경해주세요";

				mailService.mailSend(from, toMail, subject, sb);

				log.info("비밀번호찾기 성공 : {}", vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("비밀번호찾기 실패: {}", isEmail);
		}
		return isEmail;

	}

//비밀번호 체크해주는 메서드
	@Override
	public boolean passwordCheck(LoginVO loginVO, String newPassword) {
		boolean isLogin = false;
		try {
			// DB에있는 password와 현재loginVO의password 값이 같은지 판단여부
			LoginVO dbVO = loginDAO.selectByUserid(loginVO.getLoginid()); // 아이디 1개 넘어온 값
			if (dbVO != null && dbVO.getPassword().equals(loginVO.getPassword())) {
				// 패스워드가 같으면 true 로 반환
				isLogin = true;

				// map 에 현재 loginVO의 id를 넣어주고
				HashMap<String, String> map = new HashMap<>();
				map.put("loginid", loginVO.getLoginid());
				map.put("password", newPassword); // 새로운 패스워드로 업데이트
				// 비밀번호로 업데이트
				loginDAO.updatePass(map);
				log.info("새로운 비밀번호로 변경 성공!!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("새로운 비밀번호로 변경 실패: {}", isLogin);
		}
		return isLogin;
	}

//나머지 정보 수정
	@Override
	public void updateInfo(LoginVO loginVO) {
		try {

			loginDAO.updateInfo(loginVO);
			log.info("{} 나머지 정보 수정 성공!!!", loginVO);
		} catch (Exception e) {
			log.info("{} 나머지 정보 수정 실패!!!", loginVO);
		}
	}

	@Override
	public boolean passwordCheck2(LoginVO loginVO) {
		boolean isLogin = false;
		try {
			// DB에있는 password와 현재loginVO의password 값이 같은지 판단여부
			LoginVO dbVO = loginDAO.selectByUserid(loginVO.getLoginid()); // 아이디 1개 넘어온 값
			if (dbVO != null && dbVO.getPassword().equals(loginVO.getPassword())) {
				// 패스워드가 같으면 true 로 반환
				isLogin = true;

				loginDAO.delete(loginVO);
				log.info("삭제 성공!!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("삭제 실패: {}", isLogin);
		}
		return isLogin;
	}

}
