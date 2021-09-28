package com.tataelxsi.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
//@Data
@Entity
@Table
public class DesiredSkillLevel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int desiredSkillId;
	private int desiredValue;
	
	public int getDesiredSkillId() {
		return desiredSkillId;
	}

	public void setDesiredSkillId(int desiredSkillId) {
		this.desiredSkillId = desiredSkillId;
	}

	public int getDesiredValue() {
		return desiredValue;
	}

	public void setDesiredValue(int desiredValue) {
		this.desiredValue = desiredValue;
	}

	public DesiredSkillLevel() {
		super();
	}



	public DesiredSkillLevel(int desiredSkillId, int desiredValue) {
		super();
		this.desiredSkillId = desiredSkillId;
		this.desiredValue = desiredValue;
	}



	@OneToOne
	@JoinColumn(name = "gradeId", referencedColumnName = "gradeId")
	private Grade grade;
	@OneToOne
	@JoinColumn(name = "skill_id", referencedColumnName = "skillId")
	private Skill skill;
	@OneToOne
	@JoinColumn(name = "levelId", referencedColumnName = "levelId")
	private Level level;

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}


}
