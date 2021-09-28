package com.tataelxsi.Repository;





import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tataelxsi.entity.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	
public static final String DELETE_EMPLOYEE_BY_EMP_NO = "delete from employee where emp_no=:empNo" ;
	
	@Query(value = DELETE_EMPLOYEE_BY_EMP_NO, nativeQuery = true)
	public void deleteEmployeeByEmpNo(@Param("empNo")Integer empNo);
	
	@Query("select u from Employee u where u.empNo = ?1")
	Employee findEmployeeByEmpNo( int empNo);

	@Query("select c from Employee c where CONCAT(c.empNo,'') like %?1%" +"OR  c.employeeName like %?1%" + "OR c.emailId like %?1%")    
	Page<Employee> findByEmployeeContaining(String search, Pageable pageable); 
	
	
	public Employee findByEmpNoAndEmployeeNameAndEmailId(Integer empNo,String employeeName,String emailId);

	public Employee findByEmpNo(Integer empNo);
	public Employee findByHealthId(Integer healthId);


}
