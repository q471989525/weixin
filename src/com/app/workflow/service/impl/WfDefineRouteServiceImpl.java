package com.app.workflow.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.workflow.dao.WfDefineRouteDao;
import com.app.workflow.entity.WfDefineRoute;
import com.app.workflow.service.WfDefineRouteService;

/**
 * 路由定义表接口
 */
@Transactional
@Service("wfDefineRouteService")
public class WfDefineRouteServiceImpl extends BaseServiceImpl<WfDefineRoute> implements WfDefineRouteService {

	WfDefineRouteDao wfDefineRouteDao;

	@Autowired
	public void setWfDefineRouteDao(WfDefineRouteDao wfDefineRouteDao) {
		this.wfDefineRouteDao = wfDefineRouteDao;
		setBaseDao(wfDefineRouteDao);
	}
	
	// 注入Spring jdbcTemplate
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	/**
	 * 通过活动ID，查询路由
	 */
	@Override
	public List<WfDefineRoute> findRoutesByActivityId(String activityId) {
		String sql = "SELECT r.RESOURCEID,r.ACTIVITY_ID,r.ROUTE_ALIAS,r.NEXT_ACTIVITY_ID,r.FINISH_TYPE,r.CONDITION_EXPRESSION,r.CANDIDATE_ID,r.CANDIDATE_NAME,r.PERSON_ID,r.PERSON_NAME,a.ACTIVITY_NAME,a.ACTIVITY_TYPE FROM wf_define_route r, wf_define_activity a where r.NEXT_ACTIVITY_ID=a.RESOURCEID and r.ACTIVITY_ID=? order by r.ORDER_BY";
		List<WfDefineRoute> routes = jdbcTemplate.query(sql, new Object[] { activityId }, new RouteRowMapper());
		return routes;
	}
	
	private class RouteRowMapper implements RowMapper<WfDefineRoute> {
		public WfDefineRoute mapRow(ResultSet rs, int index) throws SQLException {
			WfDefineRoute route = new WfDefineRoute();
			route.setResourceid(rs.getString("RESOURCEID"));
			route.setActivityId(rs.getString("ACTIVITY_ID"));
			route.setRouteAlias(rs.getString("ROUTE_ALIAS"));
			route.setNextActivityId(rs.getString("NEXT_ACTIVITY_ID"));
			route.setFinishType(rs.getString("FINISH_TYPE"));
			route.setConditionExpression(rs.getString("CONDITION_EXPRESSION"));
			route.setCandidateId(rs.getString("CANDIDATE_ID"));
			route.setCandidateName(rs.getString("CANDIDATE_NAME"));
			route.setPersonId(rs.getString("PERSON_ID"));
			route.setPersonName(rs.getString("PERSON_NAME"));
			route.setNextActivityName(rs.getString("ACTIVITY_NAME"));
			route.setActivityType(rs.getString("ACTIVITY_TYPE"));
			return route;
		}
	}

}
