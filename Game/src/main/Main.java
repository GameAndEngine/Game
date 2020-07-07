package main;

import engine.framework.Engine;
import engine.framework.Application;
import engine.io.Window;

public class Main {
	
	public static void main(String[] args) {
		Engine.init();
		Window.create("Game", 900, 600, true, false, false);
		Application loop = new Application(60);
		loop.start();
	}
	
}
