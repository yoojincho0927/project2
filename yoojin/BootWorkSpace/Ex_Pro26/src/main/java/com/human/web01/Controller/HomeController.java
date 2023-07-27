package com.human.web01.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.human.web01.Service.TestService;

@Controller
public class HomeController {
	
	@Autowired
	private TestService testService;
	
	@GetMapping(value = {"/","/today"})
	public String index(Model model) {
		model.addAttribute("serverTime", 
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일(E) hh:mm:ss")));
		model.addAttribute("dbTime1", testService.selectToday1()); 
		model.addAttribute("dbTime2", testService.selectToday2()); 
		model.addAttribute("dbTime3", testService.selectToday3()); 
		return "index";
	}
}
