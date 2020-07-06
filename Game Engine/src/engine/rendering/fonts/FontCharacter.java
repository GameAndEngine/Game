package engine.rendering.fonts;

public class FontCharacter {

	private char id;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int xOffset;
	private int yOffset;
	private int xAdvance;
	
	public FontCharacter(char id, int x, int y, int width, int height, int xOffset, int yOffset, int xAdvance) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xAdvance = xAdvance;
	}

	public char getID() {
		return id;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public int getxAdvance() {
		return xAdvance;
	}
	
}
