package com.ding.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyFont {

	public static Font getFont(String filePath, String fileName, int style, float size){
		Font font = null;
		try {
			File file = new File(filePath + '/' + fileName);
			FileInputStream fi;
			BufferedInputStream fb;
			
			fi = new FileInputStream(file);
			fb = new BufferedInputStream(fi);
			font = Font.createFont(Font.TRUETYPE_FONT, fb);
			font = font.deriveFont(style, size);
			
			
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return font;
	}
}