package han.ding.pojo;

/**
 * AbstractWord entity provides the base persistence definition of the DWord
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractWord implements java.io.Serializable {

	// Fields

	private Integer wordId;
	private String word;
	private String wordSenEn;
	private String wordSenCn;
	private String wordPron;
	private String wordMean;
	private Integer wordType;
	private Integer wordBook;

	// Constructors

	/** default constructor */
	public AbstractWord() {
	}

	/** minimal constructor */
	public AbstractWord(String word) {
		this.word = word;
	}

	/** full constructor */
	public AbstractWord(String word, String wordSenEn, String wordSenCn,
			String wordPron, String wordMean, Integer wordType, Integer wordBook) {
		this.word = word;
		this.wordSenEn = wordSenEn;
		this.wordSenCn = wordSenCn;
		this.wordPron = wordPron;
		this.wordMean = wordMean;
		this.wordType = wordType;
		this.wordBook = wordBook;
	}

	// Property accessors

	public Integer getWordId() {
		return this.wordId;
	}

	public void setWordId(Integer wordId) {
		this.wordId = wordId;
	}

	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getWordSenEn() {
		return this.wordSenEn;
	}

	public void setWordSenEn(String wordSenEn) {
		this.wordSenEn = wordSenEn;
	}

	public String getWordSenCn() {
		return this.wordSenCn;
	}

	public void setWordSenCn(String wordSenCn) {
		this.wordSenCn = wordSenCn;
	}

	public String getWordPron() {
		return this.wordPron;
	}

	public void setWordPron(String wordPron) {
		this.wordPron = wordPron;
	}

	public String getWordMean() {
		return this.wordMean;
	}

	public void setWordMean(String wordMean) {
		this.wordMean = wordMean;
	}

	public Integer getWordType() {
		return this.wordType;
	}

	public void setWordType(Integer wordType) {
		this.wordType = wordType;
	}

	public Integer getWordBook() {
		return this.wordBook;
	}

	public void setWordBook(Integer wordBook) {
		this.wordBook = wordBook;
	}

}