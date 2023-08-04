package com.human.web01.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.human.web01.Service.BoardService;
import com.human.web01.vo.BoardVO;
import com.human.web01.vo.CommVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/main") // 아...그냥 메인으로 가려면 이걸 없애야..
public class BoardController {

	@Autowired
	private BoardService boardService;

	// 저장 폼
	@GetMapping(value = "/insert")
	public String insert() {

		return "main/insert";
	}

	// 글 저장하기
	@PostMapping(value = "/insertOk")
	public String insert2(HttpSession session, @ModelAttribute BoardVO boardVO, @ModelAttribute CommVO commVO) {
		// BoardVO 가져와서 BoardVO에 저장
		log.info("넘어온값1 : {}", commVO);
		log.info("넘어온값1 : {}", boardVO);
		boardService.write(boardVO);
		return "redirect:/";
	}

	// 글 수정 폼
	@GetMapping(value = "/update")
	public String update(@ModelAttribute CommVO commVO, Model model) {
		log.info("넘어온값 : {}", commVO);
		model.addAttribute("cv", commVO);
		BoardVO myBoardVO = boardService.selectByIdx(commVO.getIdx(), false); // 받고 조회수 증가 안함
		if (myBoardVO != null) {
			model.addAttribute("cv", commVO);
			model.addAttribute("vo", myBoardVO);
			return "update";
		} else {
			return "redirect:/";
		}

	}

}
