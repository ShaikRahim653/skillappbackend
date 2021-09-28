package com.tataelxsi.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tataelxsi.entity.EmployeeSkills;
import com.tataelxsi.entity.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer>  {
	@Query("select u from Grade u where u.gradeLevel = ?1")
	Grade findGradeByGradeLevel( String gradeLevel);
	
	/*public static final String FIND_GRADE_ID_BY_GRADE_LEVEL = "select grade_id from grade where grade_level=:gradeLevel" ;
	
	@Query(value = FIND_GRADE_ID_BY_GRADE_LEVEL, nativeQuery = true)
	public int findGradeIdByGradeLevel(@Param("gradeLevel")String gradeLevel);*/
	@Query("select c from Grade c where CONCAT(c.gradeId,'') like %?1%" +"OR  c.gradeLevel like %?1%"+"OR  c.gradeDesc like %?1%")     
	Page<Grade> findByGradeContaining(String search, Pageable pageable); 

}
