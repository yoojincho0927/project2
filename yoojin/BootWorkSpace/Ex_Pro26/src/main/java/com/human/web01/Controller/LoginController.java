package com.human.web01.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.human.web01.Service.LoginService;
import com.human.web01.vo.LoginVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/member") // get mapping앞에 모든 경로 앞에 이 것을 넣어준다는 뜻
public class LoginController {

	@Autowired
	private LoginService loginService;

	// 로그인 화면 아이디저장
	@GetMapping(value = "/login")
	public String login() {


		return "member/login";

	}

	@GetMapping(value = "/join")
	public String join() {
		return "member/join";

	}
	
	// 저장하기 : Get방식일 경우 리스트로 강제이동 //url 에 직접 때려 넣었을 때 얘기
	@GetMapping(value="/insertOk")
	public String insertOkGet(@ModelAttribute LoginVO loginVO,Model model){
		return "member/list";

	}
	
	@GetMapping(value="/insertOk")
	public String insertOkPost(@ModelAttribute LoginVO loginVO) {
		
		loginService.insert(loginVO);
		return "member/list";
	}

}
