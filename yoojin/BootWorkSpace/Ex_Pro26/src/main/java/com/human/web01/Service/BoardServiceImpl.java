package com.human.web01.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.web01.dao.BoardDaO;
import com.human.web01.vo.BoardVO;
import com.human.web01.vo.PagingVO;

import lombok.extern.slf4j.Slf4j;

@Service("boardService")
@Slf4j
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDaO boardDaO;

	// 글 저장
	@Override
	public void write(BoardVO boardVO) {
		log.info("서비스 write({})호출~~", boardVO);
		if (boardVO != null) {
			try {
				boardDaO.write(boardVO);
				log.info("{} 저장 성공!!!", boardVO);
			} catch (Exception e) {
				log.info("{} 저장 실패!!!", boardVO);
				e.printStackTrace();
			}
		}

	}

	// 목록보기
	@Override
	public PagingVO<BoardVO> selectList(int currentPage, int sizeOfPage, int sizeOfBlock) {
		log.info("서비스 selectlist({},{},{})호출~~", currentPage, sizeOfPage, sizeOfPage);
		PagingVO<BoardVO> pv = null;

		try {
			// 전체 개수 구한후
			int totalCount = boardDaO.selectCount();
			// 페이지 계산한 수
			pv = new PagingVO<>(totalCount, currentPage, sizeOfPage, sizeOfBlock);
			// 개수가 있다면 글목록을 가져와 넣어준다.

			if (pv.getTotalCount() > 0) {
				HashMap<String, Integer> map = new HashMap<>();
				map.put("startNo", pv.getStartNo());
				map.put("endNo", pv.getEndNo());
				pv.setList(boardDaO.selectList(map));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pv;

	}

	// 내용보기
	@Override
	public BoardVO selectByIdx(int idx, boolean isReadCount) {
		log.info("서비스 selectByIdx ({},{})호출~~", idx, isReadCount);
		BoardVO boardVO = null;

		try {
			boardVO = boardDaO.selectByIdx(idx);
			log.info("{} 한개 얻기 성공!!!", boardVO);

			if (boardVO != null && isReadCount) {
				// 해당 글번호의 글이 존재하면서 조회수가 참이면 조회수를 증가 시킨다.
				boardDaO.incrementReadCount(idx);
			}

		} catch (SQLException e) {

			e.printStackTrace();
			log.info("{} 한개 얻기 실패!!!", boardVO);
		}
		return boardVO;
	}

	// 글 수정하기
	@Override
	public void update(BoardVO boardVO) {
		log.info("서비스 update ({})호출~~", boardVO);

		try {
			boardDaO.update(boardVO);
			log.info("{} 글 수정 성공!!!", boardVO);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("{} 글 수정 실패!!!", boardVO);
		}

	}

	// 글 삭제하기
	@Override
	public void delete(int idx) {
		log.info("서비스 delete ({})호출~~", idx);

		try {
			boardDaO.delete2(idx);
			log.info("{} 글 삭제 성공!!!", idx);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("{} 글 삭제 실패!!!", idx);
		}

	}

	// 좋아요 가져오기
	@Override
	public List<BoardVO> selectLike(String loginid) {
		log.info("서비스 selectLike ({})호출~~", loginid);
		List<BoardVO>  list = null;

		try {

				list = boardDaO.selectLike(loginid);
				log.info("{} 좋아요 글 가져오기 성공!!!", list);
			

		} catch (Exception e) {

			e.printStackTrace();
			log.info("좋아요 글 얻기 실패!!!");
		}
		return list;

	}

}
