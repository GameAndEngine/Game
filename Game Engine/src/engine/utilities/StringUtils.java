package engine.utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtils {

	public static String readFileAsLines(String filePath) {
		String source = "";
		String line = "";
		InputStream is = StringUtils.class.getResourceAsStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try {
			while((line = reader.readLine()) != null) {
				source += line + "\n";
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return source;
	}
	
}
