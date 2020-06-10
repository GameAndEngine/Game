package engine.rendering.buffers;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class VertexBuffer {
	
	private int id;
	
	private static ArrayList<Integer> buffers = new ArrayList<Integer>();
	
	public VertexBuffer(float[] data, int usage) {
		id = glGenBuffers();
		bind();
		glBufferData(GL_ARRAY_BUFFER, data, usage);
		buffers.add(id);
	}
	
	public VertexBuffer(int size, int usage) {
		id = glGenBuffers();
		bind();
		glBufferData(GL_ARRAY_BUFFER, size, usage);
		buffers.add(id);
	}
	
	public void bind() {
		glBindBuffer(GL_ARRAY_BUFFER, id);
	}
	
	public void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);	
	}
	
	public void lock() {
		glUnmapBuffer(GL_ARRAY_BUFFER);
	}
	
	public FloatBuffer unlock(int access) {
		return glMapBuffer(GL_ARRAY_BUFFER, access).asFloatBuffer();
	}
	
	public int getID() {
		return id;
	}
	
	public static void delete() {
		for(int id : buffers) {
			glDeleteBuffers(id);
		}
	}
}
