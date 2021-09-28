package com.tataelxsi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Skill {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "SkillId")
	private int skillId;
	@Column(name = "SkillName",unique = true)
	private String skillName;

	public int getSkillId() {
		return skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Skill() {
		super();
	}

	public Skill(int skillId, String skillName) {
		super();
		this.skillId = skillId;
		this.skillName = skillName;
	}
	


}
