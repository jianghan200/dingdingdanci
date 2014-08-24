package com.ding.dao;

/**
 * UserWord entity. @author MyEclipse Persistence Tools
 */

public class UserWord implements java.io.Serializable {

	// Fields

	private UserWordId id;
	private Long lastTime;
	private Integer stage;

	// Constructors

	/** default constructor */
	public UserWord() {
	}

	/** minimal constructor */
	public UserWord(UserWordId id) {
		this.id = id;
	}

	/** full constructor */
	public UserWord(UserWordId id, Long lastTime, Integer stage) {
		this.id = id;
		this.lastTime = lastTime;
		this.stage = stage;
	}

	// Property accessors

	public UserWordId getId() {
		return this.id;
	}

	public void setId(UserWordId id) {
		this.id = id;
	}

	public Long getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getStage() {
		return this.stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

}