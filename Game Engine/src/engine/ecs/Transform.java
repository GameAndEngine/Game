package engine.ecs;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Transform {

	public Vector2f position = new Vector2f();
	public Vector2f scale = new Vector2f();
	
	public float rotation = 0;
	
	public Matrix4f toMatrix() {
		return new Matrix4f().identity().translate(position.x, position.y, 0).rotate(rotation, 0, 0, 1).scale(scale.x, scale.y, 1);
	}
}
