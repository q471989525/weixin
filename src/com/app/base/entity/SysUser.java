package com.app.base.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.core.entity.BaseEntity;
import com.app.utils.DateTimeUtil;

/**
 * 
 * TODO：系统用户
 * 
 */
@Entity
@Table(name = "SYS_USER")
public class SysUser extends BaseEntity implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;
	private String username; // 名称
	private String loginId; // 登陆贴
	private String password; // 密码
	private String enName; // 英文名
	private String employeeId; // 员工ID
	private String post; // 岗位
	private String email; // 邮箱
	private String qq;
	private String mobile; // 手机
	private String officePhone; // 办公电话
	private String dutyDesc; // 职责
	private String headImage; // 头像地址
	private String sex; // 性别
	private Date birthday; // 生日
	private Date joinDate; // 入职日期
	private String stateFlag; // 状态 1:在职，2：离职，3：待定
	private String isEnabled; // 激活标志 y:激活，n：未激活
	private Integer orderBy;
	private String remark;
	private String deleteState; // n:未删除
	private String showState; // 隐藏状态 y:显示，h:隐藏
	private String duty; // 职务

	// temp 格式化
	private String birthdayFmt;
	private String joinDateFmt;

	private String orgId; // 组织ID
	private String orgName; // 组织名称
	private String postName; // 岗位名称
	private String roleId; // 角色ID
	private List<SysRole> roles; // 拥有的角色集合

	@Transient
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Transient
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Transient
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Transient
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	@Transient
	public String getBirthdayFmt() {
		birthdayFmt = birthday != null ? DateTimeUtil.formatDate(birthday) : "";
		return birthdayFmt;
	}

	@Transient
	public String getJoinDateFmt() {
		joinDateFmt = joinDate != null ? DateTimeUtil.formatDate(joinDate) : "";
		return joinDateFmt;
	}

	public SysUser() {
	}

	@Column(name = "USERNAME", length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "LOGIN_ID", length = 50)
	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	@Column(name = "PASSWORD", length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "EN_NAME", length = 50)
	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Column(name = "EMPLOYEE_ID", length = 50)
	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "POST", length = 50)
	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "EMAIL", length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "QQ", length = 50)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "MOBILE", length = 100)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "OFFICE_PHONE", length = 100)
	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	@Column(name = "DUTY_DESC", length = 650)
	public String getDutyDesc() {
		return this.dutyDesc;
	}

	public void setDutyDesc(String dutyDesc) {
		this.dutyDesc = dutyDesc;
	}

	@Column(name = "HEAD_IMAGE", length = 150)
	public String getHeadImage() {
		return this.headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	@Column(name = "SEX", length = 50)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "JOIN_DATE", length = 7)
	public Date getJoinDate() {
		return this.joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	@Column(name = "STATE_FLAG", length = 1)
	public String getStateFlag() {
		return stateFlag;
	}

	public void setStateFlag(String stateFlag) {
		this.stateFlag = stateFlag;
	}

	@Column(name = "IS_ENABLED", length = 1)
	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "ORDER_BY")
	public Integer getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DELETE_STATE", length = 1)
	public String getDeleteState() {
		return this.deleteState;
	}

	public void setDeleteState(String deleteState) {
		this.deleteState = deleteState;
	}

	@Column(name = "SHOW_STATE", length = 1)
	public String getShowState() {
		return showState;
	}

	public void setShowState(String showState) {
		this.showState = showState;
	}

	@Column(name = "DUTY", length = 50)
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Transient
	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 锁定标志,在职状态才能进入系统
	 */
	@Transient
	public boolean isAccountNonLocked() {
		return StringUtils.equalsIgnoreCase(stateFlag, "1") ? true : false;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 激活标志
	 */
	@Transient
	public boolean isEnabled() {
		return StringUtils.equalsIgnoreCase(isEnabled, "y") ? true : false;
		// return true;
	}

	// 重写equals与hashCode，判断用户相等
	public boolean equals(Object rhs) {
		if (!(rhs instanceof SysUser) || (rhs == null)) {
			return false;
		}

		SysUser user = (SysUser) rhs;

		return (this.getPassword().equals(user.getPassword()) && this.getUsername().equals(user.getUsername()) && (this.isAccountNonExpired() == user.isAccountNonExpired()) && (this.isAccountNonLocked() == user.isAccountNonLocked())
				&& (this.isCredentialsNonExpired() == user.isCredentialsNonExpired()) && (this.isEnabled() == user.isEnabled()));
	}

	public int hashCode() {
		int code = 9792;
		for (GrantedAuthority authority : getAuthorities()) {
			code = code * (authority.hashCode() % 7);
		}
		if (this.getPassword() != null) {
			code = code * (this.getPassword().hashCode() % 7);
		}
		if (this.getUsername() != null) {
			code = code * (this.getUsername().hashCode() % 7);
		}
		if (this.isAccountNonExpired()) {
			code = code * -2;
		}
		if (this.isAccountNonLocked()) {
			code = code * -3;
		}
		if (this.isCredentialsNonExpired()) {
			code = code * -5;
		}
		if (this.isEnabled()) {
			code = code * -7;
		}
		return code;
	}

	// 组装用户拥有角色成spring security验证
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		if (getRoles() != null && getRoles().size() > 0) {
			for (SysRole role : getRoles()) {
				list.add(new SimpleGrantedAuthority(role.getRoleCode()));
			}
		}
		return list;
	}

}