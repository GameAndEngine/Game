package engine.rendering.renderers;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import engine.rendering.buffers.IndexBuffer;
import engine.rendering.buffers.Texture;
import engine.rendering.buffers.VertexArray;
import engine.rendering.buffers.VertexBuffer;

public class Renderer {

	public QuadRenderer quadRenderer;
	
	public Renderer(int maxQuads, int maxParticles) {
		quadRenderer = new QuadRenderer(maxQuads);
		
		setProjection();
	}
	
	public void setProjection() {
		quadRenderer.setProjection();
	}
	
	public void renderQuad(Matrix4f transform, Vector4f color, Vector4f clip, Texture texture) {
		quadRenderer.render(transform, color, clip, texture);
	}
	
	public void display() {
		quadRenderer.display();
	}
	
	public static void delete() {
		VertexArray.delete();
		VertexBuffer.delete();
		IndexBuffer.delete();
	}
}
