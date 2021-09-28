package com.tataelxsi.Repository;



import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.tataelxsi.entity.EmployeeSkills;


@Repository                                                                                                               
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkills, Integer> {

	public static final String DELETE_EMPLOYEE_SKILLS_BY_EMP_NO = "delete from employee_skills where emp_no=:empNo" ;
	@Transactional
	@Modifying
	@Query(value = DELETE_EMPLOYEE_SKILLS_BY_EMP_NO, nativeQuery = true)
	public void deleteEmployeeSkillsByEmpNo(@Param("empNo")Integer empNo);
	
	@Query("select c from EmployeeSkills c where CONCAT(c.employeeSkillId,'') like %?1%" +"OR  c.approveRating like %?1%" + "OR c.selfRating like %?1%"+"OR c.reportTimeStamp like %?1%"+ "OR CONCAT(c.employee.empNo,'') like %?1%" + "OR CONCAT(c.skill.skillId,'') like %?1%")       
	Page<EmployeeSkills> findByEmployeeSkillContaining(String search, Pageable pageable); 
}
