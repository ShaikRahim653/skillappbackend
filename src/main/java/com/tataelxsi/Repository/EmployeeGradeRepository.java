package com.tataelxsi.Repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tataelxsi.entity.DesiredSkillLevel;
import com.tataelxsi.entity.EmployeeGrade;
@Repository
public interface EmployeeGradeRepository extends JpaRepository<EmployeeGrade, Integer>{
	
public static final String DELETE_EMPLOYEE_GRADE_BY_EMP_NO = "delete from employee_grade where emp_no=:empNo" ;
	@Transactional
	@Modifying
	@Query(value = DELETE_EMPLOYEE_GRADE_BY_EMP_NO, nativeQuery = true)
	public void deleteEmployeeGradeByEmpNo(@Param("empNo")Integer empNo);
	
	@Query(value="select empNo from employee_grade u where u.empNo ",nativeQuery = true)
	public void findEmployeeEmpNo(@Param("empNo") Integer empNo);
	
	@Query("select c from EmployeeGrade c where CONCAT(c.employeeGradeId,'') like %?1%" +"OR  c.wonNo like %?1%" + "OR c.projectName like %?1%"+ "OR CONCAT(c.employee.empNo,'') like %?1%"  + "OR CONCAT(c.grade.gradeId,'') like %?1%"  )  
	Page<EmployeeGrade> findByEmployeeGradeContaining(String search, Pageable pageable); 



}
