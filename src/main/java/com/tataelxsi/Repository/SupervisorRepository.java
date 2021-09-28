package com.tataelxsi.Repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.tataelxsi.entity.Supervisor;
@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor,Integer> {
	
public static final String DELETE_SUPERVISOR_BY_EMP_NO = "delete from supervisor where emp_no=:empNo" ;
	@Modifying
	@Transactional
	@Query(value = DELETE_SUPERVISOR_BY_EMP_NO, nativeQuery = true)
	public void deleteSupervisorByEmpNo(@Param("empNo")Integer empNo);

//	@Query("select u from Supervisor u where u.empNo = ?1")
//	Supervisor findSupervisorByEmpNo( int empNo);
	@Query("select c from Supervisor c where CONCAT(c.supervisorId,'') like %?1%" +"OR  c.supervisorEmpNo like %?1%" + "OR c.supervisorEmpName like %?1%"+ "OR c.supervisorEmpEmailId like %?1%"+ "OR CONCAT(c.employee.empNo,'') like %?1%"  )       
	Page<Supervisor> findBySupervisorContaining(String search, Pageable pageable);
}