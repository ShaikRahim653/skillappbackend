package com.tataelxsi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Employee {
	@Id
	@Column(name="EmpNo")
	private int empNo;
	@Column(name="EmployeeName")
	private String employeeName;
	@Column(name="EmailId")
	private String emailId;
	
	private Integer healthId;
	
	public Employee(int empNo, String employeeName, String emailId, Integer healthId) {
		super();
		this.empNo = empNo;
		this.employeeName = employeeName;
		this.emailId = emailId;
		this.healthId = healthId;
	}
	public Integer getHealthId() {
		return healthId;
	}
	public void setHealthId(Integer healthId) {
		this.healthId = healthId;
	}
	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Employee() {
		super();
	}
	public Employee(int empNo, String employeeName, String emailId) {
		super();
		this.empNo = empNo;
		this.employeeName = employeeName;
		this.emailId = emailId;
	}
	
	
	

}
