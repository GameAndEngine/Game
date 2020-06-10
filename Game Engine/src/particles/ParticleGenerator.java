package particles;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

import engine.framework.GameLoop;
import engine.rendering.buffers.Texture;
import engine.utilities.MathUtils;
import particles.presets.ParticlePreset;

public class ParticleGenerator {

	public ArrayList<Particle> particles = new ArrayList<Particle>();
	
	public void emitAnimatedParticles(int numParticles, Vector2f spawnPoint, float rotation, float scale, Vector4f color, Vector4f endColor, Texture texture, Vector2i atlasDim, float life, ParticlePreset preset) {
		ParticlePreset presetInstance = new ParticlePreset(preset);
		for(int i = 0; i < numParticles; i++) {
			float determinedX = spawnPoint.x + presetInstance.generateParticleMotion(presetInstance.positionRangeX);
			float determinedY = spawnPoint.y + presetInstance.generateParticleMotion(presetInstance.positionRangeY);
			float determinedScale = scale + MathUtils.randRangeFloat(GameLoop.random, presetInstance.scaleRange.x, presetInstance.scaleRange.y);
			float determinedVelocityX = presetInstance.generateParticleMotion(presetInstance.velocityRangeX);
			float determinedVelocityY = presetInstance.generateParticleMotion(presetInstance.velocityRangeY);
			Particle particle = new Particle(new Vector2f(determinedX, determinedY), rotation, new Vector2f(determinedScale, determinedScale), 
					color, new Vector2f(determinedVelocityX, determinedVelocityY), presetInstance.generateParticleMotion(presetInstance.rotationalVelocityRange), 
					presetInstance.gravity, endColor, texture, atlasDim, life);
			particles.add(particle);
		}
	}
	
	public void emitTexturedParticles(int numParticles, Vector2f spawnPoint, float rotation, float scale, Vector4f color, Vector4f endColor, Vector4f uv, Texture texture, float life, ParticlePreset preset) {
		ParticlePreset presetInstance = new ParticlePreset(preset);
		for(int i = 0; i < numParticles; i++) {
			float determinedX = spawnPoint.x + presetInstance.generateParticleMotion(presetInstance.positionRangeX);
			float determinedY = spawnPoint.y + presetInstance.generateParticleMotion(presetInstance.positionRangeY);
			float determinedScale = scale + MathUtils.randRangeFloat(GameLoop.random, presetInstance.scaleRange.x, presetInstance.scaleRange.y);
			float determinedVelocityX = presetInstance.generateParticleMotion(presetInstance.velocityRangeX);
			float determinedVelocityY = presetInstance.generateParticleMotion(presetInstance.velocityRangeY);
			Particle particle = new Particle(new Vector2f(determinedX, determinedY), rotation, new Vector2f(determinedScale, determinedScale), 
					color, new Vector2f(determinedVelocityX, determinedVelocityY), presetInstance.generateParticleMotion(presetInstance.rotationalVelocityRange), 
					endColor, presetInstance.gravity, uv, texture, life);
			particles.add(particle);
		}
	}
	
	public void emitSimpleParticles(int numParticles, Vector2f spawnPoint, float rotation, float scale, Vector4f color, Vector4f endColor, float life, ParticlePreset preset) {
		ParticlePreset presetInstance = new ParticlePreset(preset);
		for(int i = 0; i < numParticles; i++) {
			float determinedX = spawnPoint.x + presetInstance.generateParticleMotion(presetInstance.positionRangeX);
			float determinedY = spawnPoint.y + presetInstance.generateParticleMotion(presetInstance.positionRangeY);
			float determinedScale = scale + MathUtils.randRangeFloat(GameLoop.random, presetInstance.scaleRange.x, presetInstance.scaleRange.y);
			float determinedVelocityX = presetInstance.generateParticleMotion(presetInstance.velocityRangeX);
			float determinedVelocityY = presetInstance.generateParticleMotion(presetInstance.velocityRangeY);
			Particle particle = new Particle(new Vector2f(determinedX, determinedY), rotation, new Vector2f(determinedScale, determinedScale), 
					color, new Vector2f(determinedVelocityX, determinedVelocityY), presetInstance.generateParticleMotion(presetInstance.rotationalVelocityRange), 
					endColor, presetInstance.gravity, life);
			particles.add(particle);
		}
	}
	
	public void tick() {
		for(int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			if(!particle.isAlive()) {
				particles.remove(particle);
			}
			particle.tick();
		}
	}
}
