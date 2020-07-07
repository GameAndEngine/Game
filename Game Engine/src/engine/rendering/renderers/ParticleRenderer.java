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
import engine.particles.Particle;
import engine.rendering.buffers.IndexBuffer;
import engine.rendering.buffers.Texture;
import engine.rendering.buffers.VertexArray;
import engine.rendering.buffers.VertexBuffer;
import engine.rendering.shaders.ParticleShader;
import engine.utilities.TextureUtils;

public class ParticleRenderer {

	private int maxParticles;
	
	private final int VERTEX_SIZE = 44;
	private final int VERTICES_PER_QUAD = 4;
	private final int INDICES_PER_QUAD = 6;

	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix = new Matrix4f();
	private VertexBuffer vertexBuffer;
	private VertexArray vertexArray;
	private LinkedHashMap<Texture, ArrayList<Vertex>> verticesMap = new LinkedHashMap<Texture, ArrayList<Vertex>>();
	private FloatBuffer floatBuffer;
	private ParticleShader shader;
	
	public Vector2f cameraPosition = new Vector2f(0, 0); 
	
	public ParticleRenderer(int maxParticles) {
		this.maxParticles = maxParticles;
		vertexBuffer = new VertexBuffer(maxParticles * VERTICES_PER_QUAD * VERTEX_SIZE, GL_DYNAMIC_DRAW);
		vertexArray = new VertexArray();
		shader = new ParticleShader();
		
		TextureUtils.init();
		
		int[] indices = new int[maxParticles * INDICES_PER_QUAD];
		
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
		vertexArray.addVertexBufferLayout(vertexBuffer, 3, 2, VERTEX_SIZE, 32);
		vertexArray.addVertexBufferLayout(vertexBuffer, 4, 1, VERTEX_SIZE, 36);
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
		
		floatBuffer.put(vertex.uv1.x);
		floatBuffer.put(vertex.uv1.y);
		
		floatBuffer.put(vertex.uv2.x);
		floatBuffer.put(vertex.uv2.y);
		
		floatBuffer.put(vertex.blend);
	}
	
	private void end() {
		vertexBuffer.lock();
		vertexBuffer.unbind();
	}
	
	public void render(Particle particle) {
		Matrix4f transform = new Matrix4f().identity().translate(new Vector3f(particle.position.x, particle.position.y, 1).sub(particle.scale.x / 2, particle.scale.y / 2, 0))
					.translate(particle.scale.x / 2, particle.scale.y / 2, 0).rotate(particle.rotation, 
					new Vector3f(0, 0, 1)).translate(-particle.scale.x / 2, -particle.scale.y / 2, 0).scale(new Vector3f(particle.scale.x, particle.scale.y, 1));
		Texture texture = particle.getTexture();
		Vector4f color = new Vector4f(particle.color);
		
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
		
		vertices.add(new Vertex(topLeft, color, particle.getTexOffset1(), particle.getTexOffset2(), particle.getBlend()));
		vertices.add(new Vertex(bottomLeft, color, 
				new Vector2f(particle.getTexOffset1().x, particle.getTexOffset1().y + particle.getClipHeight()), 
				new Vector2f(particle.getTexOffset2().x, particle.getTexOffset2().y + particle.getClipHeight()), particle.getBlend()));
		vertices.add(new Vertex(bottomRight, color, new Vector2f(particle.getTexOffset1().x + particle.getClipWidth(), 
				particle.getTexOffset1().y + particle.getClipHeight()), new Vector2f(particle.getTexOffset2().x + particle.getClipWidth(), 
				particle.getTexOffset2().y + particle.getClipHeight()), particle.getBlend()));
		vertices.add(new Vertex(topRight, color, new Vector2f(particle.getTexOffset1().x + particle.getClipWidth(), 
				particle.getTexOffset1().y), new Vector2f(particle.getTexOffset2().x + particle.getClipWidth(), 
				particle.getTexOffset2().y), particle.getBlend()));
	}
	
	public void display() {
		begin();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
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
		vertexArray.enableAttribute(3);
		vertexArray.enableAttribute(4);
		
		viewMatrix = viewMatrix.identity().translate(new Vector3f(-cameraPosition.x, -cameraPosition.y, 0));
		
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
		vertexArray.disableAttribute(3);
		vertexArray.disableAttribute(4);
		vertexArray.unbind();
		
		for(Texture tex : verticesMap.keySet()) {
			ArrayList<Vertex> vertices = verticesMap.get(tex);
			vertices.clear();
		}
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public int getMaxParticles() {
		return maxParticles;
	}
	
	private class Vertex {
		public Vector2f position = new Vector2f();
		public Vector4f color = new Vector4f();
		public Vector2f uv1 = new Vector2f();
		public Vector2f uv2 = new Vector2f();
		
		public float blend;
		
		public Vertex(Vector4f position, Vector4f color, Vector2f uv1, Vector2f uv2, float blend) {
			this.position.set(position.x, position.y);
			this.color.set(color.x, color.y, color.z, color.w);
			this.uv1.set(uv1.x, uv1.y);
			this.uv2.set(uv2.x, uv2.y);
			this.blend = blend;
		}
	}
}
