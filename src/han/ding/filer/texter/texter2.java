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
public class Texter2 {

	private static Log log = LogFactory.getLog(Texter2.class);
	
	private String wordBookName;
	private String wordBookFilePath;
	private ArrayList<Word> allWords = new ArrayList<Word>();

	public static void main(String[] args) throws IOException {

		Texter2 tr = new Texter2();
		tr.loadWordBook(Config.RESOURCE_PATH+"word_book_raw_2/再要你命3000.txt");
		log.info("All worrs: "+tr.getAllWords().size());
		
		WordFiler wordFiler = new WordFiler();
		wordFiler.saveToJsonFile(tr.getAllWords(), Config.RESOURCE_PATH+"word_book_json/再要你命3000.json");

	}

	public Texter2() {

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

		Word word = null;
		String line;


		do {
			line = br.readLine();
			
			if (line != null && !line.equals("")) {			
					word = wordItem2Word(line);
					allWords.add(word);
			}

		} while (line != null);

		br.close();
		fr.close();


		return allWords;
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


}
