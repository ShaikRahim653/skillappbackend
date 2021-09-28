package com.tataelxsi.entity;

import java.util.List;

public class EmployeeDto {

	private List<String> empName;
	private List<String> grade;
	private String selfRating;
	private String appRating;

	/*
	 * public int getEmpNo() { return empNo; } public void setEmpNo(int empNo) {
	 * this.empNo = empNo; }
	 */
	public List<String> getEmpName() {
		return empName;
	}

	public void setEmpName(List<String> empName) {
		this.empName = empName;
	}

	public List<String> getGrade() {
		return grade;
	}

	public void setGrade(List<String> grade) {
		this.grade = grade;
	}

	public String getSelfRating() {
		return selfRating;
	}

	public void setSelfRating(String selfRating) {
		this.selfRating = selfRating;
	}

	public String getAppRating() {
		return appRating;
	}

	public void setAppRating(String appRating) {
		this.appRating = appRating;
	}

	

}
