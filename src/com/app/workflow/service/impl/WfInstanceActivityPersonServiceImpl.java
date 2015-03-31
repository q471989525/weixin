package com.app.workflow.service.impl;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.workflow.entity.WfInstanceActivityPerson;
import com.app.workflow.service.WfInstanceActivityPersonService;

/**
 * 活动实例处理人接口
 */
@Transactional
@Service("wfInstanceActivityPersonService")
public class WfInstanceActivityPersonServiceImpl extends BaseServiceImpl<WfInstanceActivityPerson> implements WfInstanceActivityPersonService {

}
