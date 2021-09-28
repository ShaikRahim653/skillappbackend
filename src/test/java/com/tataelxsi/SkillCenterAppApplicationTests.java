package com.tataelxsi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.tataelxsi.Services.EmployeeServiceImpl;
import com.tataelxsi.controller.EmployeeController;
import com.tataelxsi.entity.DesiredSkillLevel;
import com.tataelxsi.entity.Employee;
import com.tataelxsi.entity.EmployeeGrade;
import com.tataelxsi.entity.EmployeeSkills;
import com.tataelxsi.entity.Supervisor;

@SpringBootTest
class SkillCenterAppApplicationTests {

//	@Test
//	void contextLoads() {
//	}
//	@InjectMocks
//	EmployeeController employeeController;
//
//	@Mock
//	EmployeeServiceImpl employeeService;
//
//	@Test
//	public void getAllEmployeesTest() {
//		List<Employee> list = new ArrayList<Employee>();
//		Employee empOne = new Employee(1, "Rahim", "Rahim@tataelxsi.co.in");
//		Employee empTwo = new Employee(2, "Raghava", "Raghava@tataelxsi.co.in");
//		list.add(empOne);
//		list.add(empTwo);
//		when(employeeService.getAllEmployees()).thenReturn(list);
//
//		List<Employee> cust = employeeController.getAllEmployees();
//		assertEquals(2, cust.size());
//	}
//
//	@Test
//	public void getAllEmployeeGradeTest() {
//		List<EmployeeGrade> list = new ArrayList<EmployeeGrade>();
//		EmployeeGrade empGradeOne = new EmployeeGrade(101, 89, "FullStack");
//		EmployeeGrade empGradeTwo = new EmployeeGrade(102, 90, "FullStack");
//		list.add(empGradeOne);
//		list.add(empGradeTwo);
//		when(employeeService.getAllEmployeeGrade()).thenReturn(list);
//
//		List<EmployeeGrade> cust = employeeController.getAllEmployeeGrade();
//		assertEquals(2, cust.size());
//	}
//
//	@Test
//	public void getAllSuperviorTest() {
//		List<Supervisor> list = new ArrayList<Supervisor>();
//		Supervisor supOne = new Supervisor(101, 102, "Rahul", "Rahul@tataelxsi.co.in");
//		Supervisor supTwo = new Supervisor(101, 102, "rahim", "rahim@tataelxsi.co.in");
//		list.add(supOne);
//		list.add(supTwo);
//		when(employeeService.getAllSupervisor()).thenReturn(list);
//
//		List<Supervisor> cust = employeeController.getAllSupervisor();
//		assertEquals(2, cust.size());
//	}
//
//	@Test
//	public void getAllEmpSkillsTest() {
//		List<EmployeeSkills> list = new ArrayList<EmployeeSkills>();
//		EmployeeSkills empSOne = new EmployeeSkills(101, 2, 2);
//		EmployeeSkills empSTwo = new EmployeeSkills(102, 3, 4);
//		list.add(empSOne);
//		list.add(empSTwo);
//		when(employeeService.getAllEmployeeSkills()).thenReturn(list);
//
//		List<EmployeeSkills> cust = employeeController.getAllEmployeeSkills();
//		assertEquals(2, cust.size());
//	}
//	
//	@Test
//	public void getAllDesirelLevleTest() {
//		List<DesiredSkillLevel> list = new ArrayList<DesiredSkillLevel>();
//		DesiredSkillLevel deOne = new DesiredSkillLevel(10,3);
//		DesiredSkillLevel deTwo = new DesiredSkillLevel(101, 2);
//		list.add(deOne);
//		list.add(deTwo);
//		when(employeeService.getAllDesiredSkillLevel()).thenReturn(list);
//
//		List<DesiredSkillLevel> cust = employeeController.getAllDesiredskilllevel();
//		assertEquals(2, cust.size());
//	}

}
