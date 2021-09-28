package com.tataelxsi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class Grade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GradeId")
	private int gradeId;
	@Column(name = "GradeLevel", unique = true)
	private String gradeLevel;
	@Column(name = "GradeDesc", unique = true)
	private String gradeDesc;

	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public String getGradeDesc() {
		return gradeDesc;
	}

	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
	}

	public Grade() {
		super();
	}

	public Grade(int gradeId, String gradeLevel, String gradeDesc) {
		super();
		this.gradeId = gradeId;
		this.gradeLevel = gradeLevel;
		this.gradeDesc = gradeDesc;
	}
	

}
