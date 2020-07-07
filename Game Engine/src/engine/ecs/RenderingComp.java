package engine.ecs;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.io.Mouse;
import engine.io.Window;
import engine.particles.ParticleGenerator;
import engine.particles.presets.ParticlePresets;
import engine.rendering.buffers.Texture;
import engine.rendering.fonts.Font;
import engine.rendering.renderers.Renderer;
import engine.utilities.FontUtils;

public class RenderingComp extends Component {
	
	private Font font;
	private ParticleGenerator gen;
	private Texture cosmic;
	
	public void start() {
		font = FontUtils.loadFont("/engine/rendering/fonts/fnts/candara.fnt", "../Game Engine/res/fonts/candara.png");
		gen = new ParticleGenerator();
		cosmic = new Texture("../Game Engine/res/textures/cosmic.png");
	}
	
	public void input() {
		gen.emitTexturedParticles(4, new Vector2f(Mouse.getPosition()), 0, 18, new Vector4f(0.3f, 0.3f, 0, 1), new Vector4f(1, 0, 0, 0), 
				new Vector4f(0, 0, 1, 1), cosmic, 50, ParticlePresets.FLAME);
	}
	
	public void tick() {
		gen.tick();
	}
	
	public void render(Renderer renderer) { 
		renderer.renderText(font, "Application", new Vector2f(50, 50), new Vector4f(0, 1, 0.5f, 1), 5 * (Mouse.getPosition().x / Window.getWidth()), 1f, 1f);
		for(int i = 0; i < gen.particles.size(); i++) {
			renderer.renderParticle(gen.particles.get(i));
		}
	}
	
}
