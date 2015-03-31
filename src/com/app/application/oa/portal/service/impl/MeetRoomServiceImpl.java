package com.app.application.oa.portal.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.oa.portal.dao.MeetRoomDao;
import com.app.application.oa.portal.entity.MeetRoom;
import com.app.application.oa.portal.service.MeetRoomService;

@Service
@Transactional
public class MeetRoomServiceImpl extends BaseServiceImpl<MeetRoom> implements MeetRoomService {

	MeetRoomDao meetRoomDao;

	@Autowired
	public void setMeetRoomDao(MeetRoomDao meetRoomDao) {
		this.meetRoomDao = meetRoomDao;
		super.setBaseDao(meetRoomDao);
	}

	@Override
	public void deleteByIds(Serializable[] ids) {
		for (Serializable id : ids) {
			MeetRoom room = super.load(id);
			room.setDeleteFlag("y");
		}
	}

}
