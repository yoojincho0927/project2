package com.human.web01.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.web01.dao.BoardDaO;
import com.human.web01.dao.HeartDAO;
import com.human.web01.vo.HeartVO;

import lombok.extern.slf4j.Slf4j;

@Service("heartService")
@Slf4j
public class HeartServiceImpl implements HeartService {

	@Autowired
	HeartDAO heartDAO;
	@Autowired
	BoardDaO boardDaO;

	@Override
	public boolean like(HeartVO heartVO) {
		boolean result = false;
		log.info("서비스 like({})호출~~", heartVO);
		try {
			// 이미 좋아요가 등록되어있는지 판단한다.
			if (heartDAO.likeCheck(heartVO) > 0) {
				// 등록되어있다.
				// 1. 등록된 좋아요를 지운다.
				heartDAO.deleteLike(heartVO);
				// 2. 보드의 좋아용 개수를 1개 줄인다.
				boardDaO.dislikeCount(heartVO.getTb_ref());

				result = false;
			} else {
				// 등록되어있지 않다.
				// 1.좋아요를 등록한다.
				heartDAO.like(heartVO);
				// 2. 보드의 좋아용 개수를 1개 늘린다.
				boardDaO.likeCount(heartVO.getTb_ref());
				result = true;
			}
			log.info("{} 좋아요 기능 성공!!!", heartVO);
		} catch (Exception e) {
			log.info("{} 좋아요 기능 실패!!!", heartVO);
			e.printStackTrace();
		}
		return result;
	}

}
