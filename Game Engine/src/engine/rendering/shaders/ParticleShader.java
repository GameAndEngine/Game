package engine.rendering.shaders;

import org.joml.Matrix4f;

public class ParticleShader extends Shader {
	
	public ParticleShader() {
		super("/engine/rendering/shaders/sources/ParticleVS.glsl", "/engine/rendering/shaders/sources/ParticleFS.glsl");
		
		addUniform("projection");
		addUniform("view");
	}

	public void setProjectionMatrix(Matrix4f projection) {
		setMatrix4f("projection", projection);
	}
	
	public void setViewMatrix(Matrix4f view) {
		setMatrix4f("view", view);
	}
}
