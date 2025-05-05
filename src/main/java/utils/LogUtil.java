package utils;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogUtil{
	
	public static Logger log=LogManager.getLogger(LogUtil.class);
	
	public static void info(String msg) {
		log.info(msg);
	}
	
	public static void warn(String msg) {
		log.warn(msg);
	}
	
	public static void error(String msg) {
		log.error(msg);
	}
	
	public static void fatal(String msg) {
		log.fatal(msg);
	}
}