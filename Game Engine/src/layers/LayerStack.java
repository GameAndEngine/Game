package layers;

import java.util.LinkedHashMap;

public class LayerStack {

	private LinkedHashMap<String, Layer> layers;
	
	public LayerStack() {
		layers = new LinkedHashMap<String, Layer>();
	}
	
	public void start() {
		for(String name : layers.keySet()) {
			Layer layer = layers.get(name);
			layer.start();
		}
	}
	
	public void input() {
		for (int i = layers.size() - 1; i >= 0; i--) {
			Layer layer = get(i);
			if(layer.inputEnabled) {
				layer.input();
				break;
			}
		}
	}
	
	public void tick() {
		for(String name : layers.keySet()) {
			Layer layer = layers.get(name);
			layer.tick();
		}
	}
	
	public void render() {
		for(String name : layers.keySet()) {
			Layer layer = layers.get(name);
			layer.render();
		}
	}
	
	public Layer get(int index) {
		return layers.get(layers.keySet().toArray()[index]);
	}
}
