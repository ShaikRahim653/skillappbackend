package com.tataelxsi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tataelxsi.entity.SkillSetSkillList;
@Repository
public interface SkillSetSkillListRepository extends JpaRepository<SkillSetSkillList, Integer> {

}
