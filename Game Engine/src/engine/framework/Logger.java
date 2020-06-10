package engine.framework;

public class Logger {

	public static void logLine(String message) {
		System.out.println(message);
	}
	
	public static void errorLogLine(String message) {
		System.err.println(message);
	}
	
	public static void log(String message) {
		System.out.print(message);
	}
	
	public static void errorLog(String message) {
		System.err.print(message);
	}
}
