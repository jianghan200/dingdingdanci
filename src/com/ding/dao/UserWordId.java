package com.ding.dao;

/**
 * UserWordId entity. @author MyEclipse Persistence Tools
 */

public class UserWordId implements java.io.Serializable {

	// Fields

	private Integer userId;
	private String word;

	// Constructors

	/** default constructor */
	public UserWordId() {
	}

	/** full constructor */
	public UserWordId(Integer userId, String word) {
		this.userId = userId;
		this.word = word;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserWordId))
			return false;
		UserWordId castOther = (UserWordId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getWord() == castOther.getWord()) || (this.getWord() != null
						&& castOther.getWord() != null && this.getWord()
						.equals(castOther.getWord())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getWord() == null ? 0 : this.getWord().hashCode());
		return result;
	}

}