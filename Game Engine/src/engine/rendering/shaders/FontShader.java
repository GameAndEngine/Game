package engine.rendering.shaders;

import org.joml.Matrix4f;

public class FontShader extends Shader {
	
	public FontShader() {
		super("/engine/rendering/shaders/sources/FontVS.glsl", "/engine/rendering/shaders/sources/FontFS.glsl");
		
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
