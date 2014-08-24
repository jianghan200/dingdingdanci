package com.ding.test;

import com.ding.dao.WordDAO;
import com.ding.pojo.Word;

public class testWordDAO {
	public static void main(String[] args){
		WordDAO wordDAO = new WordDAO();
		Word word = new Word();
		word.setWord("Jaitch");
		
		wordDAO.save(word);
	}

}
