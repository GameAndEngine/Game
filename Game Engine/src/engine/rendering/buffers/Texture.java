package engine.rendering.buffers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import engine.framework.Engine;
import engine.framework.Logger;

public class Texture {
	
	private int id;
	private int width;
	private int height;
	
	public Texture(String filePath) {
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);
		
		ByteBuffer pixels = stbi_load(filePath, width, height, comp, 4);
		
		this.width = width.get(0);
		this.height = height.get(0);
		
		if(pixels == null) {
			Logger.errorLogLine("System cannot locate the following file path: " + filePath);
			Engine.exit(-1);
		}
		
		create(pixels, this.width, this.height);
	}
	
	public Texture(ByteBuffer pixels, int width, int height) {
		create(pixels, width, height);
	}
	
	public void create(ByteBuffer pixels, int width, int height) {
		id = glGenTextures();
		bind();
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		unbind();
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
