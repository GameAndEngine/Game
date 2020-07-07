package engine.framework;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import engine.ecs.Entity;
import engine.ecs.RenderingComp;
import engine.io.Keyboard;
import engine.io.Mouse;
import engine.io.Window;
import engine.rendering.layers.Layer;
import engine.rendering.scenes.Scene;
import engine.rendering.scenes.SceneManager;

public class Application {

	private int updateCap;
	
	public static int fps = 0;
	public static int fpsSecond = 0;

	private boolean running = false;
	
	public static Random random = new Random();
	public static SceneManager sceneManager;
	
	public Application(int updateCap) {
		this.updateCap = updateCap;

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
	}

	public void start() {
		running = true;
		sceneManager.start();

		long lastTime = System.nanoTime();
		double amountOfTicks = updateCap;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		while(running){
			if (Engine.isGLFWInitialized())
				running = !Window.isCloseRequested();
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				input();
				tick();
				updates++;
				delta--;
			}
			render();
			fps++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				fpsSecond = fps;
				System.out.println("FPS: " + fps + " TICKS: " + updates);
				fps = 0;
				updates = 0;
			}
		}
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
