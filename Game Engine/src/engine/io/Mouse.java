package engine.io;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Mouse {

	private static Vector2f position = new Vector2f();
	
	private static ArrayList<Integer> pressedButtons = new ArrayList<Integer>();
	private static ArrayList<Integer> releasedButtons = new ArrayList<Integer>();
	
	public static final int LEFT_BUTTON = GLFW_MOUSE_BUTTON_LEFT;
	public static final int RIGHT_BUTTON = GLFW_MOUSE_BUTTON_RIGHT;
	public static final int MIDDLE_BUTTON = GLFW_MOUSE_BUTTON_MIDDLE;
	
	public static void init() {
		glfwSetCursorPosCallback(Window.getID(), new GLFWCursorPosCallback() {
			
			public void invoke(long id, double x, double y) {
				position.x = (float) x;
				position.y = (float) y;
			}
			
		});
		
		glfwSetMouseButtonCallback(Window.getID(), new GLFWMouseButtonCallback() {

			public void invoke(long id, int button, int action, int mods) {
				if(action == GLFW_PRESS) {
					pressedButtons.add(button);
				} else if(action == GLFW_RELEASE) {
					releasedButtons.add(button);
				}
			}
			
		});
	}
	
	public static void input() {
		pressedButtons.clear();
		releasedButtons.clear();
	}
	
	public static boolean isButtonDown(int button) {
		return glfwGetMouseButton(Window.getID(), button) == GLFW_PRESS;
	}
	
	public static boolean isButtonPressed(int button) {
		return pressedButtons.contains(button);
	}
	
	public static boolean isButtonReleased(int button) {
		return releasedButtons.contains(button);
	}
	
	public static Vector2f getPosition() {
		return position;
	}
}
