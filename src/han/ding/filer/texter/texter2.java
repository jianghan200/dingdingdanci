package han.ding.filer.texter;

import han.ding.Config;
import han.ding.filer.WordFiler;
import han.ding.pojo.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Save all the word
 * 
 * @author Han
 * 
 */
public class texter2 {

	private static Log log = LogFactory.getLog(texter2.class);

	public ArrayList<Word> allWords = new ArrayList<Word>();

	public static void main(String[] args) throws IOException {

		texter2 tr = new texter2();
		tr.loadWordBook(Config.RESOURCE_PATH+"word_book_raw_2/再要你命3000.txt");
		log.info("All worrs: "+tr.getAllWords().size());
		
		WordFiler wordFiler = new WordFiler();
		wordFiler.saveToJsonFile(tr.getAllWords(), Config.RESOURCE_PATH+"word_book_json/再要你命3000.json");

	}

	public texter2() {

	}

	public ArrayList<Word> getAllWords() {
		return allWords;
	}


	/**
	 * read word list form the wood book
	 * 
	 * @param wordBookPath
	 * @return
	 * @throws IOException
	 */
	public ArrayList<Word> loadWordBook(String wordBookPath) throws IOException {
		FileReader fr = new FileReader(wordBookPath);
		BufferedReader br = new BufferedReader(fr);

		ArrayList<Word> wordBook = new ArrayList<Word>();

		Word word = null;
		String line;

		// The first line is the name of the word book

		do {
			line = br.readLine();
			
			if (line != null && !line.equals("")) {			
					word = wordItem2Word(line);
					wordBook.add(word);
			}

		} while (line != null);

		br.close();
		fr.close();

		allWords = wordBook;
		return wordBook;
	}

	public Word wordItem2Word(String wordItem) {
		Word word = null;
		int x = wordItem.indexOf('\t');
		log.info(""+x);
		if(x>0){
			word = new Word();
			word.setWord(wordItem.substring(0, x));
			word.setWordMean(wordItem.substring(x));
			log.info(word.getWord());
		}
		
		return word;
	}

	/**
	 * 将原声的单词本文件转为格式化好的单词本
	 * 
	 * @throws IOException
	 */
	public static void convertAllWordBook() throws IOException {

		texter2 wordFiler = new texter2();

		String[] thesList = { "CET4", "CET6", "GRE", "TOFEL", "研究生入学考试词汇" };

		for (int i = 0; i < thesList.length; i++) {

			wordFiler.loadWordBook(Config.DIR_WORD_BOOK_RAW + thesList[i] + ".txt");
			ArrayList<Word> allWords = wordFiler.getAllWords();
			// wordFiler.saveToJsonFile(allWords,Config.jsonDir+thesList[i]+".json");
		}

	}

}
