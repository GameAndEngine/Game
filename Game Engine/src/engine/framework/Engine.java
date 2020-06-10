package engine.framework;

import static org.lwjgl.glfw.GLFW.*;

public class Engine {

	private static boolean glfwInitialized;
	
	public static void init() {
		glfwInitialized = glfwInit();
		if(!glfwInitialized) {
			Logger.errorLogLine("GLFW Initialization Failed!");
		}
	}

	public static void exit(int exitStatus) {
		System.exit(exitStatus);
	}
	
	public static boolean isGLFWInitialized() {
		return glfwInitialized;
	}
}
