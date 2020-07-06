package engine.particles;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

import engine.rendering.buffers.Texture;
import engine.utilities.MathUtils;
import engine.utilities.TextureUtils;

public class Particle {

	private Texture texture;
	private ParticleType particleType;

	public Vector2f position;
	public Vector2f scale;
	public Vector4f color;
	public Vector2f positionalVelocity;
	public Vector4f endColor;

	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private Vector2i atlasDimensions;
	private Vector2f startScale;
	private Vector4f startColor;
	
	public float rotation;
	public float rotationalVelocity;
	public float gravity;

	private float blend;
	private float life;
	private float lifeCounter = 0;
	private float clipWidth;
	private float clipHeight;

	private Particle(Vector2f position, float rotation, Vector2f scale, Vector4f color, Vector2f positionalVelocity,
			float rotationalVelocity, Vector4f endColor, float gravity, Texture texture, Vector2i atlasDimensions, float life) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.startScale = new Vector2f(scale);
		this.color = color;
		this.startColor = new Vector4f(color);
		this.positionalVelocity = positionalVelocity;
		this.rotationalVelocity = rotationalVelocity;
		this.endColor = endColor;
		this.gravity = gravity;
		this.texture = texture;
		this.atlasDimensions = atlasDimensions;
		this.life = life;
		this.particleType = ParticleType.ANIMATED;
	}

	public Particle(Vector2f position, float rotation, Vector2f scale, Vector4f color, Vector2f positionalVelocity,
			float rotationalVelocity, Vector4f endColor, float gravity, float life) {
		this(position, rotation, scale, color, positionalVelocity, rotationalVelocity, endColor, gravity,
				TextureUtils.getEmptyTexture(), null, life);
		particleType = ParticleType.SIMPLE;
	}

	public Particle(Vector2f position, float rotation, Vector2f scale, Vector4f color, Vector2f positionalVelocity,
			float rotationalVelocity, Vector4f endColor, float gravity,
			Vector4f uv, Texture texture, float life) {
		this(position, rotation, scale, color, positionalVelocity, rotationalVelocity, endColor, gravity,
				texture, null, life);
		this.texOffset1 = new Vector2f(uv.x, uv.y);
		this.texOffset2 = new Vector2f(uv.x, uv.y);
		clipWidth = uv.z;
		clipHeight = uv.z;
		particleType = ParticleType.TEXTURED;
	}

	public Particle(Vector2f position, float rotation, Vector2f scale, Vector4f color, Vector2f positionalVelocity,
			float rotationalVelocity, float gravity, Vector4f endColor,
			Texture atlas, Vector2i atlasDimensions, float life) {
		this(position, rotation, scale, color, positionalVelocity, rotationalVelocity, endColor, gravity,
				atlas, atlasDimensions, life);
		clipWidth = (1.0f / atlasDimensions.x);
		clipHeight = (1.0f / atlasDimensions.y);
		particleType = ParticleType.ANIMATED;
	}

	public void tick() {
		positionalVelocity.y += gravity;
		position = position.add(positionalVelocity);
		rotation += rotationalVelocity;
		if (scale.x >= 0 && scale.y >= 0) {
			scale = MathUtils.lerp(startScale, new Vector2f(0, 0), lifeCounter / life);
		}
		color = MathUtils.lerp(startColor, endColor, lifeCounter / life);
		if (particleType == ParticleType.ANIMATED)
			updateTextureCoords();
		lifeCounter++;
	}

	private void updateTextureCoords() {
		float lifeProgression = lifeCounter / life;
		int stageCount = atlasDimensions.x * atlasDimensions.y;
		float atlasProgression = lifeProgression * stageCount;
		int currentStage = (int) Math.floor(atlasProgression);
		int nextStage = currentStage < stageCount - 1 ? currentStage + 1 : currentStage;
		setTextureOffset(texOffset1, currentStage);
		setTextureOffset(texOffset2, nextStage);
		blend = atlasProgression % 1;
	}

	private void setTextureOffset(Vector2f offset, int stageIndex) {
		int row = (int)(stageIndex / atlasDimensions.x);
		int column = stageIndex % atlasDimensions.x;
		
		offset.x = (float) column / atlasDimensions.x;
		offset.y = (float) row / atlasDimensions.y;
	}

	public boolean isAlive() {
		return lifeCounter <= life;
	}

	public Texture getTexture() {
		return texture;
	}

	public float getBlend() {
		return blend;
	}

	public Vector2f getTexOffset1() {
		return texOffset1;
	}

	public Vector2f getTexOffset2() {
		return texOffset2;
	}

	public float getClipWidth() {
		return clipWidth;
	}
	
	public float getClipHeight() {
		return clipHeight;
	}

	public enum ParticleType {
		SIMPLE, TEXTURED, ANIMATED
	}
}
