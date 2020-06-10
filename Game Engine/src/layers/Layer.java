package layers;

import engine.ecs.System;

public class Layer {

	public System system;
	
	private String name;
	
	public boolean inputEnabled;
	public boolean tickingEnabled;
	public boolean renderingEnabled;
	
	public Layer(String name, boolean inputEnabled, boolean tickingEnabled, boolean renderingEnabled) {
		this.name = name;
		this.inputEnabled = inputEnabled;
		this.tickingEnabled = tickingEnabled;
		this.renderingEnabled = renderingEnabled;
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
		system.render();
	}
	
	public String getName() {
		return name;
	}
}
