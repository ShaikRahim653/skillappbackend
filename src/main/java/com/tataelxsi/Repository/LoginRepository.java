package com.tataelxsi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tataelxsi.entity.Login;
@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
	public Login findByUserNameAndPassword(String userName,String password);
	

}
