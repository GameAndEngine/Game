package engine.rendering.renderers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.io.Window;
import engine.rendering.buffers.IndexBuffer;
import engine.rendering.buffers.Texture;
import engine.rendering.buffers.VertexArray;
import engine.rendering.buffers.VertexBuffer;
import engine.rendering.shaders.QuadShader;
import engine.utilities.TextureUtils;

public class QuadRenderer {

	public int maxQuads = 100000;
	
	private final int VERTEX_SIZE = 32;
	private final int VERTICES_PER_QUAD = 4;
	private final int INDICES_PER_QUAD = 6;
	
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix = new Matrix4f().identity();
	private VertexBuffer vertexBuffer;
	private VertexArray vertexArray;
	private LinkedHashMap<Texture, ArrayList<Vertex>> verticesMap = new LinkedHashMap<Texture, ArrayList<Vertex>>();
	private FloatBuffer floatBuffer;
	private QuadShader shader;
	
	public Vector2f cameraPosition = new Vector2f(0, 0);
	
	public QuadRenderer(int maxQuads) {
		this.maxQuads = maxQuads;
		vertexBuffer = new VertexBuffer(maxQuads * VERTICES_PER_QUAD * VERTEX_SIZE, GL_DYNAMIC_DRAW);
		vertexArray = new VertexArray();
		shader = new QuadShader();
		
		TextureUtils.init();
		
		int[] indices = new int[maxQuads * INDICES_PER_QUAD];
		
		int offset = 0;
		for(int i = 0; i < indices.length; i += INDICES_PER_QUAD) {
			indices[i + 0] = 0 + offset;
			indices[i + 1] = 1 + offset;
			indices[i + 2] = 2 + offset;
			                   
			indices[i + 3] = 2 + offset;
			indices[i + 4] = 3 + offset;
			indices[i + 5] = 0 + offset;
			
			offset += 4;
		}
		
		IndexBuffer indexBuffer = new IndexBuffer(indices, GL_STATIC_DRAW);
		
		vertexArray.addVertexBufferLayout(vertexBuffer, 0, 2, VERTEX_SIZE, 0);
		vertexArray.addVertexBufferLayout(vertexBuffer, 1, 4, VERTEX_SIZE, 8);
		vertexArray.addVertexBufferLayout(vertexBuffer, 2, 2, VERTEX_SIZE, 24);
		vertexArray.addIndexBuffer(indexBuffer);
	}
	
	public void setProjection() {
		projectionMatrix = projectionMatrix.identity().ortho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);
	}
	
	private void begin() {
		vertexBuffer.bind();
		floatBuffer = vertexBuffer.unlock(GL_WRITE_ONLY);
	}
	
	private void push(Vertex vertex) {
		floatBuffer.put(vertex.position.x);
		floatBuffer.put(vertex.position.y);
		
		floatBuffer.put(vertex.color.x);
		floatBuffer.put(vertex.color.y);
		floatBuffer.put(vertex.color.z);
		floatBuffer.put(vertex.color.w);
		
		floatBuffer.put(vertex.uv.x);
		floatBuffer.put(vertex.uv.y);
	}
	
	private void end() {
		vertexBuffer.lock();
		vertexBuffer.unbind();
	}
	
	public void render(Matrix4f transform, Vector4f color, Vector4f clip, Texture texture) {
		Vector4f topLeft = new Vector4f(0, 0, 0, 1).mul(transform);
		Vector4f bottomLeft = new Vector4f(0, 1, 0, 1).mul(transform);
		Vector4f bottomRight = new Vector4f(1, 1, 0, 1).mul(transform);
		Vector4f topRight = new Vector4f(1, 0, 0, 1).mul(transform);
		
		if(texture == null) {
			texture = TextureUtils.getEmptyTexture();
		}
		
		if(!verticesMap.containsKey(texture)) {
			verticesMap.put(texture, new ArrayList<Vertex>());
		}
		ArrayList<Vertex> vertices = verticesMap.get(texture);
		vertices.add(new Vertex(topLeft, color, new Vector2f(clip.x, clip.y)));
		vertices.add(new Vertex(bottomLeft, color, new Vector2f(clip.x, clip.y + clip.w)));
		vertices.add(new Vertex(bottomRight, color, new Vector2f(clip.x + clip.z, clip.y + clip.w)));
		vertices.add(new Vertex(topRight, color, new Vector2f(clip.x + clip.z, clip.y)));
	}
	
	public void display() {
		begin();
		for(Texture tex : verticesMap.keySet()) {
			ArrayList<Vertex> vertices = verticesMap.get(tex);
			for(int i = 0; i < vertices.size(); i += VERTICES_PER_QUAD) {
				push(vertices.get(i + 0));
				push(vertices.get(i + 1));
				push(vertices.get(i + 2));
				push(vertices.get(i + 3));
			}
		}
		end();
		
		vertexArray.bind();
		vertexArray.enableAttribute(0);
		vertexArray.enableAttribute(1);
		vertexArray.enableAttribute(2);
		
		viewMatrix.translate(new Vector3f(-cameraPosition.x, -cameraPosition.y, 0));
		
		shader.enable();
		shader.setProjectionMatrix(projectionMatrix);
		shader.setViewMatrix(viewMatrix);
		
		int offset = 0;
		for(Texture tex : verticesMap.keySet()) {
			ArrayList<Vertex> vertices = verticesMap.get(tex);
			
			tex.bind();
			glDrawElements(GL_TRIANGLES, (vertices.size() / VERTICES_PER_QUAD) * INDICES_PER_QUAD, GL_UNSIGNED_INT, offset);
			tex.unbind();
			offset += (vertices.size() / VERTICES_PER_QUAD) * INDICES_PER_QUAD * 4;
		}
		
		shader.disable();
		
		vertexArray.disableAttribute(0);
		vertexArray.disableAttribute(1);
		vertexArray.disableAttribute(2);
		vertexArray.unbind();
		
		for(Texture tex : verticesMap.keySet()) {
			ArrayList<Vertex> vertices = verticesMap.get(tex);
			vertices.clear();
		}
	}
	
	public int getMaxQuads() {
		return maxQuads;
	}
	
	private class Vertex {
		public Vector2f position = new Vector2f();
		public Vector4f color = new Vector4f();
		public Vector2f uv = new Vector2f();
		
		public Vertex(Vector4f position, Vector4f color, Vector2f uv) {
			this.position.set(position.x, position.y);
			this.color.set(color.x, color.y, color.z, color.w);
			this.uv.set(uv.x, uv.y);
		}
	}
}
