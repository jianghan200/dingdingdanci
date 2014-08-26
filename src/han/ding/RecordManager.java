package han.ding;

import han.ding.filer.RecordFiler;
import han.ding.filer.WordFiler;
import han.ding.pojo.Record;
import han.ding.pojo.Word;
import han.ding.util.Ebbinghaus;
import han.ding.util.PathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RecordManager {

	private static Log log = LogFactory.getLog(RecordManager.class);

	public static final int MASTER_MINUS_ONE = 0;
	public static final int MASTER_PLUS_ONE = 1;
	public static final int MASTER_NINE = 2;
	public static final int HARDSHIP_PLUS = 3;

	// all words that have recited
	private Vector<Record> reciteRecords = new Vector<Record>();
	// words need to review by ebbinghaus
	public Vector<Record> ebbinghausRecords = new Vector<Record>();

	private int currentWordIndex = 0;
	private long currentTime;
	private Word currentWord;

	// read the records
	private RecordFiler recordFiler;

	// word filer, read the file
	private WordFiler wordFiler;

	private String wordBookName;
	private int totalWordNum;

	// contain all the words
	public ArrayList<Word> allWords = new ArrayList<Word>();
	public ArrayList<Word> newWords = new ArrayList<Word>();
	public LinkedList<Word> oldWords = new LinkedList<Word>();

	String wordBookFilePath;

	public RecordManager(String wordBookFilePath) throws IOException {

		this.wordBookFilePath = wordBookFilePath;
		
		PathUtil pathUtil = new PathUtil(wordBookFilePath);

		String recordFilePath = pathUtil.getDir() + pathUtil.getPureFileName() + ".rec";
		
		log.info(recordFilePath);

		File dir = new File(recordFilePath);
		if (!dir.exists()) {
			log.error("文件不存在");
			initRecord(wordBookFilePath, recordFilePath);
		}

		recordFiler = new RecordFiler(recordFilePath);
		reciteRecords = recordFiler.getReciteRecords();
		
		wordFiler = new WordFiler();
		allWords = wordFiler.loadJsonFile(wordBookFilePath);

		log.error("Num of records: " + reciteRecords.size());
		totalWordNum = allWords.size();
		currentTime = System.currentTimeMillis() - 60 * 1000;

	}

	// get name of the current word book
	public String getWordBookName() {
		return wordBookName;
	}

	public Word getCurrentWord() {
		return currentWord;
	}

	public Vector<Record> getRiciteRecords() {
		return reciteRecords;
	}

	public void masterWord(int stage) {

		for (Record r : reciteRecords) {
			if (currentWord.getWord().equals(r.word)) {
				log.debug(r.toString());

				switch (stage) {
				case MASTER_MINUS_ONE:
					log.info("减  " + r.stage + "\t" + r.word);
					if(r.stage>0){
						r.stage--;
					}
					break;
				case MASTER_PLUS_ONE:
					log.info("加  " + r.stage + "\t" + r.word);
					if(r.stage<9){
						r.stage++;
					}
					r.hardship--;
					break;
				case MASTER_NINE:
					log.info("完全" + r.stage + "\t" + r.word);
					r.stage = 9;
					break;
				case HARDSHIP_PLUS:
					log.info("难  " + r.stage + "\t" + r.word);
					r.hardship++;
					break;
				default:
					break;
				}
				// update access time
				r.lastTime = System.currentTimeMillis();
				// ignore remaining loop
				break;
			}
		}

	}

	public Word getNextWord() {

		refreshRecord();
		
		//ensure the index is in range
		if (currentWordIndex < ebbinghausRecords.size() - 1) {
			currentWordIndex++;
		}

		Record record = ebbinghausRecords.get(currentWordIndex);
		currentWord = getWordByName(record.word);
				
		return currentWord;
	}

	public Word getPreviousWord() {
		refreshRecord();

		//ensure the index is in range
		if (currentWordIndex > 0) {
			currentWordIndex--;
		}

		Record record = ebbinghausRecords.get(currentWordIndex);
		currentWord = getWordByName(record.word);
		
		return currentWord;
	}

	public void refreshRecord() {

		log.debug("currentWordIndex is " + currentWordIndex);
		log.debug("ebbinghausRecords.size is " + ebbinghausRecords.size());
		
		if ((System.currentTimeMillis() - currentTime) > (1000 * 60)) {// minutes
			

			currentTime = System.currentTimeMillis();
			resetEbbinghaus();

		}
		
		//auto save every 3 mins
		if ((System.currentTimeMillis() - currentTime) > (3000 * 60)){
			saveAllRecords();
			saveAllWords();
		}
			

	}

	public void resetEbbinghaus() {

		Ebbinghaus ebbinghaus = new Ebbinghaus();

		ebbinghausRecords = new Vector<Record>();
		for (Record r : reciteRecords) {
			if (ebbinghaus.needRecite(r) && ebbinghausRecords.size() < 200) {
				ebbinghausRecords.add(r);
			}
		}
		log.debug("Size is " + ebbinghausRecords.size());

		currentWordIndex = 0;
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
		log.debug("Random int is " + index);

		currentWord = allWords.get(index);
		return currentWord;
	}

	public Word getWordByName(String name) {
		for (Word word : allWords) {
			if (word.getWord().equals(name)) {
				return word;
			}
		}
		return null;
	}

	public void saveCurrentWord(String mean) {
		log.debug("mean is " + mean);
		for (Word word : allWords) {
			if (word.getWord().equals(currentWord.getWord())) {
				word.setWordMean(mean);

				break;
			}
		}
	}

	public void saveAllRecords() {
		recordFiler.saveAllRecords(reciteRecords);
	}

	public void saveAllWords() {
		wordFiler.saveToJsonFile(allWords, wordBookFilePath);
	}

	public void initRecord(String wordBookFilePath, String recordFilePath)
			throws IOException {

		RecordFiler recordFiler = new RecordFiler(recordFilePath);
		Vector<Record> records = new Vector<Record>();

		WordFiler wordFiler = new WordFiler();
		wordFiler.loadJsonFile(wordBookFilePath);

		for (Word word : wordFiler.getAllWords()) {
			Record temp = new Record();
			temp.word = word.getWord();
			temp.startTime = System.currentTimeMillis();
			temp.lastTime = System.currentTimeMillis();
			temp.stage = 0;
			temp.hardship = 0;

			records.addElement(temp);
		}

		log.debug("" + wordFiler.getAllWords().size());

		recordFiler.saveAllRecords(records);
	}

}