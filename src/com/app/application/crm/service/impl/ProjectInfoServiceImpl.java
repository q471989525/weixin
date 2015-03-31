package com.app.application.crm.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.ProjectInfoDao;
import com.app.application.crm.entity.CompetitorRelation;
import com.app.application.crm.entity.ContactsRelation;
import com.app.application.crm.entity.ProjectInfo;
import com.app.application.crm.service.CompetitorRelationService;
import com.app.application.crm.service.ContactsRelationService;
import com.app.application.crm.service.ProjectInfoService;

/**
 * 
 * TODO：项目信息
 * 
 * @author zhoufeng
 */
@Transactional
@Service("projectInfoService")
public class ProjectInfoServiceImpl extends BaseServiceImpl<ProjectInfo> implements ProjectInfoService {

	ProjectInfoDao projectInfoDao;

	@Resource(name = "projectInfoDao")
	public void setProjectInfoDao(ProjectInfoDao projectInfoDao) {
		this.projectInfoDao = projectInfoDao;
		setBaseDao(projectInfoDao);
	}

	@Resource(name = "contactsRelationService")
	ContactsRelationService contactsRelationService;

	@Resource(name = "competitorRelationService")
	CompetitorRelationService competitorRelationService;

	@Override
	public void deleteByIds(Serializable[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			for (Serializable id : ids) {
				ProjectInfo info = load(id);
				info.setDeleteFlag("y");
			}
		}
	}

	@Override
	public ProjectInfo save(ProjectInfo entity) {

		super.save(entity);

		saveRelation(entity);

		return entity;
	}

	@Override
	public ProjectInfo update(ProjectInfo entity) {
		// 删除联系人
		List<ContactsRelation> contacts = contactsRelationService.findListByHql("from ContactsRelation where relationId=?", entity.getResourceid());
		if (CollectionUtils.isNotEmpty(contacts))
			contactsRelationService.deleteCollection(contacts);
		// 删除竞争对手
		List<CompetitorRelation> competitors = competitorRelationService.findListByHql("from CompetitorRelation where relationId=?", entity.getResourceid());
		if (CollectionUtils.isNotEmpty(competitors))
			competitorRelationService.deleteCollection(competitors);

		saveRelation(entity);

		return super.update(entity);
	}

	// 保存联系人，竞争对手关系
	private void saveRelation(ProjectInfo entity) {
		// 保存联系人
		if (StringUtils.isNotEmpty(entity.getContactsIds())) {
			String[] ids = entity.getContactsIds().split(",");
			for (int i = 0; i < ids.length; i++) {
				ContactsRelation relation = new ContactsRelation(ids[i], entity.getResourceid());
				contactsRelationService.save(relation);
			}
		}

		// 保存竞争对手
		if (StringUtils.isNotEmpty(entity.getCompetitorIds())) {
			String[] ids = entity.getCompetitorIds().split(",");
			for (int i = 0; i < ids.length; i++) {
				CompetitorRelation relation = new CompetitorRelation(ids[i], entity.getResourceid());
				competitorRelationService.save(relation);
			}
		}
	}

}
