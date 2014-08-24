package com.ding.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ding.Config;
import com.ding.RecordManager;
import com.ding.pojo.Word;
import com.ding.util.MyFont;
import com.ding.util.ScreenManager;


public class MainPanel extends JFrame implements KeyListener, ActionListener {
	private static final Logger log = LoggerFactory.getLogger(MainPanel.class);

	private static final long serialVersionUID = -3794989334961977463L;
	
	private boolean isInEditMode = false;
	/* 各种控件 */
	private JPanel mainPanel;
	// word 
	private JLabel lblEnglish;
	// word phonetic
	private JLabel lblPhonetic;
	// word meaning
	private JTextArea txtChinese;
	
	// word book name
	private JLabel lblWordBook;
	
	/* Menubar */
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("文件 (F)");
	private JMenuItem chooseThItem = new JMenuItem("选择词库 (T)");
	private JMenuItem statItem = new JMenuItem("词汇统计 (S)");
	private JMenuItem modeItem = new JMenuItem("复习 (R)");
	private JMenuItem exitItem = new JMenuItem("退出 (X)");
	private JMenu helpMenu = new JMenu("帮助 (H)");
	private JMenuItem aboutItem = new JMenuItem("关于 (A)");
	
	
	private RecordManager mainManager;


	public static void main(String[] args) {
		try {
			//UIManager.getSystemLookAndFeelClassName() 
			//返回实现默认的跨平台外观 -- Java Look and Feel (JLF) -- 的 LookAndFeel 类的名称。 
			//下面语句实现：将外观设置为系统外观. 
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		
		
		new MainPanel();
	}
	
	public MainPanel(){
		
		if(!Config.DEBUGMODE){
			System.out.close();
		}
		
		// Initialize frame
		if(Config.IS_FULL_SCREEN){
			
			// Switch to full screen 
			ScreenManager screen = new ScreenManager();
			DisplayMode displayMode = screen.getCurrentDisplayMode();
			screen.setFullScreen(this,displayMode);

		}else{
			
			this.setSize(1024, 700);// set the size
			this.setFocusable(true);
			this.setResizable(true);// forbid resize
			this.setLocationRelativeTo(null);// center
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setUndecorated(false);
		}
		
		
		initialContent();
		// 初始化菜单栏
		initialMenubar();
		
		this.setBackground(Color.white);
		this.setForeground(Color.white);
		this.setTitle(Config.PANEL_TITLE);

		// Set the frame to transparent
		((JComponent) this.getContentPane()).setOpaque(false);
		this.setVisible(true);
				
		try {
			mainManager = new RecordManager(Config.recordDir +"day-1",Config.jsonDir +"day-1.json");
		
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(-1);
		}
		lblWordBook.setText(mainManager.getWordBookName());
		showNextWord();
		
	}
	
	public void initialMenubar(){
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);	
		
		chooseThItem.setMnemonic('T');
		fileMenu.add(chooseThItem);
		
		statItem.setMnemonic('S');
		fileMenu.add(statItem);
		
		modeItem.setMnemonic('R');
		fileMenu.add(modeItem);
		
		exitItem.setMnemonic('X');
		fileMenu.add(exitItem);
		
		helpMenu.setMnemonic('H');
		menuBar.add(helpMenu);
		
		aboutItem.setMnemonic('A');
		helpMenu.add(aboutItem);
		
		chooseThItem.addActionListener(this);
		statItem.addActionListener(this);
		modeItem.addActionListener(this);
		exitItem.addActionListener(this);
		aboutItem.addActionListener(this);
		this.setJMenuBar(menuBar);
	}
	
	public void initialContent() {
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//word
		lblEnglish = new JLabel();
		
		lblEnglish.setText("English");
		lblEnglish.setFont(new Font("Arial", Font.BOLD, 80));
		
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets (150,0,0,0);
	
		mainPanel.add(lblEnglish, c);

		
		lblPhonetic = new JLabel();
		
		// 获取音标字体
		lblPhonetic.setFont(MyFont.getFont(
					Config.fontPath, Config.phoneticFontName, Font.PLAIN,	40));
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 2;
	
		mainPanel.add(lblPhonetic, c);

		
		//Chinese explanation
		txtChinese = new JTextArea();
		txtChinese.setLineWrap(true);
		txtChinese.setFont(new Font("华文仿宋", Font.PLAIN, 40));

//		txtChinese.setFocusable(true);
//		txtChinese.setEditable(true);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty =2;
		
		txtChinese.setFocusable(false);
		
		txtChinese.addFocusListener(new FocusListener(){
			  public void focusGained(FocusEvent e){
//			    Component src = (Component)e.getSource();//得到获得焦点的组件
//			    src.doSomething();
				  isInEditMode =true;
			  }
			  public void focusLost(FocusEvent e){
				  isInEditMode = false;
			  }
			});

		mainPanel.add(txtChinese, c);
		
		lblWordBook = new JLabel();		// 词库名称 wordManager.thesName
		lblWordBook.setFont(new Font("宋体", Font.PLAIN, 18));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.weighty = 0.2;
		mainPanel.add(lblWordBook, c);

		
		this.addKeyListener(this);
		lblEnglish.addKeyListener(this);
		lblPhonetic.addKeyListener(this);
		txtChinese.addKeyListener(this);

		add(mainPanel);
	}
	

	
	public void showNextWord(){
		
			Word nextWord = mainManager.getNextWord();
			if (nextWord == null){
				// 所有单词已经复习完o
				JOptionPane.showMessageDialog(this, "已经没有需要复习的单词了~");
			}else{
				showWord(mainManager.getCurrentWord());
			}

	}
	
	
	public void showPreviousWord(){

		mainManager.getPreviousWord();
		showWord(mainManager.getCurrentWord());
	}
	
