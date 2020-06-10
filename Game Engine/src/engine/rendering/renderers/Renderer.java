package engine.rendering.renderers;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import engine.rendering.buffers.IndexBuffer;
import engine.rendering.buffers.Texture;
import engine.rendering.buffers.VertexArray;
import engine.rendering.buffers.VertexBuffer;
import particles.Particle;

public class Renderer {

	private QuadRenderer quadRenderer;
	private ParticleRenderer particleRenderer;
	
	public Renderer(int maxQuads, int maxParticles) {
		quadRenderer = new QuadRenderer(maxQuads);
		particleRenderer = new ParticleRenderer(maxParticles);
		
		setProjection();
	}
	
	public void setProjection() {
		quadRenderer.setProjection();
		particleRenderer.setProjection();
	}
	
	public void renderQuad(Matrix4f transform, Vector4f color, Vector4f clip, Texture texture) {
		quadRenderer.render(transform, color, clip, texture);
	}
	
	public void renderParticle(Particle particle) {
		try {
			particleRenderer.render(particle);
		} catch(Exception e) {
			System.out.println(particle == null);
			System.out.println(particleRenderer == null);
		}
	}
	
	public void display() {
		quadRenderer.display();
		particleRenderer.display();
	}
	
	public QuadRenderer getQuadRenderer() {
		return quadRenderer;
	}
	
	public ParticleRenderer getParticleRenderer() {
		return particleRenderer;
	}
	
	public static void delete() {
		VertexArray.delete();
		VertexBuffer.delete();
		IndexBuffer.delete();
	}
}
