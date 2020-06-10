package main;

import engine.framework.Engine;
import engine.framework.GameLoop;
import engine.io.Window;

public class Main {
	
	public static void main(String[] args) {
		Engine.init();
		Window.create("Game", 900, 600, true, false, false);
		GameLoop loop = new GameLoop(60, 60);
		loop.start();
	}
	
}
