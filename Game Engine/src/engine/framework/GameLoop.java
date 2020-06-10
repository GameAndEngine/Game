package engine.framework;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.io.Keyboard;
import engine.io.Mouse;
import engine.io.Window;
import engine.rendering.buffers.Texture;
import engine.rendering.renderers.Renderer;
import particles.ParticleGenerator;
import particles.presets.ParticlePresets;

public class GameLoop {

	private int tickCap;
	private int inputCap;
	private int fps = 0;

	private boolean running = false;

	private Thread logicThread;
	private Thread inputThread;
	private ParticleGenerator gen;
	
	public static Renderer renderer;

	public static Random random = new Random();

	public GameLoop(int tickCap, int inputCap) {
		this.tickCap = tickCap;
		this.inputCap = inputCap;

		Mouse.init();
		Keyboard.init();

		renderer = new Renderer(100000, 100000);
		gen = new ParticleGenerator();

		logicThread = new Thread(new Runnable() {

			public void run() {
				onLogicRun();
			}

		});

		inputThread = new Thread(new Runnable() {

			public void run() {
				onInputRun();
			}

		});
	}

	public void start() {
		running = true;
		logicThread.start();
		inputThread.start();
		onRenderRun();
	}

	private void onLogicRun() {
		long lastTime = System.nanoTime();
		double amountOfTicks = (double) tickCap;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		@SuppressWarnings("unused")
		int ticks = 0;

		while (running) {
			if (Engine.isGLFWInitialized())
				running = !Window.isCloseRequested();
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				ticks++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				ticks = 0;
			}
		}
	}

	private void onInputRun() {
		long lastTime = System.nanoTime();
		double amountOfTicks = (double) inputCap;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		@SuppressWarnings("unused")
		int inputs = 0;

		while (running) {
			if (Engine.isGLFWInitialized())
				running = !Window.isCloseRequested();
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				input();
				inputs++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				inputs = 0;
			}
		}
	}

	private void onRenderRun() {
		long timer = System.currentTimeMillis();

		while (running) {
			render();
			fps++;
			if (System.currentTimeMillis() - timer > 1000) {
				System.out.println("Frame rate: " + fps);
				timer += 1000;
				fps = 0;
			}
		}
		Renderer.delete();
	}

	Texture texture = new Texture("../Game Engine/res/cosmic.png");

	boolean canGen = false;

	private void tick() {
		gen.tick();
		if (canGen) {
			float x = Mouse.getPosition().x;
			float y = Mouse.getPosition().y;
			gen.emitTexturedParticles(2, new Vector2f(x, y), 0, 20, new Vector4f(1, 0.7f, 0, 1), new Vector4f(1, 0.2f, 0.6f, 0),
					new Vector4f(0, 0, 1, 1), texture, 1600, ParticlePresets.FLAME);
		}
	}

	private void input() {
		if (Mouse.isButtonDown(Mouse.LEFT_BUTTON)) {
			canGen = true;
		} else
			canGen = false;
		Mouse.input();
		Keyboard.input();
	}

	private void render() {
		Window.clearColor();
		glClear(GL_COLOR_BUFFER_BIT);
		for (int i = 0; i < gen.particles.size(); i++) {
			try {
				renderer.renderParticle(gen.particles.get(i));
			} catch (Exception e) {
				System.out.println(gen.particles == null);
				System.out.println(gen.particles.get(i) == null);
				e.printStackTrace();
				System.exit(1);
			}
		}

		renderer.display();
		
		Window.swapBuffers();
		Window.pollEvents();
	}
}
