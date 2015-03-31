package com.app.workflow.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.core.service.util.QueryParameter;
import com.app.workflow.dao.WfDefineActivityDao;
import com.app.workflow.dao.WfDefineActivityFormDao;
import com.app.workflow.entity.WfDefineActivity;
import com.app.workflow.entity.WfDefineActivityCandidate;
import com.app.workflow.entity.WfDefineActivityForm;
import com.app.workflow.entity.WfDefineRoute;
import com.app.workflow.service.WfDefineActivityCandidateService;
import com.app.workflow.service.WfDefineActivityService;
import com.app.workflow.service.WfDefineRouteService;

/**
 * 活动定义接口
 */
@Transactional
@Service("wfDefineActivityService")
public class WfDefineActivityServiceImpl extends BaseServiceImpl<WfDefineActivity> implements WfDefineActivityService {

	WfDefineActivityDao wfDefineActivityDao;

	@Autowired
	public void setWfDefineActivityDao(WfDefineActivityDao wfDefineActivityDao) {
		this.wfDefineActivityDao = wfDefineActivityDao;
		setBaseDao(wfDefineActivityDao);
	}

	@Autowired
	WfDefineActivityFormDao wfDefineActivityFormDao;

	@Resource(name = "wfDefineActivityCandidateService")
	WfDefineActivityCandidateService wfDefineActivityCandidateService;

	@Resource(name = "wfDefineRouteService")
	WfDefineRouteService wfDefineRouteService;

	@Override
	public void deleteById(Serializable id) {
		// 删除路由
		QueryParameter param = new QueryParameter();
		param.addEquals("activityId", id.toString());
		List<WfDefineRoute> list = wfDefineRouteService.findList(param);
		if (CollectionUtils.isNotEmpty(list))
			wfDefineRouteService.deleteCollection(list);

		// 删除候选人
		List<WfDefineActivityCandidate> candidates = wfDefineActivityCandidateService.findListByHql("from WfDefineActivityCandidate where activityId=?", id);
		if (CollectionUtils.isNotEmpty(candidates))
			wfDefineActivityCandidateService.deleteCollection(candidates);

		// 删除元素活动关系中间表
		List<WfDefineActivityForm> forms = wfDefineActivityFormDao._findListByHql("from WfDefineActivityForm where activityId=?", id);
		if (CollectionUtils.isNotEmpty(forms))
			wfDefineActivityFormDao._deleteCollection(forms);

		super.deleteById(id);
	}

	@Override
	public void save(WfDefineActivity activity, WfDefineActivityCandidate candidate, String selectdFormElemIds) {
		save(activity); // 新增

		candidate.setActivityId(activity.getResourceid());
		wfDefineActivityCandidateService.save(candidate); // 新增候选人

		if (StringUtils.isNotEmpty(selectdFormElemIds)) {
			String[] elems = selectdFormElemIds.split(",");
			for (int i = 0; i < elems.length; i++) {
				WfDefineActivityForm form = new WfDefineActivityForm(activity.getResourceid(), elems[i]);
				wfDefineActivityFormDao._save(form); // 新增元素活动关系中间表
			}
		}
	}

	@Override
	public void update(WfDefineActivity activity, WfDefineActivityCandidate candidate, String selectdFormElemIds) {
		update(activity); // 修改

		candidate.setActivityId(activity.getResourceid());
		wfDefineActivityCandidateService.saveOrUpdate(candidate); // 修改

		// 先删除元素活动关系中间表
		List<WfDefineActivityForm> forms = wfDefineActivityFormDao._findListByHql("from WfDefineActivityForm where activityId=?", activity.getResourceid());
		if (CollectionUtils.isNotEmpty(forms))
			wfDefineActivityFormDao._deleteCollection(forms);

		if (StringUtils.isNotEmpty(selectdFormElemIds)) {
			String[] elems = selectdFormElemIds.split(",");
			for (int i = 0; i < elems.length; i++) {
				WfDefineActivityForm form = new WfDefineActivityForm(activity.getResourceid(), elems[i]);
				wfDefineActivityFormDao._save(form); // 新增元素活动关系中间表
			}
		}
	}

	/**
	 * 通过流程定义ID，查询开始节点活动
	 */
	@Override
	public WfDefineActivity findStartActivityByProcessId(String processid) {
		QueryParameter param = new QueryParameter();
		param.addEquals("activityType", "S"); // 开始类型节点
		param.addEquals("processId", processid);
		List<WfDefineActivity> list = findList(param);
		if (CollectionUtils.isNotEmpty(list))
			return list.get(0);
		return null;
	}

}
