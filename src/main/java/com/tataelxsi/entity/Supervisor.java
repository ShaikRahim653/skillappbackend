package com.tataelxsi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Supervisor")
public class Supervisor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SupervisorId")
	private int supervisorId;
	@Column(name = "SupervisorEmployeeNo")
	private int supervisorEmpNo;
	@Column(name = "SupervisorEmployeeName")
	private String supervisorEmpName;
	@Column(name = "SupervisorEmployeeEmailId")
	private String supervisorEmpEmailId;

	
	public int getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
	}

	@OneToOne
	@JoinColumn(name = "emp_no", referencedColumnName = "empNo")
	private Employee employee;
	

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	

	public int getSupervisorEmpNo() {
		return supervisorEmpNo;
	}

	public void setSupervisorEmpNo(int supervisorEmpNo) {
		this.supervisorEmpNo = supervisorEmpNo;
	}

	public String getSupervisorEmpName() {
		return supervisorEmpName;
	}

	public void setSupervisorEmpName(String supervisorEmpName) {
		this.supervisorEmpName = supervisorEmpName;
	}

	public String getSupervisorEmpEmailId() {
		return supervisorEmpEmailId;
	}

	public void setSupervisorEmpEmailId(String supervisorEmpEmailId) {
		this.supervisorEmpEmailId = supervisorEmpEmailId;
	}

	public Supervisor(int supervisorId, int supervisorEmpNo, String supervisorEmpName, String supervisorEmpEmailId) {
		super();
		this.supervisorId = supervisorId;
		this.supervisorEmpNo = supervisorEmpNo;
		this.supervisorEmpName = supervisorEmpName;
		this.supervisorEmpEmailId = supervisorEmpEmailId;
	}

	public Supervisor() {
		// TODO Auto-generated constructor stub
	}

}
