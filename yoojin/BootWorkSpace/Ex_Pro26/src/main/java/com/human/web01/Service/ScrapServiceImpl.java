package com.human.web01.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.web01.dao.BoardDaO;
import com.human.web01.dao.ScrapDAO;
import com.human.web01.vo.ScrapVO;

import lombok.extern.slf4j.Slf4j;

@Service("scrapService")
@Slf4j
public class ScrapServiceImpl implements ScrapSevice {

	@Autowired
	ScrapDAO scrapDAO;
	@Autowired
	BoardDaO boardDaO;

	@Override
	public boolean scrap(ScrapVO scrapVO) {
		boolean result = false;
		log.info("서비스 like({})호출~~", scrapVO);
		try {
			// 이미 좋아요가 등록되어있는지 판단한다.
			if (scrapDAO.scrapCheck(scrapVO) > 0) {
				// 등록되어있다.
				// 1. 등록된 좋아요를 지운다.
				scrapDAO.notscrap(scrapVO);
				// 2. 보드의 좋아용 개수를 1개 줄인다.
				boardDaO.scrapless(scrapVO.getTb_ref());

				result = false;
			} else {
				// 등록되어있지 않다.
				// 1.좋아요를 등록한다.
				scrapDAO.scrap(scrapVO);
				// 2. 보드의 좋아용 개수를 1개 늘린다.
				boardDaO.scrapCount(scrapVO.getTb_ref());
				result = true;
			}
			log.info("{} 스크랩 기능 성공!!!", scrapVO);
		} catch (Exception e) {
			log.info("{} 스크랩 기능 실패!!!", scrapVO);
			e.printStackTrace();
		}
		return result;
	}

}
