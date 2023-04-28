package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.NoticeBoardDTO;
import com.boot.hotel.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeMapper noticemapper;
	
	@Override
	public int maxNoticeNum() throws Exception {
		return noticemapper.maxNoticeNum();
	}

	@Override
	public void insertNotice(NoticeBoardDTO dto) throws Exception {
		noticemapper.insertNotice(dto);	
	}
	
	@Override
	public int getListsCount() throws Exception {
		
		return noticemapper.getListsCount();
	}

	
	@Override
	public void updateHitCount(int num) throws Exception {
		noticemapper.updateHitCount(num);
	}

	@Override
	public List<NoticeBoardDTO> getNoticeList(Map<String, Object> params) throws Exception {
		
		return noticemapper.getNoticeList(params);
	}

	@Override
	public NoticeBoardDTO getReadNotice(int num) throws Exception {
		
		return noticemapper.getReadNotice(num);
	}

	@Override
	public void updateNotice(NoticeBoardDTO dto) throws Exception {
		noticemapper.updateNotice(dto);
		
	}

	@Override
	public void deleteNotice(int num) throws Exception {
		noticemapper.deleteNotice(num);
		
	}


	
	
	
	
}
