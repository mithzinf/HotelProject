package com.boot.hotel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.boot.hotel.dto.NoticeBoardDTO;

@Mapper
public interface NoticeMapper {
//공지사항 mapper interface
	
	public int maxNoticeNum() throws Exception;
	
	public void insertNotice(NoticeBoardDTO dto) throws Exception; //글작성
	public int getListsCount() throws Exception; //글 갯수 세는 쿼리, 조건이 없어서 매개변수도 없어
	public void updateHitCount(int num) throws Exception; //조회수 업
	public List<NoticeBoardDTO> getNoticeList(Map<String, Object> params) throws Exception; //params에 start와 end가 들어가겠지
	public NoticeBoardDTO getReadNotice(int num) throws Exception; //공지사항 글 읽어
	public void updateNotice(NoticeBoardDTO dto) throws Exception; //공지사항 수정해
	public void deleteNotice(int num) throws Exception; //공지사항 삭제해
	
	
	
	
}
