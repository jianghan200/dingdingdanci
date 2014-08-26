package han.ding.util;

import han.ding.pojo.Record;

public class Ebbinghaus {
	
	//unit - minutes
	// 10 stages , 0-9
	private int forgettingCurve[] = {
			1,			// 60秒 
			5,			// 5分钟 
			30,			// 30分钟 
			12*60,		// 12小时 
			1*24*60,	// 1天
			2*24*60,	// 2天 
			4*24*60,	// 4天 
			7*24*60,	// 7天 
			15*24*60,	// 15天 
			30*24*60	// 30天 
			};
	
	// 根据记忆曲线，判断单词是否需要复习
	public boolean needRecite(Record record) {
		// 记忆曲线完成 
		if (record.stage >= forgettingCurve.length) {
			return false;
		}
		
		long currentTime = System.currentTimeMillis();
		int timeDiff = (int)((currentTime - record.lastTime) / (1000 * 60));//minutes
		
		if (timeDiff > forgettingCurve[record.stage]) {
			return true;
		}
		else {
			return false;
		}
		
	}
}