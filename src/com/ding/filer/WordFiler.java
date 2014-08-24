package com.ding.filer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ding.Config;
import com.ding.pojo.Word;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class WordFiler {
	
	private static final Logger log = LoggerFactory.getLogger(WordFiler.class);
	
	private String wordBookName;
	public ArrayList<Word> allWords = new ArrayList<Word>();
	
	public static void main(String[] args) throws IOException{
		
		convertAllWordBook();
		
		//split 
//		WordFiler wordFiler = new WordFiler();
//		wordFiler.loadWordBook(Config.wordBookPath);
//		wordFiler.splitIntoDaysWord(200);
//		log.debug("All words: "+wordFiler.allWords.size());
		
		/*
			//Save all the words into database
			WordFiler wordFiler = new WordFiler(Config.wordBookPath);
			wordFiler.saveToJsonFile(wordFiler.getAllWords(),Config.jsonDir+"allword.json");
			wordFiler.loadJsonFile();
			
			log.debug("All words: "+wordFiler.allWords.size());
		*/
			
			
			/*
			//****保存数据到json文件
			WordFiler wordFiler = new WordFiler(Config.wordBookPath);
			wordFiler.saveToJsonFile();
			*/
			
			
			
	//		WordFiler wordFiler = new WordFiler(Config.wordBookPath);
	//		WordDAO wordDAO = new WordDAO();
	//		
	//		ArrayList<Word> allWords = wordFiler.getAllWords();
	//		HashSet<Word> allWordsSet = new HashSet<Word>();
	//		for (Word word : allWords) {
	//			allWordsSet.add(word);
	//		}
	//		System.out.println("All words is "+allWords.size());
	//		System.out.println("All words in set is "+allWordsSet.size());
	//		
	//		wordDAO.saveAllWordIntoDb(allWordsSet);
		}

	public WordFiler() throws IOException {
			
//		allWords = loadWordBook(wordBookPath);
	}

	public ArrayList<Word> getAllWords() {
		return allWords;
	}

	// 返回词库名称
	public String getWordBookName() {
		return wordBookName;
	}

	/**
	 * read word list form the wood book
	 * 
	 * @param wordBookPath
	 * @return
	 * @throws IOException
	 */
	public ArrayList<Word> loadWordBook(String wordBookPath) throws IOException{
		FileReader fr = new FileReader(wordBookPath);
		BufferedReader br = new BufferedReader(fr);
		
		ArrayList<Word> wordBook = new ArrayList<Word>();
		ArrayList<String> wordItem = new ArrayList<String>();
		
		Word word = null;
		String line;

		//The first line is the name of the word book
		wordBookName = br.readLine(); 
		System.out.println(wordBookName);
		
		do {
			line = br.readLine();

			if(line!=null&&line.equals("")){
				
				//word is initialized as null so that only the second time will execute these code
				// when there is word in wordItem
				if(word!=null){
					word = wordItem2Word(wordItem);
					wordBook.add(word);
				}
				word = new Word();
				wordItem = new ArrayList<String>();
				
			}
			
			if(line!=null&&!line.equals("")){
				wordItem.add(line);				
			}
			
		} while (line!=null);
		
		br.close();
		fr.close();
		
		allWords = wordBook;
		return wordBook;
	}
	
	public Word wordItem2Word(ArrayList<String> wordItem) {
		 Word word = null;
		 switch(wordItem.size()) {
			case 2://only has word and meaning
				word = new Word();
				word.setWord(wordItem.get(0));
				word.setWordMean(wordItem.get(1));
				break;
			case 3://have word,phonetic and meaning
				word = new Word();
				word.setWord(wordItem.get(0));
				word.setWordPron(wordItem.get(1));
				word.setWordMean(wordItem.get(2));
				break;
			case 4://have word, phonetic and 2 meaning
				word = new Word();
				word.setWord(wordItem.get(0));
				word.setWordPron(wordItem.get(1));
				word.setWordMean(wordItem.get(2)+wordItem.get(3));
				break;
			default:
				break;
			}

		 return word;
	 }
	
	public ArrayList<Word> loadJsonFile(String jsonFilePath){
		Gson mGson = new Gson();
		 
		Type type =new TypeToken<ArrayList<Word>>(){}.getType();
		
		try {
			allWords =  mGson.fromJson(new FileReader(jsonFilePath), type);
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allWords;
	}

	public void saveToJsonFile(ArrayList<Word> allWords,String jsonFilePath){
		try {   
			  Gson mGson = new Gson();
              FileWriter fw  =new FileWriter(jsonFilePath);   
              BufferedWriter bw=new BufferedWriter(fw);   
              bw.write(mGson.toJson(allWords)); 
		      bw.close();  
		      fw.close();   
       } catch (IOException e) {   
            // TODO Auto-generated catch block   
           e.printStackTrace();   
       }   
		
	}
	
	
	/**
	 * Split the word book into days amount, save as day-#.json
	 * @param num  number of words per day
	 */
	public void splitIntoDaysWord(int num){
		
		Random random = new Random(System.currentTimeMillis());
		int index = 0;
		Word word = null;
		ArrayList<Word> daysWords = new ArrayList<Word>();
		int day = 1;
		
		while(allWords.size()!=0){
			daysWords=new ArrayList<Word>();
			for (int i = 0; i < num; i++) {
				if (allWords.size()!=0) {
					index = random.nextInt(allWords.size());
					word = allWords.get(index);
					daysWords.add(word);
					allWords.remove(index);
				}	
			}
			saveToJsonFile(daysWords,Config.jsonDir+"day-"+day+".json");
			day++;
		}
		
	}
	
	
	public static void convertAllWordBook() throws IOException{
		
		WordFiler wordFiler = new WordFiler();
		
		String[] thesList = {"CET4", "CET6", "GRE", "TOFEL", "研究生入学考试词汇"};
		
		for (int i = 0; i < thesList.length; i++) {
			
			wordFiler.loadWordBook(Config.wordBookDir + thesList[i]+".txt");
			ArrayList<Word> allWords = wordFiler.getAllWords();
			wordFiler.saveToJsonFile(allWords,Config.jsonDir+thesList[i]+".json");
		}
		
	}

}
