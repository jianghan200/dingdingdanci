package com.ding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ding.filer.RecordFiler;
import com.ding.filer.WordFiler;
import com.ding.pojo.Record;
import com.ding.pojo.Word;
import com.ding.util.Ebbinghaus;


public class RecordManager {
	
	private static Log log = LogFactory.getLog(RecordManager.class);

	//all words that have recited
	private Vector<Record> reciteRecords = new Vector<Record>();
	
	//words need to review by ebbinghaus
	private Vector<Record> ebbinghausRecords = new Vector<Record>();

	private int currentWordIndex=0;
	private long currentTime;
	private Word currentWord;

	// read the records
	private RecordFiler recordFiler;
	
	//word filer, read the file
	private WordFiler wordFiler;
	
	private String wordBookName;
	private int totalWordNum;
	
	//contain all the words
	public ArrayList<Word> allWords = new ArrayList<Word>();
	public ArrayList<Word> newWords = new ArrayList<Word>();
	public LinkedList<Word> oldWords = new LinkedList<Word>();

	
	public RecordManager(String recordFilePath,String wordBookFilePath) throws IOException {
		
		recordFiler = new RecordFiler(recordFilePath);
		reciteRecords = recordFiler.getReciteRecords();
		log.debug(""+reciteRecords.size());
		currentTime = System.currentTimeMillis()- 60*1000;
		
		wordFiler = new WordFiler();
//		wordFiler.loadWordBook(Config.wordBookPath);
		allWords = wordFiler.loadJsonFile(Config.jsonDir+"day-1.json");
//		wordBookName = wordFiler.getWordBookName();
		totalWordNum = allWords.size();
	}
	

	//get name of the current word book
	public String getWordBookName(){
		return wordBookName;
	}

	public Word getCurrentWord() {
		return currentWord;
	}
	
	public Vector<Record> getRiciteRecords(){
		return reciteRecords;
	}
	
	public void masterWord() {
		
		for (Record r : reciteRecords) {
			if (currentWord.getWord().equals(r.word)) {
					log.debug(r.toString());
					
					Record record = new Record(
							r.word,
							r.startTime, 
							System.currentTimeMillis(),
							r.stage + 1
							);
					
					updateSingleReciteRecord(record);
					log.debug(record.toString());
				
				 break;
			}
		}
		
//		Record record = reciteRecords.get(currentWordIndex);
//		reciteRecords.
//		log.debug(record.toString());
//		record.stage ++;
//		record.lastTime =System.currentTimeMillis();
//		updateSingleReciteRecord(record);

	}
	
	public void masterWord(int stage) {
		
		for (Record r : reciteRecords) {
			if (currentWord.getWord().equals(r.word)) {
					log.debug(r.toString());
					
					Record record = new Record(
							r.word,
							r.startTime, 
							System.currentTimeMillis(),
							stage
							);
					
					updateSingleReciteRecord(record);
					log.debug(record.toString());
				
				 break;
			}
		}
			
	}
	
	public void unMasterWord(int stage) {
		
		for (Record r : reciteRecords) {
			if (currentWord.getWord().equals(r.word)) {
					log.debug(r.toString());
					
					Record record = new Record(
							r.word,
							r.startTime, 
							System.currentTimeMillis(),
							r.stage - 1
							);
					
					updateSingleReciteRecord(record);
					log.debug(record.toString());
				
				 break;
			}
		}
	}
	
	
	public void saveReciteRecord() {
		
			for (Record r : recordFiler.getReciteRecords()) {
				if (currentWord.getWord().equals(r.word)) {
					Record reciteRecord = new Record(
							r.word,
							r.startTime, 
							System.currentTimeMillis(),
							r.stage
							);
					 updateSingleReciteRecord(reciteRecord);
					 break;
				}
			}
	}
	
	
	
	public Word getNextWord() {
		
		refreshRecord();
		log.debug("currentWordIndex is "+currentWordIndex);
		log.debug("ebbinghausRecords.size is "+ebbinghausRecords.size());
		
		if(currentWordIndex<ebbinghausRecords.size()-1){
			currentWordIndex++;
		}
		
		if(currentWordIndex==ebbinghausRecords.size()-1){
			return null;
		}
		
		Record record = ebbinghausRecords.get(currentWordIndex);
		
		log.debug("record.word is " + record.word);
		currentWord = getWordByName(record.word);
		if(currentWord == null){
			log.error("currentWord is null ");
		}
		log.debug("Next word: "+currentWord.getWord());
		return currentWord;
	}
	
	public Word getPreviousWord() {
		refreshRecord();
		
		if(currentWordIndex>0){
			currentWordIndex--;
		}
		
		Record record = ebbinghausRecords.get(currentWordIndex);
		
		currentWord = getWordByName(record.word);
		log.debug("Previous word: "+currentWord.getWord());
		return currentWord;
	}
	
	public  void refreshRecord(){
		
		if((System.currentTimeMillis() - currentTime) > (1000 * 60)){//minutes
			currentTime =System.currentTimeMillis();
			Ebbinghaus ebbinghaus = new Ebbinghaus();
			
			ebbinghausRecords = new Vector<Record>();
			for (Record r : reciteRecords) {
				if (ebbinghaus.needRecite(r)&&ebbinghausRecords.size()<200) {
					ebbinghausRecords.add(r);
				}
			}
			log.debug("Size is "+ebbinghausRecords.size());
			
			currentWordIndex =0;
		}

	}
	
	// 保存单条记录
	public boolean updateSingleReciteRecord(Record record) {
		// 如果单词已存在于背诵记录中，则更新
		int index = -1;
		for (Record r : reciteRecords) {
			if (record.word.equals(r.word)) {
				index = reciteRecords.indexOf(r);
				break;
			}
		}
		if (index != -1) {
			reciteRecords.remove(index);
			reciteRecords.add(record);
		}else {
			reciteRecords.add(record);
		}
					
		return true;
	}
	
	public void saveAllRecords() throws FileNotFoundException, IOException{
		recordFiler.saveAllRecords(reciteRecords);
	}
	
	public Word getRandomRecord() throws FileNotFoundException, IOException {
		
		if (ebbinghausRecords.size() != 0) {
			Random random = new Random(System.currentTimeMillis());
			int index = random.nextInt(ebbinghausRecords.size());
			// 由单词名获取单词对象
			String name = ebbinghausRecords.get(index).word;
			
			Word word = getWordByName(name);
			return word;
		} else {
			return null;
		}
	}
	
	
	// 从词库中随机去取出单词
	public Word getNextRandomWord() {
		
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(totalWordNum);
		log.debug("Random int is "+ index);
		
		currentWord = allWords.get(index);
		return currentWord;
	}
	
	
	public Word getWordByName(String name) {
		for (Word word : allWords) {
			if(word.getWord().equals(name)){
				return word;
			}
		}
		return null;
	}
	
	public void  saveCurrentWord(String mean){
		log.debug("mean is "+mean);
		for (Word word : allWords) {
			if(word.getWord().equals(currentWord.getWord())){
				word.setWordMean(mean);
				
				break;
			}
		}
	}
	
	public void saveAllWords(){
		wordFiler.saveToJsonFile(allWords,Config.jsonDir+"allword.json");
	}


}