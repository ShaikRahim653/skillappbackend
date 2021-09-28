package com.tataelxsi.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.tataelxsi.Repository.DesiredSkillLevelRepository;
import com.tataelxsi.Repository.EmployeeGradeRepository;
import com.tataelxsi.Repository.EmployeeLevelRepository;
import com.tataelxsi.Repository.EmployeeRepository;
import com.tataelxsi.Repository.EmployeeSkillRepository;
import com.tataelxsi.Repository.GradeRepository;
import com.tataelxsi.Repository.LevelRepository;
import com.tataelxsi.Repository.LoginRepository;
import com.tataelxsi.Repository.SkillRepository;
import com.tataelxsi.Repository.SkillSetRepository;
import com.tataelxsi.Repository.SupervisorRepository;
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
import com.tataelxsi.exception.ResourceNotFoundException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeGradeRepository employeeGradeRepository;
	@Autowired
	GradeRepository gradeRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	SupervisorRepository supervisorRepository;
	@Autowired
	private SkillRepository skillRepository;
	@Autowired
	private LevelRepository levelRepository;
	@Autowired
	private SkillSetRepository skillSetRepository;
	@Autowired
	private DesiredSkillLevelRepository desiredSkillLevelRepository;
	@Autowired
	private EmployeeSkillRepository employeeSkillRepository;
	@Autowired
	private EmployeeLevelRepository employeeLevelRepository;
	
	@Autowired
	private LoginRepository loginRepository;

	@Override
	public ResponseEntity<Object> uploadExcel(MultipartFile files) {

		// TODO Auto-generated method stub

		try {
			long t1 = System.currentTimeMillis();
			XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());

			XSSFSheet worksheet = workbook.getSheetAt(0);
			Iterator<Row> rows = worksheet.iterator();

			Map<String, Integer> map = new HashMap<String, Integer>();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {

					short minCollx = currentRow.getFirstCellNum();
					short maxCollx = currentRow.getLastCellNum();
					for (short collx = minCollx; collx < maxCollx; collx++) {
						org.apache.poi.ss.usermodel.Cell cell = currentRow.getCell(collx);
						map.put(cell.getStringCellValue().trim(), cell.getColumnIndex());
					}
					rowNumber++;

				} else {

					Employee emp = new Employee();
					Grade grade = new Grade();

					int empNo = (int) currentRow.getCell(map.get("EmpNo")).getNumericCellValue();
					boolean empExists = employeeRepository.existsById(empNo);
					if (!empExists) {
						emp.setEmpNo(empNo);
						emp.setEmployeeName(currentRow.getCell(map.get("Employee Name")).getStringCellValue());
						emp.setEmailId(currentRow.getCell(map.get("Email Id")).getStringCellValue());
						employeeRepository.save(emp);

						String gradeLevel = currentRow.getCell(map.get("Grade")).getStringCellValue().trim();
						grade = gradeRepository.findGradeByGradeLevel(gradeLevel);
						if (grade != null) {
							emp = employeeRepository.findEmployeeByEmpNo(empNo);
							saveEmpGrade(map, currentRow, emp, grade);
						} else {// for handling if there is any new grade in the excel to store it in DB.
							grade = new Grade();
							grade.setGradeLevel(gradeLevel);
							this.gradeRepository.save(grade);
						}

						saveSupervisorDetails(map, currentRow, emp);
						parseEmpSkills(map, currentRow, emp);
					}

					else {
						employeeGradeRepository.deleteEmployeeGradeByEmpNo(empNo);
						supervisorRepository.deleteSupervisorByEmpNo(empNo);
						employeeSkillRepository.deleteEmployeeSkillsByEmpNo(empNo);

						emp.setEmpNo((int) currentRow.getCell(map.get("EmpNo")).getNumericCellValue());
						emp.setEmployeeName(currentRow.getCell(map.get("Employee Name")).getStringCellValue());
						emp.setEmailId(currentRow.getCell(map.get("Email Id")).getStringCellValue());
						employeeRepository.saveAndFlush(emp);

						String gradeLevel = currentRow.getCell(map.get("Grade")).getStringCellValue().trim();
						grade = gradeRepository.findGradeByGradeLevel(gradeLevel);
						if (grade != null) {
							emp = employeeRepository.findEmployeeByEmpNo(empNo);
							saveEmpGrade(map, currentRow, emp, grade);
						} else {// for handling if there is any new grade in the excel to store it in DB.
							grade = new Grade();
							grade.setGradeLevel(gradeLevel);
							this.gradeRepository.save(grade);
						}
//						EmployeeSkills empSkill=new EmployeeSkills();
//						employeeRepository.deleteEmployeeByEmpNo(empNo);

						saveSupervisorDetails(map, currentRow, emp);
						parseEmpSkills(map, currentRow, emp);
					}

				}

			}
			long t2 = System.currentTimeMillis();
			System.out.println("Total Time Taken for Excel Parsing " + ((t2 - t1) / 1000));
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}

	/**
	 * @param map
	 * @param currentRow
	 * @param emp
	 */
	private void parseEmpSkills(Map<String, Integer> map, Row currentRow, Employee emp) {
		// EmployeeSkills empSkills = new EmployeeSkills();
		Skill skill;
		String skillName = "";

		if (map.get("Javascript_S_Value") != null) {
			int javascriptSelfRatingValue = (int) currentRow.getCell(map.get("Javascript_S_Value"))
					.getNumericCellValue();
			if (map.get("Javascript_A_Value") != null) {
				int javaScriptApproveRatingValue = (int) currentRow.getCell(map.get("Javascript_A_Value"))
						.getNumericCellValue();
				if (javascriptSelfRatingValue > 0 && javaScriptApproveRatingValue >= 0) {
					skillName = "Javascript";
					saveEmpSkills(emp, javascriptSelfRatingValue, javaScriptApproveRatingValue, skillName);
				}

			}
		}

		if (map.get("Python_S_Value") != null) {
			int pythonSelfRatingValue = (int) currentRow.getCell(map.get("Python_S_Value")).getNumericCellValue();
			if (map.get("Python_A_Value") != null) {
				int pythonApproveRatingValue = (int) currentRow.getCell(map.get("Python_A_Value"))
						.getNumericCellValue();
				if (pythonSelfRatingValue > 0 && pythonApproveRatingValue >= 0) {
					skillName = "Python";
					saveEmpSkills(emp, pythonSelfRatingValue, pythonApproveRatingValue, skillName);
				}
			}

		}

		if (map.get("PHP_S_Value") != null) {
			int phpSelfRatingValue = (int) currentRow.getCell(map.get("PHP_S_Value")).getNumericCellValue();
			if (map.get("PHP_A_Value") != null) {
				int phpApproveRatingValue = (int) currentRow.getCell(map.get("PHP_A_Value")).getNumericCellValue();
				if (phpSelfRatingValue > 0 && phpApproveRatingValue >= 0) {
					skillName = "PHP";
					saveEmpSkills(emp, phpSelfRatingValue, phpApproveRatingValue, skillName);
				}
			}

		}

		if (map.get("Java_S_Value") != null) {
			int javaSelfRatingValue = (int) currentRow.getCell(map.get("Java_S_Value")).getNumericCellValue();
			if (map.get("Java_A_Value") != null) {
				int javaApproveRatingValue = (int) currentRow.getCell(map.get("Java_A_Value")).getNumericCellValue();
				if (javaSelfRatingValue > 0 && javaApproveRatingValue >= 0) {
					skillName = "Java";
					saveEmpSkills(emp, javaSelfRatingValue, javaApproveRatingValue, skillName);
				}
			}

		}

		if (map.get("HTML/CSS_S_Value") != null) {
			int htmlCssSelfRatingValue = (int) currentRow.getCell(map.get("HTML/CSS_S_Value")).getNumericCellValue();
			if (map.get("HTML/CSS_A_Value") != null) {
				int htmlCssApproveRatingValue = (int) currentRow.getCell(map.get("HTML/CSS_A_Value"))
						.getNumericCellValue();
				if (htmlCssSelfRatingValue > 0 && htmlCssApproveRatingValue >= 0) {
					skillName = "HTML/CSS";
					saveEmpSkills(emp, htmlCssSelfRatingValue, htmlCssApproveRatingValue, skillName);
				}
			}
		}
		if (map.get("Media_S_Value") != null) {
			int mediaSelfRatingValue = (int) currentRow.getCell(map.get("Media_S_Value")).getNumericCellValue();
			if (map.get("Media_A_Value") != null) {
				int mediaApproveRatingValue = (int) currentRow.getCell(map.get("Media_A_Value")).getNumericCellValue();
				if (mediaSelfRatingValue > 0 && mediaApproveRatingValue >= 0) {
					skillName = "Media";
					saveEmpSkills(emp, mediaSelfRatingValue, mediaApproveRatingValue, skillName);
				}
			}

		}
		if (map.get("Broadcast Domain_S_Value") != null) {
			int broadcastDomainSelfRatingValue = (int) currentRow.getCell(map.get("Broadcast Domain_S_Value"))
					.getNumericCellValue();
			if (map.get("Broadcast Domain_A_Value") != null) {
				int broadcastDomainApproveRatingValue = (int) currentRow.getCell(map.get("Broadcast Domain_A_Value"))
						.getNumericCellValue();
				if (broadcastDomainSelfRatingValue > 0 && broadcastDomainApproveRatingValue >= 0) {
					skillName = "Broadcast Domain";
					saveEmpSkills(emp, broadcastDomainSelfRatingValue, broadcastDomainApproveRatingValue, skillName);
				}
			}
		}

		if (map.get("Embedded Networking_S_Value") != null) {
			int embeddedNetworkingSelfRatingValue = (int) currentRow.getCell(map.get("Embedded Networking_S_Value"))
					.getNumericCellValue();
			if (map.get("Embedded Networking_A_Value") != null) {
				int embeddedNetworkingApproveRatingValue = (int) currentRow
						.getCell(map.get("Embedded Networking_A_Value")).getNumericCellValue();
				if (embeddedNetworkingSelfRatingValue > 0 && embeddedNetworkingApproveRatingValue >= 0) {
					skillName = "Embedded Networking";
					saveEmpSkills(emp, embeddedNetworkingSelfRatingValue, embeddedNetworkingApproveRatingValue,
							skillName);

				}
			}
		}

		if (map.get("Backend Standards_S_Value") != null) {
			int backendStandardsSelfRatingValue = (int) currentRow.getCell(map.get("Backend Standards_S_Value"))
					.getNumericCellValue();
			if (map.get("Backend Standards_A_Value") != null) {
				int backendStandardsApproveRatingValue = (int) currentRow.getCell(map.get("Backend Standards_A_Value"))
						.getNumericCellValue();
				if (backendStandardsSelfRatingValue > 0 && backendStandardsApproveRatingValue >= 0) {
					skillName = "Backend Standards";
					saveEmpSkills(emp, backendStandardsSelfRatingValue, backendStandardsApproveRatingValue, skillName);
				}

			}

		}
		if (map.get("SQL/NoSQL_S_Value") != null) {
			int sqlNosqlSelfRatingValue = (int) currentRow.getCell(map.get("SQL/NoSQL_S_Value")).getNumericCellValue();
			if (map.get("SQL/NoSQL_A_Value") != null) {
				int sqlNosqlApproveRatingValue = (int) currentRow.getCell(map.get("SQL/NoSQL_A_Value"))
						.getNumericCellValue();
				if (sqlNosqlSelfRatingValue > 0 && sqlNosqlApproveRatingValue >= 0) {
					skillName = "SQL/NoSQL";
					saveEmpSkills(emp, sqlNosqlSelfRatingValue, sqlNosqlApproveRatingValue, skillName);
				}
			}
		}
	}

	/**
	 * @param emp
	 * @param empSkills
	 * @param javaSelfRatingValue
	 * @param javaApproveRatingValue
	 */
	private void saveEmpSkills(Employee emp, int javaSelfRatingValue, int javaApproveRatingValue, String skillName) {
		Skill skill;
		EmployeeSkills empSkill = new EmployeeSkills();
		try {
			empSkill.setSelfRating(javaSelfRatingValue);
			empSkill.setApproveRating(javaApproveRatingValue);
			skill = skillRepository.findSkillBySkillName(skillName);
			if (skill != null) {
				empSkill.setSkill(skill);
			}
			empSkill.setEmployee(emp);
			employeeSkillRepository.save(empSkill);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * @param map
	 * @param currentRow
	 * @param emp
	 */
	private void saveSupervisorDetails(Map<String, Integer> map, Row currentRow, Employee emp) {
		Supervisor sup = new Supervisor();
		try {
			sup.setSupervisorEmpNo((int) currentRow.getCell(map.get("Supervisor EmpNo")).getNumericCellValue());
			sup.setSupervisorEmpName(currentRow.getCell(map.get("Supervisor Name")).getStringCellValue());
			sup.setSupervisorEmpEmailId(currentRow.getCell(map.get("Supervisor EmailId")).getStringCellValue());
			sup.setEmployee(emp);
			supervisorRepository.save(sup);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * @param map
	 * @param currentRow
	 * @param emp
	 * @param grade
	 */
	private void saveEmpGrade(Map<String, Integer> map, Row currentRow, Employee emp, Grade grade) {
		try {
			EmployeeGrade empGrade = new EmployeeGrade();
			empGrade.setGrade(grade);
			empGrade.setWonNo((int) currentRow.getCell(map.get("WonNo")).getNumericCellValue());
			empGrade.setProjectName(currentRow.getCell(map.get("Project Name")).getStringCellValue());
			empGrade.setEmployee(emp);
			employeeGradeRepository.save(empGrade);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Grade createGrade(Grade gradeReq) {
		Grade grade = null;
		try {
			grade = gradeRepository.save(gradeReq);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return grade;
	}

	@Override
	public List<Grade> getAllGrade() {
		List<Grade> gradeList = null;
		try {
			gradeList = gradeRepository.findAll();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return gradeList;
	}

	public ResponseEntity<Grade> getGradeById(@PathVariable Integer gradeId) {
		Grade grade = null;
		try {
			grade = gradeRepository.findById(gradeId)
					.orElseThrow(() -> new ResourceNotFoundException("Grade not exist with id :" + gradeId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(grade);

	}

	public ResponseEntity<Grade> updateGradeById(Integer gradeId, Grade gradeDetails) {
		Grade grade = null;
		Grade updatedGrade = null;
		try {
			grade = gradeRepository.findById(gradeId)
					.orElseThrow(() -> new ResourceNotFoundException("Grade not exist with id :" + gradeId));

			grade.setGradeLevel(gradeDetails.getGradeLevel());
			grade.setGradeDesc(gradeDetails.getGradeDesc());
			updatedGrade = gradeRepository.save(grade);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(updatedGrade);
	}

	@Override
	public ResponseEntity<Map<String, Boolean>> deleteGradeById(Integer gradeId) {
		Grade grade = null;
		Map<String, Boolean> response = null;
		try {
			grade = gradeRepository.findById(gradeId)
					.orElseThrow(() -> new ResourceNotFoundException("Grade not exist with id :" + gradeId));

			gradeRepository.delete(grade);
			response = new HashMap<>();
			response.put("Deleted", Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	public Skill createSkill(Skill skill) {
		Skill skills = null;
		try {
			skills = skillRepository.save(skill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return skills;

	}

	public List<Skill> getAllSkills() {
		List<Skill> list = null;
		try {
			list = skillRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ResponseEntity<Skill> getSkillById(Integer skillId) {
		// TODO Auto-generated method stub
		Skill skill = null;
		try {
			skill = skillRepository.findById(skillId)
					.orElseThrow(() -> new ResourceNotFoundException("Skill not exist with id :" + skillId));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(skill);
	}

	public ResponseEntity<Skill> updateSkillById(Integer skillId, Skill skillDetails) {
		Skill skill = null;
		Skill updatedSkill = null;
		try {
			skill = skillRepository.findById(skillId)
					.orElseThrow(() -> new ResourceNotFoundException("Skill not exist with id :" + skillId));

			skill.setSkillName(skillDetails.getSkillName());

			updatedSkill = skillRepository.save(skill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(updatedSkill);
	}

	@Override
	public ResponseEntity<Map<String, Boolean>> deleteSkillById(Integer skillId) {
		Skill skill = null;
		Map<String, Boolean> response = null;
		try {
			skill = skillRepository.findById(skillId)
					.orElseThrow(() -> new ResourceNotFoundException("Skill not exist with id :" + skillId));

			skillRepository.delete(skill);
			response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	@Override
	public Level createLevel(Level levelReq) {
		Level level = null;
		try {
			level = levelRepository.save(levelReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return level;

	}

	@Override
	public List<Level> getAllLevel() {
		List<Level> list = null;
		try {
			list = levelRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ResponseEntity<Level> getLevelById(Integer levelId) {
		Level level = null;
		try {
			level = levelRepository.findById(levelId)
					.orElseThrow(() -> new ResourceNotFoundException("Level not exist with id :" + levelId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(level);
	}

	@Override
	public ResponseEntity<Map<String, Boolean>> deleteLevelById(Integer levelId) {
		Level level = null;
		Map<String, Boolean> response = null;
		try {
			level = levelRepository.findById(levelId)
					.orElseThrow(() -> new ResourceNotFoundException("Level not exist with id :" + levelId));

			levelRepository.delete(level);
			response = new HashMap<>();
			response.put("Deleted", Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<Level> updateLevelById(Integer levelId, Level levelDetails) {
		Level level = null;
		Level updatedLevel = null;
		try {
			level = levelRepository.findById(levelId)
					.orElseThrow(() -> new ResourceNotFoundException("Level not exist with id :" + levelId));

			level.setLevelId(levelDetails.getLevelId());
			updatedLevel = levelRepository.save(level);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(updatedLevel);
	}

	@Override
	public SkillSet createSkillSet(SkillSet skill) {
		SkillSet skillSet = null;
		try {
			skillSet = skillSetRepository.save(skill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return skillSet;
	}

	@Override
	public ResponseEntity<SkillSet> getSkillSetById(Integer skillSetId) {
		SkillSet skillSet = null;
		try {
			skillSet = skillSetRepository.findById(skillSetId)
					.orElseThrow(() -> new ResourceNotFoundException("Skillset not exist with id :" + skillSetId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(skillSet);
	}

	@Override
	public List<SkillSet> getAllSkillSet() {
		List<SkillSet> list = null;
		try {
			list = skillSetRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ResponseEntity<Map<String, Boolean>> deleteSkillSetById(Integer skillSetId) {
		SkillSet skillSet = null;
		Map<String, Boolean> response = null;
		try {
			skillSet = skillSetRepository.findById(skillSetId)
					.orElseThrow(() -> new ResourceNotFoundException("Skillset not exist with id :" + skillSetId));

			skillSetRepository.delete(skillSet);
			response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<SkillSet> updateSkillSetById(Integer skillSetId, SkillSet skillSetDetails) {
		SkillSet skillSet = null;
		SkillSet updatedSkillSet = null;
		try {
			skillSet = skillSetRepository.findById(skillSetId)
					.orElseThrow(() -> new ResourceNotFoundException("Skillset not exist with id :" + skillSetId));

			skillSet.setSkillSetName(skillSetDetails.getSkillSetName());

			updatedSkillSet = skillSetRepository.save(skillSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(updatedSkillSet);
	}

	public EmployeeLevel createEmployeeLevel(EmployeeLevel employeeLevel) {
		EmployeeLevel employeeLevels = null;
		try {
			employeeLevels = employeeLevelRepository.save(employeeLevel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeLevels;

	}

	public DesiredSkillLevel createDesiredSkillLevell(DesiredSkillLevel desiredSkillLevel) {
		Skill skill = new Skill();
		Grade grade = new Grade();
		Level level = new Level();

		DesiredSkillLevel desiredSkillLevels = new DesiredSkillLevel();
		try {

			desiredSkillLevels.setGrade(grade);
			desiredSkillLevels.setSkill(skill);
			desiredSkillLevels.setLevel(level);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desiredSkillLevelRepository.save(desiredSkillLevels);

	}

	public EmployeeSkills createEmployeeSkills(EmployeeSkills employeeSkills) {
		Skill skill = new Skill();
		Employee employee = new Employee();
		EmployeeSkills employeeSkill = new EmployeeSkills();
		try {
			employeeSkill.setEmployee(employee);
			employeeSkill.setSkill(skill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeSkillRepository.save(employeeSkill);

	}



	





	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> list = null;
		try {
			list = employeeRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Supervisor> getAllSupervisor() {
		List<Supervisor> list = null;
		try {
			list = supervisorRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<EmployeeGrade> getAllEmployeeGrade() {
		List<EmployeeGrade> list = null;
		try {
			list = employeeGradeRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<EmployeeSkills> getAllEmployeeSkills() {
		List<EmployeeSkills> list = null;
		try {
			list = employeeSkillRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DesiredSkillLevel> getAllDesiredSkillLevel() {
		List<DesiredSkillLevel> list = null;
		try {
			list = desiredSkillLevelRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}










	@Override
	public Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
	}
	@Override
	 public ResponseEntity<Map<String, Object>> getAllEmloyees(String search,
			    int page,int size,
			     String[] sort) {
	 List<Order> orders = new ArrayList<Order>();
     
     
     if (sort[0].contains(",")) {
         // will sort more than 2 fields
         // sortOrder="field, direction"
         for (String sortOrder : sort) {
         String[] sortA = sortOrder.split(",");
         orders.add(new Order(getSortDirection(sortA[1]), sortA[0]));
         }
         } else {
         // sort=[field, direction]
         orders.add(new Order(getSortDirection(sort[1]), sort[0]));
         }
     
     Pageable paging = PageRequest.of(page, size, Sort.by(orders));

     Page<Employee> pageTuts;
     if ( search == null)
     pageTuts = employeeRepository.findAll(paging);
     else
     pageTuts = employeeRepository.findByEmployeeContaining(search , paging);
     List<Employee> employees = pageTuts.getContent();
     Map<String, Object> response = new HashMap<>();
     response.put("employee", employees);
     response.put("currentPage", pageTuts.getNumber());
     response.put("totalItems", pageTuts.getTotalElements());
     response.put("totalPages", pageTuts.getTotalPages());
     
     return new ResponseEntity<>(response, HttpStatus.OK);
	 }

	@Override
	public ResponseEntity<Map<String, Object>> getAllSupervisors(String search, int page, int size, String[] sort) {
		List<Order> orders = new ArrayList<Order>();
	     
	     
	     if (sort[0].contains(",")) {
	         // will sort more than 2 fields
	         // sortOrder="field, direction"
	         for (String sortOrder : sort) {
	         String[] sortA = sortOrder.split(",");
	         orders.add(new Order(getSortDirection(sortA[1]), sortA[0]));
	         }
	         } else {
	         // sort=[field, direction]
	         orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	         }
	     
	     Pageable paging = PageRequest.of(page, size, Sort.by(orders));

	     Page<Supervisor> pageTuts;
	     if ( search == null)
	     pageTuts = supervisorRepository.findAll(paging);
	     else
	     pageTuts = supervisorRepository.findBySupervisorContaining(search , paging);
	     List<Supervisor> supervisors = pageTuts.getContent();
	     Map<String, Object> response = new HashMap<>();
	     response.put("supervisor", supervisors);
	     response.put("currentPage", pageTuts.getNumber());
	     response.put("totalItems", pageTuts.getTotalElements());
	     response.put("totalPages", pageTuts.getTotalPages());
	     
	     return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllDesiredSkills(String search, int page, int size, String[] sort) {
		List<Order> orders = new ArrayList<Order>();
	     
	     
	     if (sort[0].contains(",")) {
	         // will sort more than 2 fields
	         // sortOrder="field, direction"
	         for (String sortOrder : sort) {
	         String[] sortA = sortOrder.split(",");
	         orders.add(new Order(getSortDirection(sortA[1]), sortA[0]));
	         }
	         } else {
	         // sort=[field, direction]
	         orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	         }
	     
	     Pageable paging = PageRequest.of(page, size, Sort.by(orders));

	     Page<DesiredSkillLevel> pageTuts;
	     if ( search == null)
	     pageTuts = desiredSkillLevelRepository.findAll(paging);
	     else
	     pageTuts = desiredSkillLevelRepository.findByDesiredSkillLevelContaining(search , paging);
	     List<DesiredSkillLevel> desiredSkillLevels = pageTuts.getContent();
	     Map<String, Object> response = new HashMap<>();
	     response.put("desiredSkillLevel", desiredSkillLevels);
	     response.put("currentPage", pageTuts.getNumber());
	     response.put("totalItems", pageTuts.getTotalElements());
	     response.put("totalPages", pageTuts.getTotalPages());
	     
	     return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllEmloyeeGrades(String search, int page, int size, String[] sort) {
		List<Order> orders = new ArrayList<Order>();
	     
	     
	     if (sort[0].contains(",")) {
	         // will sort more than 2 fields
	         // sortOrder="field, direction"
	         for (String sortOrder : sort) {
	         String[] sortA = sortOrder.split(",");
	         orders.add(new Order(getSortDirection(sortA[1]), sortA[0]));
	         }
	         } else {
	         // sort=[field, direction]
	         orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	         }
	     
	     Pageable paging = PageRequest.of(page, size, Sort.by(orders));

	     Page<EmployeeGrade> pageTuts;
	     if ( search == null)
	     pageTuts = employeeGradeRepository.findAll(paging);
	     else
	     pageTuts = employeeGradeRepository.findByEmployeeGradeContaining(search , paging);
	     List<EmployeeGrade> employeeGrades = pageTuts.getContent();
	     Map<String, Object> response = new HashMap<>();
	     response.put("employeeGrade", employeeGrades);
	     response.put("currentPage", pageTuts.getNumber());
	     response.put("totalItems", pageTuts.getTotalElements());
	     response.put("totalPages", pageTuts.getTotalPages());
	     
	     return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllEmloyeeLevels(String search, int page, int size, String[] sort) {
		List<Order> orders = new ArrayList<Order>();
	     
	     
	     if (sort[0].contains(",")) {
	         // will sort more than 2 fields
	         // sortOrder="field, direction"
	         for (String sortOrder : sort) {
	         String[] sortA = sortOrder.split(",");
	         orders.add(new Order(getSortDirection(sortA[1]), sortA[0]));
	         }
	         } else {
	         // sort=[field, direction]
	         orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	         }
	     
	     Pageable paging = PageRequest.of(page, size, Sort.by(orders));

	     Page<EmployeeLevel> pageTuts;
	     if ( search == null)
	     pageTuts = employeeLevelRepository.findAll(paging);
	     else
	     pageTuts = employeeLevelRepository.findByEmployeeLevelContaining(search , paging);
	     List<EmployeeLevel> employeeLevels = pageTuts.getContent();
	     Map<String, Object> response = new HashMap<>();
	     response.put("employeeLevel", employeeLevels);
	     response.put("currentPage", pageTuts.getNumber());
	     response.put("totalItems", pageTuts.getTotalElements());
	     response.put("totalPages", pageTuts.getTotalPages());
	     
	     return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllEmloyeeSkills(String search, int page, int size, String[] sort) {
		List<Order> orders = new ArrayList<Order>();
	     
	     
	     if (sort[0].contains(",")) {
	         // will sort more than 2 fields
	         // sortOrder="field, direction"
	         for (String sortOrder : sort) {
	         String[] sortA = sortOrder.split(",");
	         orders.add(new Order(getSortDirection(sortA[1]), sortA[0]));
	         }
	         } else {
	         // sort=[field, direction]
	         orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	         }
	     
	     Pageable paging = PageRequest.of(page, size, Sort.by(orders));

	     Page<EmployeeSkills> pageTuts;
	     if ( search == null)
	     pageTuts = employeeSkillRepository.findAll(paging);
	     else
	     pageTuts = employeeSkillRepository.findByEmployeeSkillContaining(search , paging);
	     List<EmployeeSkills> employeeSkill = pageTuts.getContent();
	     Map<String, Object> response = new HashMap<>();
	     response.put("employeeSkills", employeeSkill);
	     response.put("currentPage", pageTuts.getNumber());
	     response.put("totalItems", pageTuts.getTotalElements());
	     response.put("totalPages", pageTuts.getTotalPages());
	     
	     return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllGrades(String search, int page, int size, String[] sort) {
		List<Order> orders = new ArrayList<Order>();
	     
	     
	     if (sort[0].contains(",")) {
	         // will sort more than 2 fields
	         // sortOrder="field, direction"
	         for (String sortOrder : sort) {
	         String[] sortA = sortOrder.split(",");
	         orders.add(new Order(getSortDirection(sortA[1]), sortA[0]));
	         }
	         } else {
	         // sort=[field, direction]
	         orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	         }
	     
	     Pageable paging = PageRequest.of(page, size, Sort.by(orders));

	     Page<Grade> pageTuts;
	     if ( search == null)
	     pageTuts = gradeRepository.findAll(paging);
	     else
	     pageTuts = gradeRepository.findByGradeContaining(search , paging);
	     List<Grade> grades = pageTuts.getContent();
	     Map<String, Object> response = new HashMap<>();
	     response.put("grade", grades);
	     response.put("currentPage", pageTuts.getNumber());
	     response.put("totalItems", pageTuts.getTotalElements());
	     response.put("totalPages", pageTuts.getTotalPages());
	     
	     return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllLevels(String search, int page, int size, String[] sort) {
		List<Order> orders = new ArrayList<Order>();
	     
	     
	     if (sort[0].contains(",")) {
	         // will sort more than 2 fields
	         // sortOrder="field, direction"
	         for (String sortOrder : sort) {
	         String[] sortA = sortOrder.split(",");
	         orders.add(new Order(getSortDirection(sortA[1]), sortA[0]));
	         }
	         } else {
	         // sort=[field, direction]
	         orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	         }
	     
	     Pageable paging = PageRequest.of(page, size, Sort.by(orders));

	     Page<Level> pageTuts;
	     if ( search == null)
	     pageTuts = levelRepository.findAll(paging);
	     else
	     pageTuts = levelRepository.findByLevelContaining(search , paging);
	     List<Level> levels = pageTuts.getContent();
	     Map<String, Object> response = new HashMap<>();
	     response.put("level", levels);
	     response.put("currentPage", pageTuts.getNumber());
	     response.put("totalItems", pageTuts.getTotalElements());
	     response.put("totalPages", pageTuts.getTotalPages());
	     
	     return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllSkillSets(String search, int page, int size, String[] sort) {
	
			List<Order> orders = new ArrayList<Order>();
		     
		     
		     if (sort[0].contains(",")) {
		         // will sort more than 2 fields
		         // sortOrder="field, direction"
		         for (String sortOrder : sort) {
		         String[] sortA = sortOrder.split(",");
		         orders.add(new Order(getSortDirection(sortA[1]), sortA[0]));
		         }
		         } else {
		         // sort=[field, direction]
		         orders.add(new Order(getSortDirection(sort[1]), sort[0]));
		         }
		     
		     Pageable paging = PageRequest.of(page, size, Sort.by(orders));

		     Page<SkillSet> pageTuts;
		     if ( search == null)
		     pageTuts = skillSetRepository.findAll(paging);
		     else
		     pageTuts = skillSetRepository.findBySkillSetContaining(search , paging);
		     List<SkillSet> skillSets = pageTuts.getContent();
		     Map<String, Object> response = new HashMap<>();
		     response.put("skillSet", skillSets);
		     response.put("currentPage", pageTuts.getNumber());
		     response.put("totalItems", pageTuts.getTotalElements());
		     response.put("totalPages", pageTuts.getTotalPages());
		     
		     return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllSkills(String search, int page, int size, String[] sort) {
		List<Order> orders = new ArrayList<Order>();
	     
	     
	     if (sort[0].contains(",")) {
	         // will sort more than 2 fields
	         // sortOrder="field, direction"
	         for (String sortOrder : sort) {
	         String[] sortA = sortOrder.split(",");
	         orders.add(new Order(getSortDirection(sortA[1]), sortA[0]));
	         }
	         } else {
	         // sort=[field, direction]
	         orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	         }
	     
	     Pageable paging = PageRequest.of(page, size, Sort.by(orders));

	     Page<Skill> pageTuts;
	     if ( search == null)
	     pageTuts = skillRepository.findAll(paging);
	     else
	     pageTuts = skillRepository.findBySkillContaining(search , paging);
	     List<Skill> skill = pageTuts.getContent();
	     Map<String, Object> response = new HashMap<>();
	     response.put("skill", skill);
	     response.put("currentPage", pageTuts.getNumber());
	     response.put("totalItems", pageTuts.getTotalElements());
	     response.put("totalPages", pageTuts.getTotalPages());
	     
	     return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public Login findByUserNameAndPassword(String userName, String password) throws Exception {
		// TODO Auto-generated method stub
	return loginRepository.findByUserNameAndPassword(userName, password);
	}

	/*
	 * //@Override public Login findByEmpNoAndEmployeeName(String userName,String
	 * password) { // TODO Auto-generated method stub List<Login> list=new
	 * ArrayList<Login>(); list=loginRepository.findAll(); for(int
	 * i=0;i<list.size();i++) {
	 * 
	 * if(list.equals("")&&list.equals(password)) {
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * return ; }
	 */

	
	
     
     
      

}
