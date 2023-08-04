package com.human.web01.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.human.web01.vo.BoardVO;

@Mapper
public interface BoardDaO {
	//개수 세기
	int selectCount() throws SQLException;
	//한개 갖고오기
	BoardVO selectByIdx(int idx) throws SQLException;
	// 페이징한 데이터 목록
	List<BoardVO> selectList(HashMap<String, Integer> map)  throws SQLException;
	// 글 저장
	void write(BoardVO boardVO);
    //조회수 증가
	void incrementReadCount(int idx) throws SQLException;
	
	//글 수정
	void update(BoardVO boardVO);
}
