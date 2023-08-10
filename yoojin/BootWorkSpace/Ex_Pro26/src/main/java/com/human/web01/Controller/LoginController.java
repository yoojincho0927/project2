package com.human.web01.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.human.web01.Service.BoardService;
import com.human.web01.Service.LoginService;
import com.human.web01.vo.BoardVO;
import com.human.web01.vo.LoginVO;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/member")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private BoardService boardService;

	// 회원가입화면
	@GetMapping(value = "/join")
	public String join() {
		return "member/join";

	}

	// 저장하기 : Get방식일 경우 리스트로 강제이동 //url 에 직접 때려 넣었을 때 얘기
	@GetMapping(value = "/insertOk")
	public String insertOkGet() {
		return "member/list";

	}

	// 저장하기
	@PostMapping(value = "/insertOk")
	public String insertOkPost(@ModelAttribute LoginVO loginVO) {
		log.info("insertOk({}) 호출 ", loginVO);

		// DB에 회원가입 member 정보 저장 + authkey생성 + 이메일 발송 =
		loginService.insert(loginVO);

		return "member/insertOk";
	}

	// 이메일 인증 확인하면 나오는 경로
	@GetMapping(value = "/emailConfirm")
	public String emailConfirm(Model model, @RequestParam String email) {

		// mem_auth 권한 상태 1 로 변경
		loginService.updateAuth(email);

		// html 에서 쓰기 위해 model 에 담음
		model.addAttribute("email", email);
		return "member/emailConfirm";
	}

	// 회원목록화면
	@GetMapping(value = "/list")
	public String list(Model model) {

		List<LoginVO> list = loginService.selectList();
		model.addAttribute("list", list);
		return "member/list";
	}

	// 아이디 중복확인
	@GetMapping("/idCheck")
	@ResponseBody // 직접 출력해라!!!!
	public int idCheck(@RequestParam(required = true, defaultValue = "") String loginid) {
		return loginService.selectByUseridCount(loginid);
	}

	// 이메일 중복확인
	@GetMapping("/emailCheck")
	@ResponseBody // 직접 출력해라!!!!
	public int emailCheck(@RequestParam(required = true, defaultValue = "") String email) {
		return loginService.selectByEmail(email);
	}

	// 로그인 화면
	@GetMapping(value = "/login")
	public String login() {

		return "member/login";

	}

	// 로그인 계정 세션 값에 넣기
	@PostMapping(value = "/loginOk")
	@ResponseBody
	public boolean loginOkPost(@ModelAttribute LoginVO loginVO, HttpSession session, Model model) {
		log.info("넘어온값 loginOk() : {}", loginVO);
		boolean isLogin = false;
		// 아이디와 비밀번호가 같으면
		if (loginService.loginCheck(loginVO.getLoginid(), loginVO.getPassword())) {
			session.setAttribute("loginVO", loginService.selectByUserid(loginVO.getLoginid()));
			session.setMaxInactiveInterval(60*60);
			isLogin = true;

		}
		return isLogin;

	}

	// 로그아웃 화면
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		// 세션 값 지우기
		session.removeAttribute("loginVO");
		return "member/login";
	}

	@GetMapping(value = "/idFind")
	public String idFind() {

		return "member/idFind";
	}

	// 아이디 찾기
	// 일치하는 이메일 있으면 Controller 없으면
	@PostMapping(value = "/idFindOk")
	@ResponseBody
	public boolean idFind2(@ModelAttribute LoginVO loginVO) {
		log.info("넘어온값 loginOk() : {}", loginVO);
		boolean isEmail = false;
		// 이메일이 같으면
		if (loginService.emailCheck(loginVO.getEmail())) {
			isEmail = true;
		}
		return isEmail;
	}

	@GetMapping(value = "/passwordFind")
	public String passwordFind() {

		return "member/passwordFind";
	}

	// 비밀번호 찾기
	// 일치하는 이메일 있으면 Controller 없으면
	@PostMapping(value = "/idFindOk2")
	@ResponseBody
	public boolean passwordFind2(@ModelAttribute LoginVO loginVO) {
		log.info("넘어온값 loginOk() : {}", loginVO);
		boolean isEmail = false;
		// 이메일이 같으면
		if (loginService.emailCheck2(loginVO.getEmail())) {
			isEmail = true;
		}
		return isEmail;
	}

	// 마이페이지 폼
	@GetMapping(value = "/Mypage")
	public String MyPage() {

		return "member/Mypage";
	}

	// 비밀번호 변경 폼
	@GetMapping(value = "/passwordUpdate")
	public String passwordUpdate() {

		return "member/passwordUpdate";
	}

	// 비밀번호를 변경해라
	@PostMapping(value = "/updatePass")
	public String password(@ModelAttribute LoginVO loginVO, @RequestParam String newPassword, Model model) {
		log.info("넘어온값 loginOk() : {}, {}", loginVO, newPassword);
		// 현재 패스워드 같으면
		if (loginService.passwordCheck(loginVO, newPassword)) {
			return "redirect:/"; // 바로 메인으로 가라
		}

		return "redirect:/member/passwordUpdate"; // 그게 아니면 다시 폼으로 가라

	}

	// 나머지 정보를 변경해라
	@PostMapping(value = "/updateOk")
	public String update(@ModelAttribute LoginVO loginVO, Model model) {
		log.info("넘어온값 loginOk() : {}", loginVO);
		loginService.updateInfo(loginVO);

		return "redirect:/";

	}

	// 탈퇴 폼
	@GetMapping(value = "/deleteForm")
	public String delete() {

		return "member/deleteForm";
	}

	// 탈퇴해라
	@PostMapping(value = "/deleteOk")
	public String delete2(@ModelAttribute LoginVO loginVO, Model model) {
		log.info("넘어온값 loginOk() : {}", loginVO);
		if (loginService.passwordCheck2(loginVO)) {
			return "redirect:/";

		}
		return "member/deleteForm";
	}

	// 좋아요 한 글 보러가기
	@GetMapping(value = "/like")
	public String like(@ModelAttribute LoginVO loginVO, Model model) {
		log.info("넘어온값 loginOk() : {}", loginVO);
		List<BoardVO>  list = boardService.selectLike(loginVO.getLoginid());
		model.addAttribute("list", list);

		return "member/like";
	}

}