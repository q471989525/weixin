package com.app.application.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.CrmAttachmentDao;
import com.app.application.crm.entity.CrmAttachment;
import com.app.application.crm.service.CrmAttachmentService;

/**
 * 
 * TODO：合同附件表
 * 
 * @author zhoufeng
 */
@Transactional
@Service("crmAttachmentService")
public class CrmAttachmentServiceImpl extends BaseServiceImpl<CrmAttachment> implements CrmAttachmentService {

	CrmAttachmentDao crmAttachmentDao;

	@Resource(name = "crmAttachmentDao")
	public void setCrmAttachmentDao(CrmAttachmentDao crmAttachmentDao) {
		this.crmAttachmentDao = crmAttachmentDao;
		setBaseDao(crmAttachmentDao);
	}

}
