package engine.rendering.fonts;

import java.util.LinkedHashMap;

import engine.rendering.buffers.Texture;

public class Font {

	private LinkedHashMap<Character, FontCharacter> characters;
	private Texture atlas;
	
	private int lineHeight;
	
	private int[] paddings;
	
	public Font(LinkedHashMap<Character, FontCharacter> characters, Texture atlas, int lineHeight, int[] paddings) {
		this.characters = characters;
		this.atlas = atlas;
		this.lineHeight = lineHeight;
		this.paddings = paddings;
	}

	public FontCharacter get(char ch) {
		return characters.get(ch);
	}
	
	public LinkedHashMap<Character, FontCharacter> getCharacters() {
		return characters;
	}

	public Texture getAtlas() {
		return atlas;
	}

	public int getLineHeight() {
		return lineHeight;
	}
	
	public int[] getPaddings() {
		return paddings;
	}
}
