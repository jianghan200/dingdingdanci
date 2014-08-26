// ReciteStat.java
package han.ding.panel;

import han.ding.filer.RecordFiler;
import han.ding.pojo.Record;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class StatisticPanel extends JFrame {
	
	private static Log log = LogFactory.getLog(StatisticPanel.class);

	private static final long serialVersionUID = 7247900451593318205L;
	
	final JTable table;
	Vector<Record> recordsVector;
	
	public StatisticPanel(Vector<Record> recordsVector) {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.recordsVector = recordsVector;
		
//		try {
//			recordsVector = (new RecordFiler(recordPath)).getReciteRecords();
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(this, "StatisticPanel"+e.getMessage());
//			System.exit(-1);
//		}

		Object[][] rowData = loadReciteRecords();
		String[] names = {"单词", "首次记忆时间", "上次记忆时间", "记忆阶段","单词难度"}; 
		
		table = new JTable(rowData, names) {
			// 将词汇统计表格控件设为只读
			public boolean isCellEditable(int row, int column) {
				return false;  
			}
		};
		
		table.setAutoCreateRowSorter(true);	// 表头排序

		mainPanel.add(new JScrollPane(table));
		
		this.add(mainPanel);
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
		log.info("Table show "+ recordsVector.size()+" records");
		Object[][] obj = new Object[recordsVector.size()][5];
		int j = 0;
		String str;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		for (Record i : recordsVector) {
			obj[j][0] = i.word;
			str = format.format(new Date(i.startTime));
			obj[j][1] = str;
			str = format.format(new Date(i.lastTime));
			obj[j][2] = str;
			obj[j][3] = i.stage;
			obj[j][4] = i.hardship;
			++j;
		}
		return obj;
	}

}