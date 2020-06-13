package layers;

import engine.ecs.System;
import engine.rendering.renderers.Renderer;

public class Layer {

	public System system;
	public Renderer renderer;
	
	private String name;
	
	public boolean inputEnabled;
	public boolean tickingEnabled;
	public boolean renderingEnabled;
	
	public Layer(String name, boolean inputEnabled, boolean tickingEnabled, boolean renderingEnabled) {
		this.name = name;
		this.inputEnabled = inputEnabled;
		this.tickingEnabled = tickingEnabled;
		this.renderingEnabled = renderingEnabled;
		
		renderer = new Renderer(10000, 10000);
	}
	
	public void start() {
		system.start();
	}
	
	public void input() {
		system.input();
	}
	
	public void tick() {
		system.tick();
	}
	
	public void render() {
		system.render(renderer);
	}
	
	public void display() {
		renderer.display();
	}
	
	public String getName() {
		return name;
	}
}
