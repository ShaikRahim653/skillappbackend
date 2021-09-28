package com.tataelxsi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
@Entity
@Table
public class Level {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int levelId;
	private String levelName;
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Level(int levelId, String levelName) {
		super();
		this.levelId = levelId;
		this.levelName = levelName;
	}
	public Level() {
		super();
	}
	
	

}