	public void showRandomWord() {

		mainManager.getNextRandomWord();
		showWord(mainManager.getCurrentWord());
	}
	// 将单词显示在界面上
	public void showWord(Word word) {
		
		lblEnglish.setText(word.getWord());
		lblPhonetic.setText(word.getWordPron());
		txtChinese.setText("");
		// lblWordBook.setText(wordManager.thesName);
	}
	


	public void chooseThesaurus() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File(Config.wordBookPath));
		chooser.setDialogTitle("选择词库");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			Config.wordBookPath = chooser.getSelectedFile().getPath();
			// thesName = chooser.getSelectedFile().getName();
//			try {
//				mainManager.setThesaurus(Config.wordBookPath);
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(this,"Thesaurus "+ e.getMessage());
//				System.exit(-1);
//			}
			lblWordBook.setText(mainManager.getWordBookName());
			
//			mainManager.setReciteMode(mainManager.Modes.NEW);
//			modeItem.setText("复习 (R)");
//			modeItem.setMnemonic('R');
			showNextWord();
		} else {
			return;
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == chooseThItem) {
			chooseThesaurus();
		} 
		else if (event.getSource() == statItem) {
			new StatisticPanel(mainManager.getRiciteRecords());
		}
		else if (event.getSource() == modeItem) {
//			if (mainManager.getReciteMode() == mainManager.Modes.REVIEW) {
//				mainManager.setReciteMode(mainManager.Modes.NEW);
//				modeItem.setText("复习 (R)");
//				modeItem.setMnemonic('R');
//				showNextWord();
//			}
//			else if (mainManager.getReciteMode() == mainManager.Modes.NEW) {
//				mainManager.setReciteMode(mainManager.Modes.REVIEW);
//				modeItem.setText("学习 (N)");
//				modeItem.setMnemonic('N');
//				showNextWord();
//			}
		}
		else if (event.getSource() == exitItem){
			System.exit(0);
		}
		else if (event.getSource() == aboutItem) {
			JOptionPane.showMessageDialog(this, "版权所有 2014-2015  丁丁背单词");
		}
		
		if(event.getSource() == txtChinese){
			isInEditMode = true;
		}
	}

	
	@Override
	public void keyTyped(KeyEvent event) {
		char ch = event.getKeyChar();
		
		
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		
		
		if(!isInEditMode){
			if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
				try {
					mainManager.saveAllRecords();
					mainManager.saveAllWords();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.exit(0);
			}
			
			if (event.getKeyCode() == KeyEvent.VK_F1) {
				
			}
			if (event.getKeyCode() == KeyEvent.VK_F2) {
				
			}
			
			
			
			if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
				txtChinese.setFocusable(false);
				showNextWord();
			}
			if (event.getKeyCode() == KeyEvent.VK_LEFT) {
				txtChinese.setFocusable(false);
				showPreviousWord();
			}
			if (event.getKeyCode() == KeyEvent.VK_DOWN) {
				txtChinese.setFocusable(false);
				showRandomWord();
			}
			
			if (event.getKeyCode() == KeyEvent.VK_R) {
				//复习模式
//				mainManager.setReciteMode(mainManager.Modes.REVIEW);
//				modeItem.setText("学习 (N)");
//				modeItem.setMnemonic('N');
//				showNextWord();
			}
			
			if (event.getKeyCode() == KeyEvent.VK_S) {
				//学习模式
//				mainManager.setReciteMode(mainManager.Modes.NEW);
//				modeItem.setText("复习 (R)");
//				modeItem.setMnemonic('R');
//				showNextWord();
			}
			
			

			if (event.getKeyCode() == KeyEvent.VK_SPACE) {
				txtChinese.setText(mainManager.getCurrentWord().getWordMean());		
				txtChinese.setFocusable(true);
			}
			
			
			
			 
		}
		
		if(event.getKeyCode()==KeyEvent.VK_S && event.isControlDown()){
			
			log.debug("Save word!");
			mainManager.saveCurrentWord(txtChinese.getText());
//			lblEnglish.requestFocus();
			txtChinese.setFocusable(false);
			txtChinese.setFocusable(true);
			
//			txtChinese.
//			isInEditMode = false;
		 }
		
	}
	
	
	@Override
	public void keyReleased(KeyEvent event) {
		if(!isInEditMode){
			if (event.getKeyCode() == KeyEvent.VK_M) {
				log.debug("Word mastered");
				mainManager.masterWord();
				showNextWord();
			}
		}
		if (event.getKeyCode() == KeyEvent.VK_9) {
			log.debug("Word mastered");
			mainManager.masterWord(9);
		}
	}
}
