package engine.ecs;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import engine.rendering.renderers.Renderer;

public class System {

	private LinkedHashMap<String, LinkedList<Entity>> entities;
	
	public System() {
		entities = new LinkedHashMap<String, LinkedList<Entity>>();
	}
	
	public void add(Entity entity) {
		entity.system = this;
		if (!entities.containsKey(entity.getTag())) {
			entities.put(entity.getTag(), new LinkedList<Entity>());
		}
		
		entities.get(entity.getTag()).add(entity);
	}
	
	public void addAtRuntime(Entity entity) {
		add(entity);
		entity.start();
	}
	
	public void remove(Entity entity) {
		entities.get(entity.getTag()).remove(entity);
	}
	
	public void removeEntitiesOfTag(String tag) {
		entities.remove(tag);
	}
	
	public void start() {
		for(String key : entities.keySet()) {
			for (int i = 0; i < entities.get(key).size(); i++)
			entities.get(key).get(i).start();
		}
	}
	
	public void input() {
		for(String key : entities.keySet()) {
			for (int i = 0; i < entities.get(key).size(); i++)
			entities.get(key).get(i).input();
		}
	}
	
	public void tick() {
		for(String key : entities.keySet()) {
			for (int i = 0; i < entities.get(key).size(); i++)
			entities.get(key).get(i).tick();
		}
	}
	
	public void render(Renderer renderer) {
		for(String key : entities.keySet()) {
			for (int i = 0; i < entities.get(key).size(); i++)
			entities.get(key).get(i).render(renderer);
		}
	}
	
	public Set<String> getTags() {
		return entities.keySet();
	}
	
	public LinkedList<Entity> getEntities(String tag) {
		return entities.get(tag);
	}
}
