package engine.rendering.scenes;

import engine.rendering.layers.Layer;
import engine.rendering.layers.LayerStack;

public class Scene {

	private String name;
	
	public LayerStack stack;
	
	public Scene(String name) {
		this.name = name;
		stack = new LayerStack();
	}
	
	public void add(Layer layer) {
		stack.add(layer);
	}
	
	public void remove(Layer layer) {
		stack.remove(layer);
	}
	
	public void start() {
		stack.start();
	}
	
	public void input() {
		stack.input();
	}
	
	public void tick() {
		stack.tick();
	}
	
	public void render() {
		stack.render();
	}
	
	public void display() {
		stack.display();
	}
	
	public Layer get(int index) {
		return stack.get(index);
	}
	
	public Layer get(String name) {
		return stack.get(name);
	}
	
	public String getName() {
		return name;
	}
}
