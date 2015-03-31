package com.app.core.service.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型类工具类
 * 
 * @author ZF
 * @date 2010-7-12
 */
public class GenericsManager {

	/**
	 * 获取对象类型.
	 * 
	 * @param entityClass
	 *            具体要获取的对象类型
	 * @return Class Class
	 */
	@SuppressWarnings("rawtypes")
	public static Class getGeneric(Class entityClass) {
		return getGeneric(entityClass, 1);
	}

	/**
	 * 获取对象类型.
	 * 
	 * @param entityClass
	 *            具体要获取的对象类型
	 * @param index
	 *            参数位置
	 * @return Class Class
	 */
	@SuppressWarnings("rawtypes")
	private static Class getGeneric(Class entityClass, int index) {
		Type genType = (Type) entityClass.getGenericSuperclass();

		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

			if ((params != null) && (params.length >= index)) {
				return (Class) params[index - 1];
			}
		}
		return null;
	}

}
