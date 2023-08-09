package com.human.web01.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.human.web01.Service.BoardService;
import com.human.web01.Service.HeartService;
import com.human.web01.Service.LoginService;
import com.human.web01.Service.ScrapSevice;
import com.human.web01.vo.BoardVO;
import com.human.web01.vo.CommVO;
import com.human.web01.vo.HeartVO;
import com.human.web01.vo.LoginVO;
import com.human.web01.vo.ScrapVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/main") // 아...그냥 메인으로 가려면 이걸 없애야..
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private HeartService heartService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private ScrapSevice scrapSevice;

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

	// 글 수정 폼띄우기
	@GetMapping(value = "/update")
	public String update(@ModelAttribute CommVO commVO, Model model) {
		log.info("넘어온값 : {}", commVO);
		model.addAttribute("cv", commVO);
		BoardVO myBoardVO = boardService.selectByIdx(commVO.getIdx(), false); // 받고 조회수 증가 안함
		if (myBoardVO != null) {
			model.addAttribute("cv", commVO);
			model.addAttribute("vo", myBoardVO);
			return "main/update";
		} else {
			return "redirect:/";
		}

	}

	// 수정하기 : Get방식일 경우 리스트로 강제이동
	@GetMapping(value = "/updateOk")
	public String updateOkGet(@ModelAttribute CommVO commVO, Model model) {
		return "redirect:/";
	}

	// 글 수정하기...
	@PostMapping(value = "/updateOk")
	public String updateOk(@ModelAttribute CommVO commVO, @ModelAttribute BoardVO boardVO) {

		log.info("넘어온값 : {}", commVO);
		log.info("넘어온값 2 : {}", boardVO);
		boardService.update(boardVO);
		return "redirect:/view?idx=" + commVO.getIdx() + "&p=" + commVO.getCurrentPage() + "&s="
				+ commVO.getSizeOfPage() + "&b=" + commVO.getSizeOfBlock();

	}

	// 글 삭제 완료
	@GetMapping(value = "/deleteOk2")
	@ResponseBody
	public boolean deleteOk2(@RequestParam(required = true) int idx, @ModelAttribute CommVO commVO) {
		boolean deleteOk = false;
		try {
			boardService.delete(idx);
			deleteOk = true;
		} catch (Exception e) {
			;
		}
		return deleteOk;
	}

	// 좋아요 기능
	@PostMapping(value = "/likeOk")
	@ResponseBody
	public boolean likeOk(@ModelAttribute HeartVO heartVO) {
		boolean result = heartService.like(heartVO);

		return result;

	}

	// 스크랩 기능
	@PostMapping(value = "/scrapOk")
	@ResponseBody
	public boolean likeOk(@ModelAttribute ScrapVO scrapVO) {
		boolean result = scrapSevice.scrap(scrapVO);

		return result;

	}

}
