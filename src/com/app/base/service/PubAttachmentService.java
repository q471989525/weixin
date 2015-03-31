package com.app.base.service;

import java.util.List;

import com.app.base.entity.PubAttachment;
import com.app.core.service.BaseService;

/**
 * 
 * TODO：公共附件
 * @author zhoufeng
 */
public interface PubAttachmentService extends BaseService<PubAttachment>{
	
	List<PubAttachment> getAttachmentsByUuid(String uuid);

}
