package com.tataelxsi.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tataelxsi.entity.EmployeeGrade;
import com.tataelxsi.entity.EmployeeLevel;
@Repository
public interface EmployeeLevelRepository extends JpaRepository<EmployeeLevel,Integer> {
	@Query("select c from EmployeeLevel c where CONCAT(c.id,'') like %?1%" +  "OR CONCAT(c.level.levelId,'') like %?1%"  + "OR CONCAT(c.employee.empNo,'') like %?1%"  )      
     
	Page<EmployeeLevel> findByEmployeeLevelContaining(String search, Pageable pageable); 

}
