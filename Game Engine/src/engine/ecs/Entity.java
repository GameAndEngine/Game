package engine.ecs;

import java.util.LinkedHashMap;

import engine.rendering.renderers.Renderer;

public class Entity {

	private LinkedHashMap<String, Component> components;
	
	public String tag;
	public System system;

	public Entity(String tag) {
		this.tag = tag;
		components = new LinkedHashMap<String, Component>();
	}

	public void start() {
		for(String name : components.keySet()) {
			getComponent(name).start();
		}
	}
	
	public void input() {
		for(String name : components.keySet()) {
			getComponent(name).input();
		}
	}
	
	public void tick() {
		for(String name : components.keySet()) {
			getComponent(name).tick();
		}
	}
	
	public void render(Renderer renderer) {
		for(String name : components.keySet()) {
			getComponent(name).render(renderer);
		}
	}
	
	public Component addComponent(Component component) {
		components.put(component.getClass().getSimpleName(), component);
		component.parent = this;
		component.onAttach();
		return component;
	}

	public Component getComponent(String name) {
		return components.get(name);
	}
	
	public String getTag() {
		return tag;
	}
}
