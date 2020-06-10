package engine.framework;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.Random;

import engine.io.Keyboard;
import engine.io.Mouse;
import engine.io.Window;
import engine.rendering.renderers.Renderer;

public class GameLoop {

	private int tickCap;
	private int inputCap;
	private int fps = 0;

	private boolean running = false;

	private Thread logicThread;
	private Thread inputThread;
	
	public static Renderer renderer; 
	
	public static Random random = new Random();

	public GameLoop(int tickCap, int inputCap) {
		this.tickCap = tickCap;
		this.inputCap = inputCap;

		Mouse.init();
		Keyboard.init();

		renderer = new Renderer(100000, 100000);
		
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

	private void tick() {
	}

	private void input() {
		Mouse.input();
		Keyboard.input();
	}

	private void render() {
		Window.clearColor();
		glClear(GL_COLOR_BUFFER_BIT);
		renderer.display();
		
		Window.swapBuffers();
		Window.pollEvents();
	}
}
