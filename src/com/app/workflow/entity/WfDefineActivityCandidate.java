package com.app.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 活动定义候选人表
 * 
 */
@Entity
@Table(name = "wf_define_activity_candidate")
public class WfDefineActivityCandidate extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String activityId; // 活动定义ID
	private String candidateId; // 主办人ID
	private String candidateName; // 主办人
	private String personId; // 经办人ID
	private String personName; // 经办人
	private String objCandidateId; // 对象主办人ID
	private String objCandidateIdValue; // 对象主办人ID值
	private String objCandidateName; // 对象主办人
	private String objCandidateNameValue; // 对象主办人值
	private String objPersonId; // 对象经办人ID
	private String objPersonIdValue; // 对象经办人ID值
	private String objPersonName; // 对象经办人
	private String objPersonNameValue; // 对象经办人值
	private String relateRoleId; // 关联角色ID
	private String relateRoleName; // 关联角色名称
	private String relateCondition; // 关联条件,S:发起人/C:当前人，P:人员/D:部门变量
	private String relateVariable; // 关联变量
	private String relateVariableValue; // 关联变量值
	private String relateExplain; // 关联说明
	private String isSelect; // 人员选择弹框,是否允许弹框Y:是,N:否
	private String limitPersonId; // 人员范围ID
	private String limitPersonName; // 人员范围名称
	private String limitRoleId; // 角色范围ID
	private String limitRoleName; // 角色范围名称
	private String limitOrgId; // 组织范围ID
	private String limitOrgName; // 组织范围名称
	private String candidateRemark; // 备注

	public WfDefineActivityCandidate() {
	}

	@Column(name = "ACTIVITY_ID", length = 32)
	public String getActivityId() {
		return this.activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Column(name = "CANDIDATE_ID", length = 350)
	public String getCandidateId() {
		return this.candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	@Column(name = "CANDIDATE_NAME", length = 300)
	public String getCandidateName() {
		return this.candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	@Column(name = "PERSON_ID", length = 500)
	public String getPersonId() {
		return this.personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(name = "PERSON_NAME", length = 350)
	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name = "OBJ_CANDIDATE_ID", length = 200)
	public String getObjCandidateId() {
		return this.objCandidateId;
	}

	public void setObjCandidateId(String objCandidateId) {
		this.objCandidateId = objCandidateId;
	}

	@Column(name = "OBJ_CANDIDATE_ID_VALUE", length = 200)
	public String getObjCandidateIdValue() {
		return this.objCandidateIdValue;
	}

	public void setObjCandidateIdValue(String objCandidateIdValue) {
		this.objCandidateIdValue = objCandidateIdValue;
	}

	@Column(name = "OBJ_CANDIDATE_NAME", length = 300)
	public String getObjCandidateName() {
		return this.objCandidateName;
	}

	public void setObjCandidateName(String objCandidateName) {
		this.objCandidateName = objCandidateName;
	}

	@Column(name = "OBJ_CANDIDATE_NAME_VALUE", length = 300)
	public String getObjCandidateNameValue() {
		return this.objCandidateNameValue;
	}

	public void setObjCandidateNameValue(String objCandidateNameValue) {
		this.objCandidateNameValue = objCandidateNameValue;
	}

	@Column(name = "OBJ_PERSON_ID", length = 300)
	public String getObjPersonId() {
		return this.objPersonId;
	}

	public void setObjPersonId(String objPersonId) {
		this.objPersonId = objPersonId;
	}

	@Column(name = "OBJ_PERSON_ID_VALUE", length = 350)
	public String getObjPersonIdValue() {
		return this.objPersonIdValue;
	}

	public void setObjPersonIdValue(String objPersonIdValue) {
		this.objPersonIdValue = objPersonIdValue;
	}

	@Column(name = "OBJ_PERSON_NAME", length = 300)
	public String getObjPersonName() {
		return this.objPersonName;
	}

	public void setObjPersonName(String objPersonName) {
		this.objPersonName = objPersonName;
	}

	@Column(name = "OBJ_PERSON_NAME_VALUE", length = 350)
	public String getObjPersonNameValue() {
		return this.objPersonNameValue;
	}

	public void setObjPersonNameValue(String objPersonNameValue) {
		this.objPersonNameValue = objPersonNameValue;
	}

	@Column(name = "RELATE_ROLE_ID", length = 300)
	public String getRelateRoleId() {
		return this.relateRoleId;
	}

	public void setRelateRoleId(String relateRoleId) {
		this.relateRoleId = relateRoleId;
	}

	@Column(name = "RELATE_ROLE_NAME", length = 200)
	public String getRelateRoleName() {
		return this.relateRoleName;
	}

	public void setRelateRoleName(String relateRoleName) {
		this.relateRoleName = relateRoleName;
	}

	@Column(name = "RELATE_CONDITION", length = 50)
	public String getRelateCondition() {
		return this.relateCondition;
	}

	public void setRelateCondition(String relateCondition) {
		this.relateCondition = relateCondition;
	}

	@Column(name = "RELATE_VARIABLE", length = 100)
	public String getRelateVariable() {
		return this.relateVariable;
	}

	public void setRelateVariable(String relateVariable) {
		this.relateVariable = relateVariable;
	}

	@Column(name = "RELATE_VARIABLE_VALUE", length = 100)
	public String getRelateVariableValue() {
		return this.relateVariableValue;
	}

	public void setRelateVariableValue(String relateVariableValue) {
		this.relateVariableValue = relateVariableValue;
	}

	@Column(name = "RELATE_EXPLAIN", length = 450)
	public String getRelateExplain() {
		return this.relateExplain;
	}

	public void setRelateExplain(String relateExplain) {
		this.relateExplain = relateExplain;
	}

	@Column(name = "IS_SELECT", length = 1)
	public String getIsSelect() {
		return this.isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	@Column(name = "LIMIT_PERSON_ID", length = 1000)
	public String getLimitPersonId() {
		return this.limitPersonId;
	}

	public void setLimitPersonId(String limitPersonId) {
		this.limitPersonId = limitPersonId;
	}

	@Column(name = "LIMIT_PERSON_NAME", length = 1000)
	public String getLimitPersonName() {
		return this.limitPersonName;
	}

	public void setLimitPersonName(String limitPersonName) {
		this.limitPersonName = limitPersonName;
	}

	@Column(name = "LIMIT_ROLE_ID", length = 1000)
	public String getLimitRoleId() {
		return this.limitRoleId;
	}

	public void setLimitRoleId(String limitRoleId) {
		this.limitRoleId = limitRoleId;
	}

	@Column(name = "LIMIT_ROLE_NAME", length = 1000)
	public String getLimitRoleName() {
		return this.limitRoleName;
	}

	public void setLimitRoleName(String limitRoleName) {
		this.limitRoleName = limitRoleName;
	}

	@Column(name = "LIMIT_ORG_ID", length = 1000)
	public String getLimitOrgId() {
		return this.limitOrgId;
	}

	public void setLimitOrgId(String limitOrgId) {
		this.limitOrgId = limitOrgId;
	}

	@Column(name = "LIMIT_ORG_NAME", length = 1000)
	public String getLimitOrgName() {
		return this.limitOrgName;
	}

	public void setLimitOrgName(String limitOrgName) {
		this.limitOrgName = limitOrgName;
	}

	@Column(name = "CANDIDATE_REMARK", length = 450)
	public String getCandidateRemark() {
		return this.candidateRemark;
	}

	public void setCandidateRemark(String candidateRemark) {
		this.candidateRemark = candidateRemark;
	}

}