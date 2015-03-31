package com.app.base.service;

import java.util.List;

import com.app.base.entity.SysDictionary;
import com.app.core.service.BaseService;

/**
 * 
 * TODO：数据字典Service接口
 * 
 * @author zhoufeng
 */
public interface SysDictionaryService extends BaseService<SysDictionary> {

	void save(SysDictionary dictionary, List<SysDictionary> lists);

	void update(SysDictionary dictionary, List<SysDictionary> lists);

	void refresh(String operator);

}
