package com.tataelxsi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tataelxsi.Repository.EmployeeRepository;
import com.tataelxsi.Repository.LoginRepository;
import com.tataelxsi.Services.EmployeeServiceImpl;
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

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EmployeeController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	EmployeeServiceImpl employeeServiceImpl;
	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	LoginRepository loginRepository;
	@PostMapping("/parseData")
	public Object uploadExcel(@RequestParam("file") MultipartFile files) throws IOException {
		logger.info("file is uploading.....");
		return employeeServiceImpl.uploadExcel(files);
	}

	@PostMapping("/grade")
	public Grade saveGrade(@RequestBody Grade grade) {

		Grade grades = employeeServiceImpl.createGrade(grade);
		return grades;
	}

	@GetMapping("/grade/{gradeId}")
	public ResponseEntity<Grade> getGradeById(@PathVariable int gradeId) {

		return employeeServiceImpl.getGradeById(gradeId);
	}

	@GetMapping("/grade")
	public List<Grade> getAllGrades(Grade grade) {
		List<Grade> grades = employeeServiceImpl.getAllGrade();
		return grades;
	}

	@PutMapping("/grade/{gradeId}")
	public ResponseEntity<Grade> updateGrade(@PathVariable int gradeId, @RequestBody Grade grade) {
		ResponseEntity<Grade> grades = employeeServiceImpl.updateGradeById(gradeId, grade);
		return grades;
	}

	@DeleteMapping("/grade/{gradeId}")
	public String deleteGradeById(@PathVariable int gradeId) {
		employeeServiceImpl.deleteGradeById(gradeId);
		return "Deleted ";
	}

	@PostMapping("/skill")
	public Skill saveSkill(@RequestBody Skill skill) {

		Skill skills = employeeServiceImpl.createSkill(skill);
		return skills;
	}

	@GetMapping("/skill/{skillId}")
	public ResponseEntity<Skill> getSkillById(@PathVariable int skillId) {
		return employeeServiceImpl.getSkillById(skillId);
	}

	@GetMapping("/skill")
	public List<Skill> getAllSkills(Skill skill) {
		List<Skill> skills = employeeServiceImpl.getAllSkills();
		return skills;
	}

	@PutMapping("/skill/{skillId}")
	public ResponseEntity<Skill> updateSkillById(@PathVariable int skillId, @RequestBody Skill skill) {
		ResponseEntity<Skill> skills = employeeServiceImpl.updateSkillById(skillId, skill);
		return skills;
	}

	@DeleteMapping("/skill/{skillId}")
	public String deleteSkill(@PathVariable int skillId) {
		employeeServiceImpl.deleteSkillById(skillId);
		return "deleted";
	}

	@PostMapping("/level")
	public Level saveLevel(@RequestBody Level level) {

		Level levels = employeeServiceImpl.createLevel(level);
		return levels;
	}

	@GetMapping("/level/{levelId}")
	public ResponseEntity<Level> getLevelById(@PathVariable int levelId) {
		return employeeServiceImpl.getLevelById(levelId);
	}

	@GetMapping("/level")
	public List<Level> getAllLevels(Level level) {
		List<Level> levels = employeeServiceImpl.getAllLevel();
		return levels;
	}

	@PutMapping("/level/{levelId}")
	public ResponseEntity<Level> updateLevel(@PathVariable int levelId, @RequestBody Level level) {
		ResponseEntity<Level> levels = employeeServiceImpl.updateLevelById(levelId, level);
		return levels;
	}

	@DeleteMapping("/level/{levelId}")
	public String deleteLevel(@PathVariable int levelId) {
		employeeServiceImpl.deleteLevelById(levelId);
		return "deleted";
	}

	@PostMapping("/skillset")
	public SkillSet saveSkillSet(@RequestBody SkillSet skillSet) {

		SkillSet skillSets = employeeServiceImpl.createSkillSet(skillSet);
		return skillSets;
	}

	@GetMapping("/skillset/{skillSetId}")
	public ResponseEntity<SkillSet> getskillSetById(@PathVariable int skillSetId) {
		return employeeServiceImpl.getSkillSetById(skillSetId);
	}

	@GetMapping("/skillset")
	public List<SkillSet> getAllSkillSets(SkillSet skillSet) {
		List<SkillSet> skillSets = employeeServiceImpl.getAllSkillSet();
		return skillSets;
	}

	@PutMapping("/skillset/{skillSetId}")
	public ResponseEntity<SkillSet> updateSkillSetById(@PathVariable int skillSetId, @RequestBody SkillSet skillSet) {
		ResponseEntity<SkillSet> skillSets = employeeServiceImpl.updateSkillSetById(skillSetId, skillSet);
		return skillSets;
	}

	@DeleteMapping("/skillset/{skillSetId}")
	public String deleteSkillSetById(@PathVariable int skillSetId) {
		employeeServiceImpl.deleteSkillSetById(skillSetId);
		return "Deleted ";
	}

	@PostMapping("/employeelevel")
	public EmployeeLevel saveEmployeeLevel(@RequestBody EmployeeLevel employeeLevel) {

		EmployeeLevel employeeLevels = employeeServiceImpl.createEmployeeLevel(employeeLevel);
		return employeeLevels;
	}

	@PostMapping("/desiredSkills")
	public DesiredSkillLevel saveDesiredSkills(@RequestBody DesiredSkillLevel desiredSkillLevbel) {

		DesiredSkillLevel desiredSkillLevels = employeeServiceImpl.createDesiredSkillLevell(desiredSkillLevbel);
		return desiredSkillLevels;
	}

	@PostMapping("/employeeskills")
	public EmployeeSkills saveEmployeeSkills(@RequestBody EmployeeSkills employeeSkills) {

		EmployeeSkills EmployeeSkill = employeeServiceImpl.createEmployeeSkills(employeeSkills);
		return EmployeeSkill;
	}

