package com.app.application.oa.portal.service;

import java.util.List;

import com.app.core.service.BaseService;
import com.app.application.oa.portal.entity.MeetOrder;
import com.app.application.oa.portal.entity.MeetOrderPerson;

public interface MeetOrderService extends BaseService<MeetOrder> {

	void save(MeetOrder meetOrder, List<MeetOrderPerson> persons);

	void update(MeetOrder meetOrder, List<MeetOrderPerson> persons);
	
	/**
	 * 查询有效会议时间
	 * @param meetId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int findValidMeetTime(String meetId, String startTime, String endTime, String resourceid);

}
