package engine.rendering.renderers;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.particles.Particle;
import engine.rendering.buffers.IndexBuffer;
import engine.rendering.buffers.Texture;
import engine.rendering.buffers.VertexArray;
import engine.rendering.buffers.VertexBuffer;
import engine.rendering.fonts.Font;

public class Renderer {

	private QuadRenderer quadRenderer;
	private ParticleRenderer particleRenderer;
	private FontRenderer fontRenderer;
	
	public Renderer(int maxQuads, int maxParticles, int maxChars) {
		quadRenderer = new QuadRenderer(maxQuads);
		particleRenderer = new ParticleRenderer(maxParticles);
		fontRenderer = new FontRenderer(maxChars);
		
		setProjection();
	}
	
	public void setProjection() {
		quadRenderer.setProjection();
		particleRenderer.setProjection();
		fontRenderer.setProjection();
	}
	
	public void renderQuad(Matrix4f transform, Vector4f color, Vector4f clip, Texture texture) {
		quadRenderer.render(transform, color, clip, texture);
	}
	
	public void renderParticle(Particle particle) {
		particleRenderer.render(particle);
	}
	
	public void renderText(Font font, String text, Vector2f position, Vector4f[] colors, float fontScale, float alter, float spacing) {
		fontRenderer.renderText(font, text, position, colors, fontScale, alter, spacing);
	}
	
	public void renderText(Font font, String text, Vector2f position, Vector4f color, float fontScale, float alter, float spacing) {
		fontRenderer.renderText(font, text, position, color, fontScale, alter, spacing);
	}
	
	public void display() {
		quadRenderer.display();
		particleRenderer.display();
		fontRenderer.display();
	}
	
	public QuadRenderer getQuadRenderer() {
		return quadRenderer;
	}
	
	public ParticleRenderer getParticleRenderer() {
		return particleRenderer;
	}
	
	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
	
	public static void delete() {
		VertexArray.delete();
		VertexBuffer.delete();
		IndexBuffer.delete();
	}
}
