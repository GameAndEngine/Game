package engine.rendering.scenes;

import java.util.LinkedHashMap;

public class SceneManager {

	public LinkedHashMap<String, Scene> scenes;
	public Scene activeScene = null;
	
	public SceneManager() {
		scenes = new LinkedHashMap<String, Scene>();
	}
	
	public void add(Scene scene) {
		scenes.put(scene.getName(), scene);
	}
	
	public void remove(Scene scene) {
		scenes.put(scene.getName(), scene);
	}
	
	public void start() {
		if(activeScene != null)
			activeScene.start();
	}
	
	public void input() {
		if(activeScene != null)
			activeScene.input();
	}
	
	public void tick() {
		if(activeScene != null)
			activeScene.tick();
	}
	
	public void render() {
		if(activeScene != null)
			activeScene.render();
	}
	
	public void display() {
		if(activeScene != null)
			activeScene.display();
	}
}
