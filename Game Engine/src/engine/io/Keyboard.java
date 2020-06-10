package engine.io;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard {

	public static final int KEY_0 = GLFW_KEY_0;
	public static final int KEY_1 = GLFW_KEY_1;
	public static final int KEY_2 = GLFW_KEY_2;
	public static final int KEY_3 = GLFW_KEY_3;
	public static final int KEY_4 = GLFW_KEY_4;
	public static final int KEY_5 = GLFW_KEY_5;
	public static final int KEY_6 = GLFW_KEY_6;
	public static final int KEY_7 = GLFW_KEY_7;
	public static final int KEY_8 = GLFW_KEY_8;
	public static final int KEY_9 = GLFW_KEY_9;
	
	public static final int KEY_A = GLFW_KEY_A;
	public static final int KEY_B = GLFW_KEY_B;
	public static final int KEY_C = GLFW_KEY_C;
	public static final int KEY_D = GLFW_KEY_D;
	public static final int KEY_E = GLFW_KEY_E;
	public static final int KEY_F = GLFW_KEY_F;
	public static final int KEY_G = GLFW_KEY_G;
	public static final int KEY_H = GLFW_KEY_H;
	public static final int KEY_I = GLFW_KEY_I;
	public static final int KEY_J = GLFW_KEY_J;
	public static final int KEY_K = GLFW_KEY_K;
	public static final int KEY_L = GLFW_KEY_L;
	public static final int KEY_M = GLFW_KEY_M;
	public static final int KEY_N = GLFW_KEY_N;
	public static final int KEY_O = GLFW_KEY_O;
	public static final int KEY_P = GLFW_KEY_P;
	public static final int KEY_Q = GLFW_KEY_Q;
	public static final int KEY_R = GLFW_KEY_R;
	public static final int KEY_S = GLFW_KEY_S;
	public static final int KEY_T = GLFW_KEY_T;
	public static final int KEY_U = GLFW_KEY_U;
	public static final int KEY_V = GLFW_KEY_V;
	public static final int KEY_W = GLFW_KEY_W;
	public static final int KEY_X = GLFW_KEY_X;
	public static final int KEY_Y = GLFW_KEY_Y;
	public static final int KEY_Z = GLFW_KEY_Z;
	
	public static final int KEY_MINUS = GLFW_KEY_MINUS;
	public static final int KEY_EQUAL = GLFW_KEY_EQUAL;
	public static final int KEY_LEFT_BRACKET = GLFW_KEY_LEFT_BRACKET;
	public static final int KEY_RIGHT_BRACKET = GLFW_KEY_RIGHT_BRACKET;
	public static final int KEY_APOSTROPHE = GLFW_KEY_APOSTROPHE;
	public static final int KEY_SEMICOLON = GLFW_KEY_SEMICOLON;
	public static final int KEY_PERIOD = GLFW_KEY_PERIOD;
	public static final int KEY_COMMA = GLFW_KEY_COMMA;
	public static final int KEY_SLASH = GLFW_KEY_SLASH;
	public static final int KEY_BACKSLASH = GLFW_KEY_BACKSLASH;
	
	public static final int KEY_LEFT_SHIFT = GLFW_KEY_LEFT_SHIFT;
	public static final int KEY_RIGHT_SHIFT = GLFW_KEY_RIGHT_SHIFT;
	public static final int KEY_LEFT_ALT = GLFW_KEY_LEFT_ALT;
	public static final int KEY_RIGHT_ALT = GLFW_KEY_RIGHT_ALT;
	public static final int KEY_LEFT_CONTROL = GLFW_KEY_LEFT_CONTROL;
	public static final int KEY_RIGHT_CONTROL = GLFW_KEY_RIGHT_CONTROL;
	public static final int KEY_ENTER = GLFW_KEY_ENTER;
	public static final int KEY_SPACE = GLFW_KEY_SPACE;
	public static final int KEY_BACKSPACE = GLFW_KEY_BACKSPACE;
	public static final int KEY_DELETE = GLFW_KEY_DELETE;
	public static final int KEY_TAB = GLFW_KEY_TAB;
	public static final int KEY_ESCAPE = GLFW_KEY_ESCAPE;
	public static final int KEY_LEFT = GLFW_KEY_LEFT;
	public static final int KEY_RIGHT = GLFW_KEY_RIGHT;
	public static final int KEY_UP = GLFW_KEY_UP;
	public static final int KEY_DOWN = GLFW_KEY_DOWN;
	
	private static ArrayList<Integer> pressedKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> releasedKeys = new ArrayList<Integer>();
	
	public static void init() {
		glfwSetKeyCallback(Window.getID(), new GLFWKeyCallback() {

			public void invoke(long id, int key, int scancode, int action, int mods) {
				if(action == GLFW_PRESS) {
					pressedKeys.add(key);
				} else if(action == GLFW_RELEASE) {
					releasedKeys.add(key);
				}
			}
			
		});
		
		glfwSetCharCallback(Window.getID(), new GLFWCharCallback() {

			public void invoke(long id, int character) {
				onKeyPress((char) character);
			}
			
		});
	}
	
	public static void onKeyPress(char character) {
		
	}
	
	public static void input() {
		pressedKeys.clear();
		releasedKeys.clear();
	}
	
	public static boolean isKeyDown(int key) {
		return glfwGetKey(Window.getID(), key) == GLFW_PRESS;
	}
	
	public static boolean isKeyPressed(int key) {
		return pressedKeys.contains(key);
	}
	
	public static boolean isKeyReleased(int key) {
		return releasedKeys.contains(key);
	}
}
