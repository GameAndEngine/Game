package engine.rendering.shaders;

import org.joml.Matrix4f;

public class QuadShader extends Shader {
	
	public QuadShader() {
		super("/engine/rendering/shaders/sources/QuadVS.glsl", "/engine/rendering/shaders/sources/QuadFS.glsl");
		
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
