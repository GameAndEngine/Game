package engine.rendering.buffers;

import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;

public class VertexArray {

	private int id;
	
	private static ArrayList<Integer> arrays = new ArrayList<Integer>();
	
	public VertexArray() {
		id = glGenVertexArrays();
		arrays.add(id);
	}
	
	public void addVertexBufferLayout(VertexBuffer buffer, int index, int size, int stride, long pointer) {
		bind();
		buffer.bind();
		glVertexAttribPointer(index, size, GL_FLOAT, false, stride, pointer);
	}
	
	public void addIndexBuffer(IndexBuffer buffer) {
		bind();
		buffer.bind();
		unbind();
		buffer.unbind();
	}
	
	public void enableAttribute(int index) {
		glEnableVertexAttribArray(index);
	}
	
	public void disableAttribute(int index) {
		glDisableVertexAttribArray(index);
	}
	
	public void bind() {
		glBindVertexArray(id);
	}
	
	public void unbind() {
		glBindVertexArray(0);
	}
	
	public static void delete() {
		for(int id : arrays) {
			glDeleteVertexArrays(id);
		}
	}
}
