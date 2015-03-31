package com.app.application.crm.service.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.CustomerContactsDao;
import com.app.application.crm.entity.CustomerContacts;
import com.app.application.crm.service.CustomerContactsService;

/**
 * 
 * TODO：客户联系人
 * 
 * @author zhoufeng
 */
@Transactional
@Service("customerContactsService")
public class CustomerContactsServiceImpl extends BaseServiceImpl<CustomerContacts> implements CustomerContactsService {

	CustomerContactsDao customerContactsDao;

	@Resource(name = "customerContactsDao")
	public void setCustomerContactsDao(CustomerContactsDao customerContactsDao) {
		this.customerContactsDao = customerContactsDao;
		setBaseDao(customerContactsDao);
	}

	// 注入Spring jdbcTemplate
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Override
	public void deleteByIds(Serializable[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			for (Serializable id : ids) {
				CustomerContacts contacts = load(id);
				contacts.setDeleteFlag("y");
			}
		}
	}

	@Override
	public List<CustomerContacts> findListByRelation(String relationid) {
		String sql = "select cc.resourceid, cc.user_name, cc.user_sex, cc.user_dept, cc.user_post, cc.telphone, cc.mobile, cc.fax, cc.email, cc.qq, cc.remark from T_CRM_CUSTOMER_CONTACTS cc, T_CRM_CONTACTS_RELATION cr where cc.resourceid = cr.contacts_id and cr.relation_id =?";
		List<CustomerContacts> contacts = jdbcTemplate.query(sql, new Object[] { relationid }, new CustomerContactsRowMapper());
		return contacts;
	}

	private class CustomerContactsRowMapper implements RowMapper<CustomerContacts> {
		public CustomerContacts mapRow(ResultSet rs, int index) throws SQLException {
			CustomerContacts cts = new CustomerContacts();
			cts.setResourceid(rs.getString("resourceid"));
			cts.setUserName(rs.getString("user_name"));
			cts.setUserSex(rs.getString("user_sex"));
			cts.setUserDept(rs.getString("user_dept"));
			cts.setUserPost(rs.getString("user_post"));
			cts.setTelphone(rs.getString("telphone"));
			cts.setMobile(rs.getString("mobile"));
			cts.setFax(rs.getString("fax"));
			cts.setEmail(rs.getString("email"));
			cts.setQq(rs.getString("qq"));
			cts.setRemark(rs.getString("remark"));
			return cts;
		}
	}

}
