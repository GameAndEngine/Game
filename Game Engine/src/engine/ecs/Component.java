package engine.ecs;

import engine.rendering.renderers.Renderer;

public abstract class Component {
	
	protected Entity parent;
	protected String name;
	
	public void start() {}
	public void input() {}
	public void tick() {}
	public void render(Renderer renderer) {}
	
	public void onAttach() {}
	
	public String getString() {
		return name;
	}
	
}
