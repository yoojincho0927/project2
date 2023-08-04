package com.human.web01.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.human.web01.Service.BoardService;
import com.human.web01.Service.TestService;
import com.human.web01.vo.BoardVO;
import com.human.web01.vo.CommVO;
import com.human.web01.vo.PagingVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@Autowired
	private TestService testService;
	@Autowired
	private BoardService boardService;

	@GetMapping(value = { "/today" })
	public String index(Model model) {
		model.addAttribute("serverTime",
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일(E) hh:mm:ss")));
		model.addAttribute("dbTime1", testService.selectToday1());
		model.addAttribute("dbTime2", testService.selectToday2());
		model.addAttribute("dbTime3", testService.selectToday3());
		return "index";
	}

	@GetMapping(value = "/")
	public String home(@ModelAttribute CommVO commVO, Model model) {
		log.info("넘어온값 : {}", commVO);
		model.addAttribute("cv", commVO);
		PagingVO<BoardVO> pagingVO = boardService.selectList(commVO.getCurrentPage(), commVO.getSizeOfPage(),
				commVO.getSizeOfBlock());
		model.addAttribute("pv", pagingVO);
		log.info("가져온값 : {}", pagingVO);
		return "main/main";

	}

	// 내용보기
	@GetMapping(value = "/view")
	public String view(@ModelAttribute CommVO commVO, Model model) {
		BoardVO myBoardVO = boardService.selectByIdx(commVO.getIdx(), true); // 받고 조회수 증가
		if (myBoardVO != null) {
			myBoardVO.setReadCount(myBoardVO.getReadCount() + 1); // 조회수 증가
			model.addAttribute("cv", commVO);
			model.addAttribute("vo", myBoardVO);
			model.addAttribute("newLine", "\n");
			model.addAttribute("br", "<br/>");
			return "main/view";
		} else {
			return "redirect:/";
		}
	}
	
	
	
	
}
