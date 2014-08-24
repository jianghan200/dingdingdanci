package com.ding.pojo;

public class Record {
    
	public String word;
	public long startTime;
	public long lastTime;
	public int stage;
	
	public Record() {
		
	}
	
	public Record(
			String word, 
			long startTime, long lastTime, 
			int stage) {
		this.word = word;
		this.startTime = startTime;
		this.lastTime = lastTime;
		this.stage = stage;
	}

	@Override
	public String toString() {
		return "Record [word=" + word + ", lastTime=" + lastTime + ", stage=" + stage + "]";
	}
	
	
}