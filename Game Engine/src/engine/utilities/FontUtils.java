package engine.utilities;

import java.util.LinkedHashMap;

import engine.rendering.buffers.Texture;
import engine.rendering.fonts.Font;
import engine.rendering.fonts.FontCharacter;

public class FontUtils {

	public static Font loadFont(String fntPath, String atlasPath) {
		Texture atlas = new Texture(atlasPath);
		String content = StringUtils.readFileAsLines(fntPath);
		LinkedHashMap<Character, FontCharacter> characters = new LinkedHashMap<Character, FontCharacter>();
		String[] lines = content.split("\n");
		
		int lineHeight = Integer.parseInt(lines[1].split(" ")[1].split("=")[1]);
		String[] strPaddings = lines[0].split(" ")[10].split("=")[1].split(",");
		int[] paddings = new int[] {Integer.parseInt(strPaddings[3]), Integer.parseInt(strPaddings[1]), 
				Integer.parseInt(strPaddings[0]), Integer.parseInt(strPaddings[2])};
		
		for(int i = 0; i < lines.length; i++) {
			String line = lines[i];
			
			if(line.startsWith("char id")) {
				char id = 0;
				int x = 0;
				int y = 0;
				int width = 0;
				int height = 0;
				int xOffset = 0;
				int yOffset = 0;
				int xAdvance = 0;
				String[] attributes = line.split("\\s+");
				for(int j = 1; j < attributes.length; j++) {
					String attributeStr = attributes[j];
					String[] attribute = attributeStr.split("=");
					
					if(attribute[0].equals("id"))
						id = (char) Integer.parseInt(attribute[1]);
					if(attribute[0].equals("x"))
						x = (int) Integer.parseInt(attribute[1]);
					if(attribute[0].equals("y"))
						y = (int) Integer.parseInt(attribute[1]);
					if(attribute[0].equals("width"))
						width = (int) Integer.parseInt(attribute[1]);
					if(attribute[0].equals("height"))
						height = (int) Integer.parseInt(attribute[1]);
					if(attribute[0].equals("xoffset"))
						xOffset = (int) Integer.parseInt(attribute[1]);
					if(attribute[0].equals("yoffset"))
						yOffset = (int) Integer.parseInt(attribute[1]);
					if(attribute[0].equals("xadvance"))
						xAdvance = (int) Integer.parseInt(attribute[1]);
				}
				
				characters.put((char) id, new FontCharacter(id, x, y, width, height, xOffset, yOffset, xAdvance));
			}
		}
		
		return new Font(characters, atlas, lineHeight, paddings);
	}
	
}
