package engine.framework;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import engine.ecs.Entity;
import engine.ecs.RenderingComp;
import engine.io.Keyboard;
import engine.io.Mouse;
import engine.io.Window;
import engine.rendering.layers.Layer;
import engine.rendering.renderers.Renderer;
import engine.rendering.scenes.Scene;
import engine.rendering.scenes.SceneManager;

public class Application {

	private int tickCap;
	private int inputCap;
	
	public static int fps = 0;
	public static int fpsSecond = 0;

	private boolean running = false;

	private Thread inputThread;
	private Thread logicThread;
	
	public static Random random = new Random();
	public static SceneManager sceneManager;
	
	public Application(int tickCap, int inputCap) {
		this.tickCap = tickCap;
		this.inputCap = inputCap;

		Mouse.init();
		Keyboard.init();

		sceneManager = new SceneManager();
		Scene scene = new Scene("Main");
		Layer layer = new Layer("Main", true, true, true);
		Entity entity = new Entity("Main");
		entity.addComponent(new RenderingComp());
		layer.add(entity);
		scene.add(layer);
		sceneManager.activeScene = scene;
		
		inputThread = new Thread(new Runnable() {

			public void run() {
				onInputRun();
			}

		});

		logicThread = new Thread(new Runnable() {

			public void run() {
				onLogicRun();
			}

		});
	}

	public void start() {
		running = true;
		sceneManager.start();
		inputThread.start();
		logicThread.start();
		onRenderRun();
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

	private void onRenderRun() {
		long timer = System.currentTimeMillis();

		while (running) {
			render();
			fps++;
			if (System.currentTimeMillis() - timer > 1000) {
				System.out.println("Frame rate: " + fps);
				fpsSecond = fps;
				timer += 1000;
				fps = 0;
			}
		}
		Renderer.delete();
	}

	private void input() {
		sceneManager.input();
		Mouse.input();
		Keyboard.input();
	}

	private void tick() {
		sceneManager.tick();
	}

	private void render() {
		Window.clearColor();
		glClear(GL_COLOR_BUFFER_BIT);

		sceneManager.render();
		sceneManager.display();
		Window.swapBuffers();
		Window.pollEvents();
	}
}
