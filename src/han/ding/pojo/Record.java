package han.ding.pojo;

public class Record {
    
	public String word;
	public long startTime;
	public long lastTime;
	public int stage;
	public int hardship;//陌生度
	
	public Record() {
		
	}
	


	@Override
	public String toString() {
		return "Record [word=" + word + ", lastTime=" + lastTime + ", stage=" + stage + "]";
	}
	
	
}