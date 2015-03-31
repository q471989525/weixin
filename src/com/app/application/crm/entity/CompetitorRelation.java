package com.app.application.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：竞争对手关系表
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_COMPETITOR_RELATION")
public class CompetitorRelation extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String competitorId;
	private String relationId;

	public CompetitorRelation() {
	}

	public CompetitorRelation(String competitorId, String relationId) {
		super();
		this.competitorId = competitorId;
		this.relationId = relationId;
	}

	@Column(name = "COMPETITOR_ID", length = 32)
	public String getCompetitorId() {
		return this.competitorId;
	}

	public void setCompetitorId(String competitorId) {
		this.competitorId = competitorId;
	}

	@Column(name = "RELATION_ID", length = 32)
	public String getRelationId() {
		return this.relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

}