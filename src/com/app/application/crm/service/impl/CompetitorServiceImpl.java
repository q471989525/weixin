package com.app.application.crm.service.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.CompetitorDao;
import com.app.application.crm.entity.Competitor;
import com.app.application.crm.service.CompetitorService;

/**
 * 
 * TODO：竞争对手
 * 
 * @author zhoufeng
 */
@Transactional
@Service("competitorservice")
public class CompetitorServiceImpl extends BaseServiceImpl<Competitor> implements CompetitorService {

	CompetitorDao competitorDao;

	@Resource(name = "competitordao")
	public void setCompetitorDao(CompetitorDao competitorDao) {
		this.competitorDao = competitorDao;
		super.setBaseDao(competitorDao);
	}

	// 注入Spring jdbcTemplate
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	/**
	 * 覆盖删除方法,只更新删除标识位
	 */
	@Override
	public void deleteByIds(Serializable[] ids) {
		for (Serializable id : ids) {
			Competitor competitor = super.load(id);
			competitor.setDeleteFlag("y");
		}
	}

	@Override
	public List<Competitor> findListByRelation(String relationid) {
		String sql = "select cc.resourceid, cc.competitor_name, cc.company_scale, cc.company_property, cc.company_desc, cc.superiority, cc.disadvantages, cc.create_time from T_CRM_COMPETITOR cc, T_CRM_COMPETITOR_RELATION cr where cc.resourceid = cr.competitor_id and cr.relation_id=?";
		List<Competitor> competitors = jdbcTemplate.query(sql, new Object[] { relationid }, new CompetitorRowMapper());
		return competitors;
	}

	private class CompetitorRowMapper implements RowMapper<Competitor> {
		public Competitor mapRow(ResultSet rs, int index) throws SQLException {
			Competitor competitor = new Competitor();
			competitor.setResourceid(rs.getString("resourceid"));
			competitor.setCompetitorName(rs.getString("competitor_name"));
			competitor.setCompanyScale(rs.getString("company_scale"));
			competitor.setCompanyProperty(rs.getString("company_property"));
			competitor.setCompanyDesc(rs.getString("company_desc"));
			competitor.setSuperiority(rs.getString("superiority"));
			competitor.setDisadvantages(rs.getString("disadvantages"));
			competitor.setCreateTime(rs.getDate("create_time"));
			return competitor;
		}
	}

}
