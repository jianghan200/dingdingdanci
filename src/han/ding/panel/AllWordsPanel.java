package han.ding.panel;

import han.ding.pojo.Word;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 显示所有的单词
 * @author Han
 *
 */
public class AllWordsPanel extends JFrame {
	
	private static Log log = LogFactory.getLog(AllWordsPanel.class);

	private static final long serialVersionUID = 7247900451593318205L;
	
	final JTable table;
	
	public ArrayList<Word> allWords = new ArrayList<Word>();
	
	public AllWordsPanel( ArrayList<Word> allWords) {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.allWords = allWords;
		

		Object[][] rowData = loadReciteRecords();
		String[] names = {"单词", "释义", "发音"}; 
		
		table = new JTable(rowData, names) {
			// 将词汇统计表格控件设为只读
			public boolean isCellEditable(int row, int column) {
				return false;  
			}
		};
		
		table.setAutoCreateRowSorter(true);	// 表头排序

		mainPanel.add(new JScrollPane(table));
		
		this.add(mainPanel);
		this.setFont(new Font("Arial", Font.BOLD, 36));
		// 
		this.setTitle("背诵情况统计");
		this.setSize(600, 400);
		this.setResizable(false);

		// 使窗口居中
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width / 2; // 获取屏幕的宽
		int screenHeight = screenSize.height / 2; // 获取屏幕的高
		int height = this.getHeight();
		int width = this.getWidth();
		setLocation(screenWidth - width / 2, screenHeight - height / 2);

		this.setVisible(true);
	}
	
	private Object[][] loadReciteRecords() {
		log.info("Table show "+ allWords.size()+" records");
		Object[][] obj = new Object[allWords.size()][3];
		int j = 0;
		String str;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		for (Word i : allWords) {
			obj[j][0] = i.getWord();
			obj[j][1] = i.getWordMean();
			obj[j][2] = i.getWordPron();

			++j;
		}
		return obj;
	}
	
}
