package com.tataelxsi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class SkillSetSkillList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "skillId", referencedColumnName = "skillId")
	private Skill skill;
	@OneToOne
	@JoinColumn(name = "skillSetId", referencedColumnName = "skillSetId")
	private SkillSet skillset;
	
	public Skill getSkill() {
		return skill;
	}
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	public SkillSet getSkillset() {
		return skillset;
	}
	public void setSkillset(SkillSet skillset) {
		this.skillset = skillset;
	}
	public SkillSetSkillList() {
		super();
	}

}
