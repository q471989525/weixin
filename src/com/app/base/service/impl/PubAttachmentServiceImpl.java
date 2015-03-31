package com.app.base.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.base.dao.PubAttachmentDao;
import com.app.base.entity.PubAttachment;
import com.app.base.service.PubAttachmentService;
import com.app.core.service.impl.BaseServiceImpl;

/**
 * 
 * TODO：公共附件
 * 
 * @author zhoufeng
 */
@Transactional
@Service("pubAttachmentService")
public class PubAttachmentServiceImpl extends BaseServiceImpl<PubAttachment> implements PubAttachmentService {

	PubAttachmentDao pubAttachmentDao;

	@Autowired
	public void setPubAttachmentDao(PubAttachmentDao pubAttachmentDao) {
		this.pubAttachmentDao = pubAttachmentDao;
		setBaseDao(pubAttachmentDao);
	}
	
	@Override
	public void deleteById(Serializable id) {
		PubAttachment attachment = super.load(id);
		attachment.setDeleteState("y");
	}

	@Override
	public List<PubAttachment> getAttachmentsByUuid(String uuid) {
		return pubAttachmentDao._findList(PubAttachment.class, "deleteState='n' and fkUuid='" + uuid + "'", "createTime");
	}

}
