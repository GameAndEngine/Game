package particles.presets;

import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.framework.GameLoop;
import engine.utilities.MathUtils;

public class ParticlePreset {

	public Vector2f positionRangeX;
	public Vector2f positionRangeY;
	public Vector2f velocityRangeX;
	public Vector2f velocityRangeY;
	public Vector2f scaleRange;
	public Vector4f endColor;
	public Vector2f rotationalVelocityRange;
	
	public float rotation;
	public float gravity;
	
	public ParticlePreset(Vector2f positionRangeX, Vector2f positionRangeY, float rotation, Vector2f velocityRangeX,
			Vector2f velocityRangeY, Vector2f rotationalVelocityRange, Vector2f scaleRange, float gravity) {
		this.positionRangeX = positionRangeX;
		this.positionRangeY = positionRangeY;
		this.rotation = rotation;
		this.velocityRangeX = velocityRangeX;
		this.velocityRangeY = velocityRangeY;
		this.rotationalVelocityRange = rotationalVelocityRange;
		this.scaleRange = scaleRange;
		this.gravity = gravity;
	}
	
	public ParticlePreset(ParticlePreset preset) {
		this(preset.positionRangeX, preset.positionRangeY, preset.rotation, preset.velocityRangeX, preset.velocityRangeY, 
				preset.rotationalVelocityRange, preset.scaleRange, preset.gravity);
	}
	
	public float generateParticleMotion(Vector2f range) {
		Random rand = GameLoop.random;
		return MathUtils.randRangeFloat(rand, range.x, range.y);
	}
}
