package com.tataelxsi.Services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tataelxsi.entity.DesiredSkillLevel;
import com.tataelxsi.entity.Employee;
import com.tataelxsi.entity.EmployeeGrade;
import com.tataelxsi.entity.EmployeeLevel;
import com.tataelxsi.entity.EmployeeSkills;
import com.tataelxsi.entity.Grade;
import com.tataelxsi.entity.Level;
import com.tataelxsi.entity.Login;
import com.tataelxsi.entity.Skill;
import com.tataelxsi.entity.SkillSet;
import com.tataelxsi.entity.Supervisor;

public interface EmployeeService {
	ResponseEntity<Object> uploadExcel(MultipartFile files);

	Grade createGrade(Grade grade);

	ResponseEntity<Grade> getGradeById(@PathVariable Integer gradeId);

	List<Grade> getAllGrade();

	ResponseEntity<Map<String, Boolean>> deleteGradeById(Integer gradeId);

	ResponseEntity<Grade> updateGradeById(Integer gradeId, Grade gradeDetails);

	Skill createSkill(Skill skill);

	List<Skill> getAllSkills();

	ResponseEntity<Skill> getSkillById(Integer skillId);

	ResponseEntity<Map<String, Boolean>> deleteSkillById(Integer skillId);

	ResponseEntity<Skill> updateSkillById(Integer skillId, Skill skillDetails);

	Level createLevel(Level level);

	ResponseEntity<Level> getLevelById(Integer levelId);

	List<Level> getAllLevel();

	ResponseEntity<Map<String, Boolean>> deleteLevelById(Integer levelId);

	ResponseEntity<Level> updateLevelById(Integer levelId, Level levelDetails);

	SkillSet createSkillSet(SkillSet skill);

	ResponseEntity<SkillSet> getSkillSetById(Integer skillSetId);

	List<SkillSet> getAllSkillSet();

	ResponseEntity<Map<String, Boolean>> deleteSkillSetById(Integer skillSetId);

	ResponseEntity<SkillSet> updateSkillSetById(Integer skillSetId, SkillSet skillSetDetails);

	EmployeeLevel createEmployeeLevel(EmployeeLevel employeeLevel);

	DesiredSkillLevel createDesiredSkillLevell(DesiredSkillLevel desiredSkillLevel);

	EmployeeSkills createEmployeeSkills(EmployeeSkills employeeSkills);

	ResponseEntity<Map<String, Object>> getAllEmloyees(String search, int page, int size, String[] sort);
	ResponseEntity<Map<String, Object>> getAllSupervisors(String search, int page, int size, String[] sort);
	ResponseEntity<Map<String, Object>> getAllDesiredSkills(String search, int page, int size, String[] sort);
	ResponseEntity<Map<String, Object>> getAllEmloyeeGrades(String search, int page, int size, String[] sort);
	ResponseEntity<Map<String, Object>> getAllEmloyeeLevels(String search, int page, int size, String[] sort);
	ResponseEntity<Map<String, Object>> getAllEmloyeeSkills(String search, int page, int size, String[] sort);
	ResponseEntity<Map<String, Object>> getAllGrades(String search, int page, int size, String[] sort);
	ResponseEntity<Map<String, Object>> getAllLevels(String search, int page, int size, String[] sort);
	ResponseEntity<Map<String, Object>> getAllSkillSets(String search, int page, int size, String[] sort);
	ResponseEntity<Map<String, Object>> getAllSkills(String search, int page, int size, String[] sort);

	List<Employee> getAllEmployees();

	List<Supervisor> getAllSupervisor();

	List<EmployeeGrade> getAllEmployeeGrade();

	List<EmployeeSkills> getAllEmployeeSkills();

	List<DesiredSkillLevel> getAllDesiredSkillLevel();

	Sort.Direction getSortDirection(String direction);
	

	//public Employee findByEmpNoAndEmployeeName(Integer empNo,String employeeName,String emailId);
	
	
	public Login findByUserNameAndPassword(String userName,String password) throws Exception;

}
