package com.tataelxsi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EmployeeGrade")
public class EmployeeGrade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EmployeeGradeId")
	private int employeeGradeId;
	@Column(name = "WonNo")
	private int wonNo;
	@Column(name = "ProjectName")
	private String projectName;
	
	@OneToOne
	@JoinColumn(name = "emp_no", referencedColumnName = "empNo")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "gradeId", referencedColumnName = "gradeId")
	private Grade grade;
	

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	
	public int getEmployeeGradeId() {
		return employeeGradeId;
	}

	public void setEmployeeGradeId(int employeeGradeId) {
		this.employeeGradeId = employeeGradeId;
	}

	public int getWonNo() {
		return wonNo;
	}

	public void setWonNo(int wonNo) {
		this.wonNo = wonNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public EmployeeGrade() {
		super();
	}

	public EmployeeGrade(int employeeGradeId, int wonNo, String projectName) {
		super();
		this.employeeGradeId = employeeGradeId;
		this.wonNo = wonNo;
		this.projectName = projectName;
	}
	


}
