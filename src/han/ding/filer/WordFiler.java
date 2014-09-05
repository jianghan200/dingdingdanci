package han.ding.filer;

import han.ding.Config;
import han.ding.filer.texter.Texter1;
import han.ding.pojo.Word;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;


/**
 * Save all the word
 * @author Han
 *
 */
public class WordFiler {
	
	private static Log log = LogFactory.getLog(WordFiler.class);
	
	private String wordBookName;
	public ArrayList<Word> allWords = new ArrayList<Word>();
	
	public static void main(String[] args) throws IOException{
		
		/**
		//批量处理单词释义
		WordFiler wordFiler = new WordFiler();
		wordFiler.loadJsonFile(Config.DIR_WORD_BOOK_JSON+"再要你命3000.json");
		log.debug("All words: "+wordFiler.allWords.size());
		
		for (Word word : wordFiler.getAllWords()) {
			word.setWordMean(word.getWordMean().replace("\t", ""));
		}
		wordFiler.saveToJsonFile( wordFiler.getAllWords(), Config.DIR_WORD_BOOK_JSON+"再要你命3000.json");
		 */
		
//		convertAllWordBook();
		
		
		//split 
		WordFiler wordFiler = new WordFiler();
		wordFiler.loadJsonFile(Config.DIR_WORD_BOOK_JSON+"再要你命3000.json");
		wordFiler.splitIntoDaysWord(300);
		log.debug("All words: "+wordFiler.allWords.size());
		
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
			
			
			//将数据去重，加入
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

	public WordFiler(){

	}

	public ArrayList<Word> getAllWords() {
		return allWords;
	}

	// 返回词库名称
	public String getWordBookName() {
		return wordBookName;
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

	/**
	 * 将ArrayList中的单词存为
	 * @param allWords
	 * @param jsonFilePath
	 */
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
			saveToJsonFile(daysWords,Config.DIR_WORD_BOOK_JSON +"再要你命3000/day-"+day+".json");
			day++;
		}
		
	}
	
	
	/**
	 * 将原声的单词本文件转为格式化好的单词本
	 * 
	 * @throws IOException
	 */
	public static void convertAllWordBook() throws IOException {

		Texter1 texter;
		WordFiler wordFiler = new WordFiler();

		String[] thesList = { "CET4", "CET6", "GRE", "TOFEL", "研究生入学考试词汇" };

		for (int i = 0; i < thesList.length; i++) {
			
			texter = new Texter1(Config.DIR_WORD_BOOK_RAW+ thesList[i] + ".txt");
			ArrayList<Word> allWords = texter.getAllWords();
			wordFiler.saveToJsonFile(allWords,Config.DIR_WORD_BOOK_JSON+thesList[i]+".json");
		}

	}

}
