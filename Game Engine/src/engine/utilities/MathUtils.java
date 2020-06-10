package engine.utilities;

import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MathUtils {

	public static float lerp(float var, float destination, float smoothness) {
		return var + smoothness * (destination - var);
	}
	
	public static Vector2f lerp(Vector2f src, Vector2f dst, float t) {
		Vector2f result = new Vector2f();
		
		result.x = lerp(src.x, dst.x, t);
		result.y = lerp(src.y, dst.y, t);
		
		return result;
	}
	
	public static Vector3f lerp(Vector3f src, Vector3f dst, float t) {
		Vector3f result = new Vector3f();
		
		result.x = lerp(src.x, dst.x, t);
		result.y = lerp(src.y, dst.y, t);
		result.z = lerp(src.z, dst.z, t);
		
		return result;
	}
	
	public static Vector4f lerp(Vector4f src, Vector4f dst, float t) {
		Vector4f result = new Vector4f();
		
		result.x = lerp(src.x, dst.x, t);
		result.y = lerp(src.y, dst.y, t);
		result.z = lerp(src.z, dst.z, t);
		result.w = lerp(src.w, dst.w, t);
		
		return result;
	}
	
	public static float randRangeFloat(Random random, float min, float max) {
		return (float) (min + random.nextDouble() * (max - min));
	}
}
