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

	// all words that have recited
	private Vector<Record> reciteRecords = new Vector<Record>();
	public static final int MASTER_MINUS_ONE = 0;
	public static final int MASTER_PLUS_ONE = 1;
	public static final int MASTER_NINE = 2;
	public static final int HARDSHIP_PLUS = 3;

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
		log.error(wordBookFilePath);
		PathUtil pathUtil = new PathUtil(wordBookFilePath);

		String recordFilePath = pathUtil.getDir() + pathUtil.getPureFileName()
				+ ".rec";
		log.error(recordFilePath);

		File dir = new File(recordFilePath);
		if (!dir.exists()) {
			log.error("文件不存在");
			initRecord(wordBookFilePath, recordFilePath);
		}

		recordFiler = new RecordFiler(recordFilePath);
		reciteRecords = recordFiler.getReciteRecords();

		log.error("haha" + reciteRecords.size());
		currentTime = System.currentTimeMillis() - 60 * 1000;

		wordFiler = new WordFiler();
		allWords = wordFiler.loadJsonFile(wordBookFilePath);

		totalWordNum = allWords.size();

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
					log.info("减一" + r.stage + "\t" + r.word);
					r.stage--;
					break;
				case MASTER_PLUS_ONE:
					log.info("加一" + r.stage + "\t" + r.word);
					r.stage++;
					r.hardship--;
					break;
				case MASTER_NINE:
					log.info("完全" + r.stage + "\t" + r.word);
					r.stage = 9;
					break;
				case HARDSHIP_PLUS:
					log.info("难度" + r.stage + "\t" + r.word);
					r.hardship++;
					break;
				default:
					break;
				}

				r.lastTime = System.currentTimeMillis();

				// Record record = new Record(
				// r.word,
				// r.startTime,
				// System.currentTimeMillis(),
				// stage
				// );
				//
				// updateSingleReciteRecord(record);
				// log.debug(record.toString());

				// Record record = reciteRecords.get(currentWordIndex);
				// reciteRecords.
				// log.debug(record.toString());
				// record.stage ++;
				// record.lastTime =System.currentTimeMillis();
				// updateSingleReciteRecord(record);
				break;
			}
		}

	}

	// public void unMasterWord(int stage) {
	//
	// for (Record r : reciteRecords) {
	// if (currentWord.getWord().equals(r.word)) {
	// log.debug(r.toString());
	//
	// r.stage--;
	// r.lastTime = System.currentTimeMillis();
	//
	// // Record record = new Record(
	// // r.word,
	// // r.startTime,
	// // System.currentTimeMillis(),
	// // r.stage - 1
	// // );
	// //
	// // updateSingleReciteRecord(record);
	// // log.debug(record.toString());
	//
	// break;
	// }
	// }
	// }

	// public void saveReciteRecord() {
	//
	// for (Record r : recordFiler.getReciteRecords()) {
	// if (currentWord.getWord().equals(r.word)) {
	// Record reciteRecord = new Record(
	// r.word,
	// r.startTime,
	// System.currentTimeMillis(),
	// r.stage
	// );
	// updateSingleReciteRecord(reciteRecord);
	// break;
	// }
	// }
	// }

	public Word getNextWord() {

		refreshRecord();
		log.debug("currentWordIndex is " + currentWordIndex);
		log.debug("ebbinghausRecords.size is " + ebbinghausRecords.size());

		if (currentWordIndex < ebbinghausRecords.size() - 1) {
			currentWordIndex++;
		}

		if (currentWordIndex == ebbinghausRecords.size() - 1) {
			return null;
		}

		Record record = ebbinghausRecords.get(currentWordIndex);

		log.debug("record.word is " + record.word);
		currentWord = getWordByName(record.word);
		if (currentWord == null) {
			log.error("currentWord is null ");
		}
		log.debug("Next word: " + currentWord.getWord());
		return currentWord;
	}

	public Word getPreviousWord() {
		refreshRecord();

		if (currentWordIndex > 0) {
			currentWordIndex--;
		}

		Record record = ebbinghausRecords.get(currentWordIndex);

		currentWord = getWordByName(record.word);
		log.debug("Previous word: " + currentWord.getWord());
		return currentWord;
	}

	public void refreshRecord() {

		if ((System.currentTimeMillis() - currentTime) > (1000 * 60)) {// minutes

			currentTime = System.currentTimeMillis();
			resetEbbinghaus();

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
		} else {
			reciteRecords.add(record);
		}

		return true;
	}

	public void saveAllRecords() throws FileNotFoundException, IOException {
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