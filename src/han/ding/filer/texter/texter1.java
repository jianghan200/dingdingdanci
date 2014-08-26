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
public class texter1 {

	private static Log log = LogFactory.getLog(texter1.class);
	
	
	private String wordBookName;
	public ArrayList<Word> allWords = new ArrayList<Word>();


	public static void main(String[] args) throws IOException {

		texter1 tr = new texter1();
		tr.loadWordBook(Config.RESOURCE_PATH+"word_book_raw/GRE.txt");
		log.info("All worrs: "+tr.getAllWords().size());

	}

	public texter1() {

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

	/**
	 * 将原声的单词本文件转为格式化好的单词本
	 * 
	 * @throws IOException
	 */
	public static void convertAllWordBook() throws IOException {

		texter1 wordFiler = new texter1();

		String[] thesList = { "CET4", "CET6", "GRE", "TOFEL", "研究生入学考试词汇" };

		for (int i = 0; i < thesList.length; i++) {

			wordFiler.loadWordBook(Config.DIR_WORD_BOOK_RAW+ thesList[i] + ".txt");
			ArrayList<Word> allWords = wordFiler.getAllWords();
			// wordFiler.saveToJsonFile(allWords,Config.jsonDir+thesList[i]+".json");
		}

	}

}
