package com.app.core.service.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

public class QueryParameter {

	/** 操作符 */
	public static final String AND = "and ";
	public static final String OR = "or ";
	/** 运算符 */
	public static final String EQUALS = " = ";
	public static final String NOT_EQUALS = " != ";
	public static final String LIKE = " like ";
	public static final String NOT_LIKE = " not like ";
	public static final String IN = " in ";
	public static final String NOT_IN = " not in ";
	public static final String GT = " > ";
	public static final String GT_EQ = " >= ";
	public static final String LT = " < ";
	public static final String LT_EQ = " <= ";
	public static final String IS = " is ";
	public static final String IS_NOT = " is not ";

	/** AND 或 OR */
	private String operator = "and ";

	/** 查询字段 */
	private String fieldName;

	/** 条件 = */
	private String condition;

	/** 前缀 */
	private String prefix;

	/** 值 */
	private String value;

	/** 后缀 */
	private String suffix;

	/** 复杂HQL可以直接拼写 */
	private String hql;

	/** 排序 */
	private String orderBy;

	/** 条件集合 */
	List<QueryParameter> param = new ArrayList<QueryParameter>();

	public QueryParameter() {
	}

	public QueryParameter(String fieldName, String condition, String value) {
		super();
		this.fieldName = fieldName;
		this.condition = condition;
		this.prefix = "";
		this.value = value;
		this.suffix = "";
	}

	public QueryParameter(String fieldName, String condition, String prefix, String value, String suffix) {
		super();
		this.fieldName = fieldName;
		this.condition = condition;
		this.prefix = prefix;
		this.value = value;
		this.suffix = suffix;
	}

	public QueryParameter(String operator, String fieldName, String condition, String prefix, String value, String suffix) {
		super();
		this.operator = operator;
		this.fieldName = fieldName;
		this.condition = condition;
		this.prefix = prefix;
		this.value = value;
		this.suffix = suffix;
	}

	public QueryParameter(String hql) {
		super();
		this.fieldName = "";
		this.condition = "";
		this.prefix = "";
		this.value = "";
		this.suffix = "";
		this.hql = hql;
	}

	public QueryParameter(String operator, String hql) {
		super();
		this.operator = operator;
		this.fieldName = "";
		this.condition = "";
		this.prefix = "";
		this.value = "";
		this.suffix = "";
		this.hql = hql;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<QueryParameter> getParam() {
		return param;
	}

	public void setParam(List<QueryParameter> param) {
		this.param = param;
	}

	/**
	 * 添加HQL
	 * 
	 * @param hql
	 */
	public void addHql(String hql) {
		getParam().add(new QueryParameter(hql));
	}

	/**
	 * 添加HQL
	 * 
	 * @param operator
	 * @param hql
	 */
	public void addHql(String operator, String hql) {
		getParam().add(new QueryParameter(operator, hql));
	}

	/**
	 * 等于
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void addEquals(String fieldName, String value) {
		if (NumberUtils.isNumber(value)) { // 数字
			getParam().add(new QueryParameter(fieldName, EQUALS, value));
		} else {
			getParam().add(new QueryParameter(fieldName, EQUALS, "'", value, "'"));
		}
	}

	/**
	 * 不等于
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void addNotEquals(String fieldName, String value) {
		if (NumberUtils.isNumber(value)) { // 数字
			getParam().add(new QueryParameter(fieldName, NOT_EQUALS, value));
		} else {
			getParam().add(new QueryParameter(fieldName, NOT_EQUALS, "'", value, "'"));
		}
	}

	/**
	 * 大于
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void addGt(String fieldName, String value) {
		if (NumberUtils.isNumber(value)) { // 数字
			getParam().add(new QueryParameter(fieldName, GT, value));
		} else {
			getParam().add(new QueryParameter(fieldName, GT, "'", value, "'"));
		}
	}

	/**
	 * 大于等于
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void addGtEq(String fieldName, String value) {
		if (NumberUtils.isNumber(value)) { // 数字
			getParam().add(new QueryParameter(fieldName, GT_EQ, value));
		} else {
			getParam().add(new QueryParameter(fieldName, GT_EQ, "'", value, "'"));
		}
	}

	/**
	 * 小于
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void addLt(String fieldName, String value) {
		if (NumberUtils.isNumber(value)) { // 数字
			getParam().add(new QueryParameter(fieldName, LT, value));
		} else {
			getParam().add(new QueryParameter(fieldName, LT, "'", value, "'"));
		}
	}

	/**
	 * 小于等于
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void addLtEq(String fieldName, String value) {
		if (NumberUtils.isNumber(value)) { // 数字
			getParam().add(new QueryParameter(fieldName, LT_EQ, value));
		} else {
			getParam().add(new QueryParameter(fieldName, LT_EQ, "'", value, "'"));
		}
	}

	/**
	 * 模糊like
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void addLike(String fieldName, String value) {
		getParam().add(new QueryParameter(fieldName, LIKE, "'%", value, "%'"));
	}

	/**
	 * 模糊not like
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void addNotLike(String fieldName, String value) {
		getParam().add(new QueryParameter(fieldName, NOT_LIKE, "'%", value, "%'"));
	}

	/**
	 * 范围in
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void addIn(String fieldName, String value) {
		getParam().add(new QueryParameter(fieldName, IN, "(", value, ")"));
	}

	/**
	 * 范围not in
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void addNotIn(String fieldName, String value) {
		getParam().add(new QueryParameter(fieldName, NOT_IN, "(", value, ")"));
	}

	/** 自定义条件 */
	public void add(String fieldName, String condition, String value) {
		getParam().add(new QueryParameter(fieldName, condition, value));
	}

	/** 自定义条件 */
	public void add(String fieldName, String condition, String prefix, String value, String suffix) {
		getParam().add(new QueryParameter(fieldName, condition, prefix, value, suffix));
	}

	/** 自定义条件 */
	public void add(String operator, String fieldName, String condition, String prefix, String value, String suffix) {
		getParam().add(new QueryParameter(operator, fieldName, condition, prefix, value, suffix));
	}

}
