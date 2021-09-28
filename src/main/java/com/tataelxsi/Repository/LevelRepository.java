package com.tataelxsi.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tataelxsi.entity.Grade;
import com.tataelxsi.entity.Level;

public interface LevelRepository extends JpaRepository<Level, Integer>{
	@Query("select c from Level c where CONCAT(c.levelId,'') like %?1%" +"OR  c.levelName like %?1%")      
	Page<Level> findByLevelContaining(String search, Pageable pageable); 

}
