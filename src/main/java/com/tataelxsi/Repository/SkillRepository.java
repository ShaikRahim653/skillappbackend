package com.tataelxsi.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tataelxsi.entity.Level;
import com.tataelxsi.entity.Skill;
@Repository
public interface SkillRepository extends JpaRepository<Skill,Integer>{


	@Query("select u from Skill u where u.skillName = ?1")
	Skill findSkillBySkillName(String skillName);
	@Query("select c from Skill c where CONCAT(c.skillId,'') like %?1%" +"OR  c.skillName like %?1%")   
	Page<Skill> findBySkillContaining(String search, Pageable pageable); 
}
