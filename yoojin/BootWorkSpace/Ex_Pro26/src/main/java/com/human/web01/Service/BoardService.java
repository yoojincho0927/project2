package com.human.web01.Service;

import com.human.web01.vo.BoardVO;

public interface BoardService {

	// 목록보기
	com.human.web01.vo.PagingVO<BoardVO> selectList(int currentPage, int sizeOfPage, int sizeOfBlock);

	// 글 저장하기
	void write(BoardVO boardVO);

	// 내용보기
	BoardVO selectByIdx(int idx, boolean isReadCount); // isReadCount는 조회수 증가여부 //idx를 받아온다.

	// 글 수정
	void update(BoardVO boardVO);

	
	void delete(int idx);
}
