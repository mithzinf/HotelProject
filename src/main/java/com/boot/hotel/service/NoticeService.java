package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.NoticeBoardDTO;

public interface NoticeService {
	
	//공지사항 일련번호 최대값 구하기
	public int maxNoticeNum() throws Exception;
	
	//글 작성하기
	public void insertNotice(NoticeBoardDTO dto) throws Exception;
	
	//공지사항 전체 글 갯수 구하기
	public int getListsCount() throws Exception;
	
	//조회수 늘려주기
	public void updateHitCount(int num) throws Exception;
	
	//페이징된 리스트 구하기
	public List<NoticeBoardDTO> getNoticeList(Map<String, Object> params) throws Exception;
	
	//공지사항 아티클 정보 가져오기
	public NoticeBoardDTO getReadNotice(int num) throws Exception;
	
	//공지사항 수정 메소드
	public void updateNotice(NoticeBoardDTO dto) throws Exception;
	
	//공지사항 삭제 메소드
	public void deleteNotice(int num) throws Exception;
	

}
