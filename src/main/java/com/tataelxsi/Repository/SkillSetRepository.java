package com.tataelxsi.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tataelxsi.entity.Skill;
import com.tataelxsi.entity.SkillSet;
@Repository
public interface SkillSetRepository extends JpaRepository<SkillSet,Integer>{
//	@Query(value="select ss from skillset ss where CONCAT(ss.skill_set_id,'') like %?%"+"OR  ss.skillSetName like %?1%")   
	@Query("select c from SkillSet c where CONCAT(c.skillSetId,'') like %?1%" +"OR  c.skillSetName like %?1%") 
	Page<SkillSet> findBySkillSetContaining(String search, Pageable pageable);
	

}
