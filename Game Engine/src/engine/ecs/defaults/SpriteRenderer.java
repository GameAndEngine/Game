package engine.ecs.defaults;

import org.joml.Vector4f;

import engine.ecs.Component;
import engine.rendering.buffers.Texture;
import engine.rendering.renderers.Renderer;

public class SpriteRenderer extends Component {

	public Vector4f color;
	public Vector4f clip;
	public Texture texture;
	
	public SpriteRenderer() {
		super("SpriteRenderer");
	}
	
	public void render(Renderer renderer) {
		renderer.renderQuad(parent.transform.toMatrix(), color, clip, texture);
	}
	
}
