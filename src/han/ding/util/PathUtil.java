package han.ding.util;

import han.ding.Config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PathUtil {
	private static Log log = LogFactory.getLog(PathUtil.class);
	
	String path;
	int x;
	int y;
	
	public static void main(String[] args) {
		
		PathUtil pathUtil = new PathUtil(Config.PATH_WORD_BOOK );
		
		log.info(pathUtil.getFileName());
		log.info(pathUtil.getPureFileName());
		log.info(pathUtil.getDir());
		log.info(pathUtil.getRelativePath());
	}
	
	public PathUtil(String path) {
		this.path = path;
		this.x = path.lastIndexOf('/');
		this.y = path.lastIndexOf('.');
	}
	
	public String getFileName(){
		
		return path.substring(x+1);
	}
	
	public String getPureFileName(){

		return path.substring(x+1,y);
	}
	
	public String getDir() {
		return path.substring(0, x+1);
	}
	
	public String getRelativePath(){
		
		String rPath = path.replace(Config.DIR_WORD_BOOK_JSON, "");
		rPath = rPath.replace(getFileName(), "");
		return rPath;
		
	}
}

