package com.tataelxsi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class EmployeeSkills {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int employeeSkillId;

	private int reportTimeStamp;
	private int selfRating;
	private int approveRating;

	public int getReportTimeStamp() {
		return reportTimeStamp;
	}

	public void setReportTimeStamp(int reportTimeStamp) {
		this.reportTimeStamp = reportTimeStamp;
	}

	public int getSelfRating() {
		return selfRating;
	}

	public void setSelfRating(int selfRating) {
		this.selfRating = selfRating;
	}

	public int getApproveRating() {
		return approveRating;
	}

	public void setApproveRating(int approveRating) {
		this.approveRating = approveRating;
	}

	public int getEmployeeSkillId() {
		return employeeSkillId;
	}

	public void setEmployeeSkillId(int employeeSkillId) {
		this.employeeSkillId = employeeSkillId;
	}

	@ManyToOne
	@JoinColumn(name = "skill_id", referencedColumnName = "skillId")
	private Skill skill;
	@ManyToOne
	@JoinColumn(name = "emp_no", referencedColumnName = "empNo")
	private Employee employee;

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EmployeeSkills(int employeeSkillId, int selfRating, int approveRating) {
		super();
		this.employeeSkillId = employeeSkillId;
		this.selfRating = selfRating;
		this.approveRating = approveRating;
	}

	

	public EmployeeSkills() {
		// TODO Auto-generated constructor stub
	}
	
}
