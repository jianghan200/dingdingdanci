package com.ding.pojo;


/**
 * DWord entity. @author MyEclipse Persistence Tools
 */
public class Word extends AbstractWord implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Word() {
	}

	/** minimal constructor */
	public Word(String word) {
		super(word);
	}

	/** full constructor */
	public Word(String word, String wordSenEn, String wordSenCn,
			String wordPron, String wordMean, Integer wordType, Integer wordBook) {
		super(word, wordSenEn, wordSenCn, wordPron, wordMean, wordType,
				wordBook);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return getWord().hashCode();
	}
	
	@Override
	public boolean equals(Object arg0) {
		Word word = (Word)arg0;
		if (getWord().equals(word.getWord() )){
			return true;
		}
		return false;
	}

}
