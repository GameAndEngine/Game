package engine.utilities;

import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MathUtils {

	public static float lerp(float var, float destination, float smoothness) {
		var += (destination - var) * smoothness;
		return var;
	}
	
	public static void lerp(Vector4f current, Vector4f destination, float smoothness) {
		current.x = lerp(current.x, destination.x, smoothness);
		current.y = lerp(current.y, destination.y, smoothness);
		current.z = lerp(current.z, destination.z, smoothness);
		current.w = lerp(current.w, destination.w, smoothness);
	}
	
	public static void lerp(Vector3f current, Vector3f destination, float smoothness) {
		current.x = lerp(current.x, destination.x, smoothness);
		current.y = lerp(current.y, destination.y, smoothness);
		current.z = lerp(current.z, destination.z, smoothness);
	}
	
	public static void lerp(Vector2f current, Vector2f destination, float smoothness) {
		current.x = lerp(current.x, destination.x, smoothness);
		current.y = lerp(current.y, destination.y, smoothness);
	}
	
	public static float randRangeFloat(Random random, float min, float max) {
		return (float) (min + random.nextDouble() * (max - min));
	}
}