//	@GetMapping("/employees/page")
//	public ResponseEntity<List<Employee>> getAllEmployeesByPagingAndSorting(
//			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
//			@RequestParam(defaultValue = "id") String sortBy) {
//		List<Employee> list = employeeServiceImpl.getAllEmployees(pageNo, pageSize, sortBy);
//
//		return new ResponseEntity<List<Employee>>(list, new HttpHeaders(), HttpStatus.OK);
//	}

//	@GetMapping("/supervisor/page")
//	public ResponseEntity<List<Supervisor>> getAllSupervisorsByPagingAndSorting(
//			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
//			@RequestParam(defaultValue = "id") String sortBy) {
//		List<Supervisor> list = employeeServiceImpl.getAllSupervisors(pageNo, pageSize, sortBy);
//
//		return new ResponseEntity<List<Supervisor>>(list, new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@GetMapping("/employeeGrades/page")
//	public ResponseEntity<List<EmployeeGrade>> getAllEmployeeGradesByPagingAndSorting(
//			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
//			@RequestParam(defaultValue = "id") String sortBy) {
//		List<EmployeeGrade> list = employeeServiceImpl.getAllEmployeeGrades(pageNo, pageSize, sortBy);
//
//		return new ResponseEntity<List<EmployeeGrade>>(list, new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@GetMapping("/employeeSkills/page")
//	public ResponseEntity<List<EmployeeSkills>> getAllEmployeeSkillsByPagingAndSorting(
//			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
//			@RequestParam(defaultValue = "id") String sortBy) {
//		List<EmployeeSkills> list = employeeServiceImpl.getAllEmployeeSkills(pageNo, pageSize, sortBy);
//
//		return new ResponseEntity<List<EmployeeSkills>>(list, new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@GetMapping("/desiredskilllevel/page")
//	public ResponseEntity<List<DesiredSkillLevel>> getAllDesiredSkillLevelByPagingAndSorting(
//			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
//			@RequestParam(defaultValue = "id") String sortBy) {
//		List<DesiredSkillLevel> list = employeeServiceImpl.getAllDesiredSkillLevels(pageNo, pageSize, sortBy);
//
//		return new ResponseEntity<List<DesiredSkillLevel>>(list, new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@GetMapping("/level/page")
//	public ResponseEntity<List<Level>> getAllLevelByPagingAndSorting(@RequestParam(defaultValue = "0") Integer pageNo,
//			@RequestParam(defaultValue = "10") Integer pageSize,
//			@RequestParam(defaultValue = "id") String sortBy) {
//		List<Level> list = employeeServiceImpl.getAllLevels(pageNo, pageSize, sortBy);
//
//		return new ResponseEntity<List<Level>>(list, new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@GetMapping("/grade/page")
//	public ResponseEntity<List<Grade>> getAllGradeByPagingAndSorting(@RequestParam(defaultValue = "0") Integer pageNo,
//			@RequestParam(defaultValue = "10") Integer pageSize,
//			@RequestParam(defaultValue = "id") String sortBy) {
//		List<Grade> list = employeeServiceImpl.getAllGrades(pageNo, pageSize, sortBy);
//
//		return new ResponseEntity<List<Grade>>(list, new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@GetMapping("/skill/page")
//	public ResponseEntity<List<Skill>> getAllSkillByPagingAndSorting(@RequestParam(defaultValue = "0") Integer pageNo,
//			@RequestParam(defaultValue = "10") Integer pageSize,
//			@RequestParam(defaultValue = "id") String sortBy) {
//		List<Skill> list = employeeServiceImpl.getAllSkills(pageNo, pageSize, sortBy);
//
//		return new ResponseEntity<List<Skill>>(list, new HttpHeaders(), HttpStatus.OK);
//	}
//
//	@GetMapping("/skillset/page")
//	public ResponseEntity<List<SkillSet>> getAllSkillSetByPagingAndSorting(
//			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
//			@RequestParam(defaultValue = "id") String sortBy) {
//		List<SkillSet> list = employeeServiceImpl.getAllSkillSets(pageNo, pageSize, sortBy);
//
//		return new ResponseEntity<List<SkillSet>>(list, new HttpHeaders(), HttpStatus.OK);
//	}

	@GetMapping("/employee")
	public List<Employee> getAllEmployees() {
		List<Employee> employees = employeeServiceImpl.getAllEmployees();
		return employees;
	}
	@GetMapping("/employee/{empNo}")
	public Employee getAllEmployeesById(@PathVariable Integer empNo) {
		
		 Employee emp=empRepo.findByEmpNo(empNo);
		 return emp;
		
	}
	@GetMapping("/employee/health/{healthId}")
	public Employee getAllEmployeesByHealthId(@PathVariable Integer healthId) {
		
		 Employee emp=empRepo.findByHealthId(healthId);
		 return emp;
		
	}
	@GetMapping("/employeeGrade")
	public List<EmployeeGrade> getAllEmployeeGrade() {
		List<EmployeeGrade> employeeGrades = employeeServiceImpl.getAllEmployeeGrade();
		return employeeGrades;
	}

	@GetMapping("/supervisor")
	public List<Supervisor> getAllSupervisor() {
		List<Supervisor> supervisors = employeeServiceImpl.getAllSupervisor();
		return supervisors;
	}

	@GetMapping("/employeeSkills")
	public List<EmployeeSkills> getAllEmployeeSkills() {
		List<EmployeeSkills> employeeSkills = employeeServiceImpl.getAllEmployeeSkills();
		return employeeSkills;
	}

	@GetMapping("/desiredskilllevel")
	public List<DesiredSkillLevel> getAllDesiredskilllevel() {
		List<DesiredSkillLevel> desiredskilllevel = employeeServiceImpl.getAllDesiredSkillLevel();
		return desiredskilllevel;
	}

    
    @GetMapping("/employees/page")
    public ResponseEntity<Map<String, Object>> getAllEmployees(@RequestParam(required = false)String search,
    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size,
    @RequestParam(defaultValue = "empNo,asc") String[] sort) {
    	ResponseEntity<Map<String, Object>> response = employeeServiceImpl.getAllEmloyees(search, page, size, sort);
    	 
		return  response;
    }
    @GetMapping("/supervisor/page")
    public ResponseEntity<Map<String, Object>> getAllSupercisors(@RequestParam(required = false)String search,
    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size,
    @RequestParam(defaultValue = "supervisorId,asc") String[] sort) {
    	ResponseEntity<Map<String, Object>> response = employeeServiceImpl.getAllSupervisors(search, page, size, sort);
		return  response;
    }
    @GetMapping("/desiredskilllevel/page")
    public ResponseEntity<Map<String, Object>> getAllDesiredSkillLevels(@RequestParam(required = false)String search,
    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size,
    @RequestParam(defaultValue = "desiredSkillId,asc") String[] sort) {
    	ResponseEntity<Map<String, Object>> response = employeeServiceImpl.getAllDesiredSkills(search, page, size, sort);
		return  response;
    }
    
	@GetMapping("/employeeGrades/page")
    public ResponseEntity<Map<String, Object>> getAllEmployeeGrades(@RequestParam(required = false)String search,
    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size,
    @RequestParam(defaultValue = "employeeGradeId,asc") String[] sort) {
    	ResponseEntity<Map<String, Object>> response = employeeServiceImpl.getAllEmloyeeGrades(search, page, size, sort);
		return  response;
    }
    public ResponseEntity<Map<String, Object>> getAllEmployeeLevels(@RequestParam(required = false)String search,
    	    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size,
    	    @RequestParam(defaultValue = "employeeLevelId,asc") String[] sort) {
    	    	ResponseEntity<Map<String, Object>> response = employeeServiceImpl.getAllEmloyeeLevels(search, page, size, sort);
    			return  response;
    	    }
    @GetMapping("/employeeSkills/page")
    public ResponseEntity<Map<String, Object>> getAllEmployeeSkills(@RequestParam(required = false)String search,
    	    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size,
    	    @RequestParam(defaultValue = "employeeSkillId,asc") String[] sort) {
    	    	ResponseEntity<Map<String, Object>> response = employeeServiceImpl.getAllEmloyeeSkills(search, page, size, sort);
    			return  response;
    	    }
    @GetMapping("/grade/page")
    public ResponseEntity<Map<String, Object>> getAllGrades(@RequestParam(required = false)String search,
    	    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size,
    	    @RequestParam(defaultValue = "gradeId,asc") String[] sort) {
    	    	ResponseEntity<Map<String, Object>> response = employeeServiceImpl.getAllGrades(search, page, size, sort);
    			return  response;
    	    }
    @GetMapping("/level/page")
    public ResponseEntity<Map<String, Object>> getAllLevels(@RequestParam(required = false)String search,
    	    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size,
    	    @RequestParam(defaultValue = "levelId,asc") String[] sort) {
    	    	ResponseEntity<Map<String, Object>> response = employeeServiceImpl.getAllLevels(search, page, size, sort);
    			return  response;
    	    }
    @GetMapping("/skill/page")
    public ResponseEntity<Map<String, Object>> getAllSkills(@RequestParam(required = false)String search,
    	    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size,
    	    @RequestParam(defaultValue = "skillId,asc") String[] sort) {
    	    	ResponseEntity<Map<String, Object>> response = employeeServiceImpl.getAllSkills(search, page, size, sort);
    			return  response;
    	    }
    @GetMapping("/skillset/page")
    public ResponseEntity<Map<String, Object>> getAllSkillSets(@RequestParam(required = false)String search,
    	    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size,
    	    @RequestParam(defaultValue = "skillSetId,asc") String[] sort) {
    	    	ResponseEntity<Map<String, Object>> response = employeeServiceImpl.getAllSkillSets(search, page, size, sort);
    			return  response;
    	    }
    @PostMapping("/userLogin")
    public Login loginPage(@RequestBody Login login) throws Exception{
    	String userName=login.getUserName();
    	String password=login.getPassword();
    	Login log=null;
    	if(userName!=null&&password!=null) {
    		
    	log=employeeServiceImpl.findByUserNameAndPassword(userName,password);
    	}
    	if(log==null) {
    		throw new Exception("Bad credentials");
    	}
    	return log;
    	}

	
    @PostMapping("/saveRegistration")
    public Login saveRegistartion(@RequestBody Login login) {
    	
    	return loginRepository.save(login);
    }
    
}
