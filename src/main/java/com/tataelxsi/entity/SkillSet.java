package com.tataelxsi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table
public class SkillSet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int skillSetId;
	@Column(name = "SkillSetName",unique = true)
	private String skillSetName;
	
	public int getSkillSetId() {
		return skillSetId;
	}

	public void setSkillSetId(int skillSetId) {
		this.skillSetId = skillSetId;
	}

	public String getSkillSetName() {
		return skillSetName;
	}

	public void setSkillSetName(String skillSetName) {
		this.skillSetName = skillSetName;
	}

	
	public SkillSet() {
		super();

	}

	public SkillSet(int skillSetId, String skillSetName) {
		super();
		this.skillSetId = skillSetId;
		this.skillSetName = skillSetName;
	}
	


}
