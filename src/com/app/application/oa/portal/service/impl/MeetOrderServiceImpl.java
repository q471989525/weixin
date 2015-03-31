package com.app.application.oa.portal.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.oa.portal.dao.MeetOrderDao;
import com.app.application.oa.portal.dao.MeetOrderPersonDao;
import com.app.application.oa.portal.entity.MeetOrder;
import com.app.application.oa.portal.entity.MeetOrderPerson;
import com.app.application.oa.portal.service.MeetOrderService;

@Service
@Transactional
public class MeetOrderServiceImpl extends BaseServiceImpl<MeetOrder> implements MeetOrderService {

	MeetOrderDao meetOrderDao;

	@Autowired
	MeetOrderPersonDao meetOrderPersonDao;

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Autowired
	public void setMeetOrderDao(MeetOrderDao meetOrderDao) {
		this.meetOrderDao = meetOrderDao;
		super.setBaseDao(meetOrderDao);
	}

	@Override
	public void save(MeetOrder meetOrder, List<MeetOrderPerson> persons) {
		super.save(meetOrder);

		if (CollectionUtils.isNotEmpty(persons)) {
			for (MeetOrderPerson person : persons) {
				person.setMeetOrderId(meetOrder.getResourceid());
				meetOrderPersonDao._save(person);
			}
		}
	}

	@Override
	public void update(MeetOrder meetOrder, List<MeetOrderPerson> persons) {
		super.update(meetOrder);

		List<MeetOrderPerson> orderPersons = meetOrderPersonDao._findListByHql("from MeetOrderPerson where meetOrderId=?", meetOrder.getResourceid());
		if (CollectionUtils.isNotEmpty(orderPersons))
			meetOrderPersonDao._deleteCollection(orderPersons);

		if (CollectionUtils.isNotEmpty(persons)) {
			for (MeetOrderPerson person : persons) {
				person.setMeetOrderId(meetOrder.getResourceid());
			}
			meetOrderPersonDao._saveOrUpdateCollection(persons);
		}
	}
	
	@Override
	public void deleteByIds(Serializable[] ids) {
		for (int i = 0; i < ids.length; i++) {
			List<MeetOrderPerson> orderPersons = meetOrderPersonDao._findListByHql("from MeetOrderPerson where meetOrderId=?", ids[i]);
			if (CollectionUtils.isNotEmpty(orderPersons))
				meetOrderPersonDao._deleteCollection(orderPersons);
		}
		super.deleteByIds(ids);
	}

	@Override
	public int findValidMeetTime(String meetId, String startTime, String endTime, String resourceid) {
		String condition = "";
		if(StringUtils.isNotEmpty(resourceid)) condition = "o.RESOURCEID <> '"+resourceid+"' and";
		String sql = "select count(t.resourceid) from (select o.RESOURCEID, o.START_TIME from T_PORTAL_MEET_ORDER o where "+condition+" o.End_TIME >= ? and o.MEET_ID = ?) t where t.START_TIME <= ?";
		int count = jdbcTemplate.queryForInt(sql, new Object[] { startTime, meetId, endTime });
		return count;
	}

}
