package com.tataelxsi.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tataelxsi.entity.DesiredSkillLevel;
import com.tataelxsi.entity.Employee;
@Repository
public interface DesiredSkillLevelRepository extends JpaRepository<DesiredSkillLevel, Integer>{
	@Query("select c from DesiredSkillLevel c where CONCAT(c.desiredSkillId,'') like %?1%" +"OR  c.desiredValue like %?1%" +  "OR CONCAT(c.skill.skillId,'') like %?1%" +  "OR CONCAT(c.level.levelId,'') like %?1%"  + "OR CONCAT(c.grade.gradeId,'') like %?1%"  )      
	Page<DesiredSkillLevel> findByDesiredSkillLevelContaining(String search, Pageable pageable); 

}
