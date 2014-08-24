// ReviewManager.java

package com.ding.filer;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ding.Config;
import com.ding.pojo.Record;
import com.ding.pojo.Word;

/**
 * save all the record
 * @author Han
 *
 */
public class RecordFiler {
	
	private static Log log = LogFactory.getLog(RecordFiler.class);
	
	private Vector<Record> reciteRecords = new Vector<Record>();
	private String recordPath;

	public static void main(String[] args) throws IOException{
		RecordFiler recordFiler = new RecordFiler(Config.recordDir +"day-1");
		
		WordFiler wordFiler = new WordFiler();
//		wordFiler.loadWordBook(Config.wordBookPath);
		wordFiler.loadJsonFile(Config.jsonDir+"day-1.json");
		
		log.debug(""+wordFiler.getAllWords().size());
		for (Word word : wordFiler.getAllWords()) {
			Record temp = new Record();
		
				temp.word = word.getWord();
				temp.startTime = System.currentTimeMillis();
				temp.lastTime = System.currentTimeMillis();
				temp.stage = 0;
			
			recordFiler.reciteRecords.addElement(temp);
		}


		log.debug(""+wordFiler.getAllWords().size());
		recordFiler.saveAllRecords(recordFiler.reciteRecords);
	}
	
	public RecordFiler(String recordPath) throws IOException {
		
		this.recordPath = recordPath;
		loadReciteRecords();
	}
	
	/**
	 * save all recite records into file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveAllRecords(Vector<Record> records) throws FileNotFoundException, IOException {
		ObjectOutputStream outputStream = 
			new ObjectOutputStream(
					new FileOutputStream(recordPath));

		for (Record r : records) {
			outputStream.writeUTF(r.word);
			outputStream.writeLong(r.startTime);
			outputStream.writeLong(r.lastTime);
			outputStream.writeInt(r.stage);	
		}
		outputStream.close();
	}
	
	/**
	 * load all recite records into Vector<Record> reciteRecords 
	 * @throws IOException
	 */
	private void loadReciteRecords() throws IOException {
		try {
			ObjectInputStream inputStream = 
					new ObjectInputStream(
							new FileInputStream(recordPath));
			while (true) {
				Record temp = new Record();
				try {
					temp.word = inputStream.readUTF();
					temp.startTime = inputStream.readLong();
					temp.lastTime = inputStream.readLong();
					temp.stage = inputStream.readInt();
				}
				catch (EOFException e) {
					break;
				}
				this.reciteRecords.addElement(temp);
			}
			
			inputStream.close();
		}
		catch (FileNotFoundException e){
			return;
		}
	}
	
	public Vector<Record> getReciteRecords() {
		return reciteRecords;
	}

}
