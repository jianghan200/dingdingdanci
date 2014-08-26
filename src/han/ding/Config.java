package han.ding;

import java.awt.Color;

/**
 * Config file, contains general setting information such as dimension, file
 * path, setting variables
 * 
 * @Author Han
 */
public class Config {

	// General setting
//	public static boolean DEBUGMODE = true;
	
	// 768 is too large for my laptop
	public static final String PANEL_TITLE = "Dark Destiny";
	public static int PANEL_WIDTH = 1280;
	public static int PANEL_HEIGHT = 800;
	public static final boolean IS_FULL_SCREEN = false;
	
//	public static final boolean IS_FULL_SCREEN = true;


	
	/****** Path ******/
	// Resource path
	public static final String RESOURCE_PATH = "./res/";
		
	//phonetic font
	public static String fontPath = RESOURCE_PATH + "phonetic_font";	// 字体路径
	public static String phoneticFontName = "TOPhonetic.ttf";
	
		
	
	// 背诵记录文件路径
	public static String recordDir = RESOURCE_PATH+"record/";
	public static final String DIR_WORD_BOOK_RAW =  RESOURCE_PATH+"word_book/";
	public static final String DIR_WORD_BOOK_JSON =  RESOURCE_PATH+"word_book_json/";
	public static String jsonDir = RESOURCE_PATH+"json/";
	
	
	public static String PATH_WORD_BOOK =DIR_WORD_BOOK_JSON +"再要你命3000/day-1.json";	// 默认词库路径
	
	public static String[] wordBookList = {"CET4.txt", "CET6.txt", "GRE.txt", "TOFEL.txt", "研究生入学考试词汇.txt"};	

}