package han.ding.filer.texter;

import han.ding.Config;
import han.ding.pojo.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 转化文本文件到 ArrayList<Word>
 * 
 * @author Han
 * 
 */
public class Texter1 {

	private static Log log = LogFactory.getLog(Texter1.class);
	
	
	private String wordBookName;
	private String wordBookFilePath;
	private ArrayList<Word> allWords = new ArrayList<Word>();


	public static void main(String[] args) throws IOException {

		Texter1 tr = new Texter1(Config.RESOURCE_PATH+"word_book_raw/GRE.txt");
		
		log.info("All worrs: "+tr.getAllWords().size());

	}

	public Texter1(String wordBookFilePath) {
		try {
			loadWordBook(wordBookFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public ArrayList<Word> loadWordBook(String wordBookPath) throws IOException{
		FileReader fr = new FileReader(wordBookPath);
		BufferedReader br = new BufferedReader(fr);
		
		ArrayList<String> wordItem = new ArrayList<String>();
		
		Word word = null;
		String line;

		//The first line is the name of the word book
		wordBookName = br.readLine(); 
		
		do {
			line = br.readLine();

			if(line!=null&&line.equals("")){
				
				//word is initialized as null so that only the second time will execute these code
				// when there is word in wordItem
				if(word!=null){
					word = wordItem2Word(wordItem);
					allWords.add(word);
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
		
		return allWords;
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



}
