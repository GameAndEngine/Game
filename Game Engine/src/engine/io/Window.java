package engine.io;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import engine.framework.GameLoop;
import engine.framework.Logger;

public class Window {

	private static long id;
	
	private static boolean resizable;
	private static boolean centered;
	private static boolean vSyncEnabled;
	
	private static int width;
	private static int height;
	private static int x;
	private static int y;
	
	private static String title;
	private static GLFWVidMode videoMode;
	private static Vector4f clearColor = new Vector4f(0, 0, 0, 1);
	
	public static void create(String title, int width, int height, boolean resizable, boolean centered, boolean vSyncEnabled) {
		Window.title = title;
		Window.width = width;
		Window.height = height;
		Window.resizable = resizable;
		Window.centered = centered;
		Window.vSyncEnabled = vSyncEnabled;
		
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
		
		// Window creation
		videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		id = glfwCreateWindow(width, height, title, 0, 0);
		
		if(id == 0) {
			Logger.errorLogLine("Failed to create window!");
		}
		
		// Window size
		glfwSetWindowSizeCallback(id, new GLFWWindowSizeCallback() {

			public void invoke(long id, int width, int height) {
				Window.width = width;
				Window.height = height;
				GameLoop.renderer.setProjection();
				glViewport(0, 0, width, height);
			}
			
		});
		
		// Window position
		glfwSetWindowPosCallback(id, new GLFWWindowPosCallback() {

			@Override
			public void invoke(long id, int x, int y) {
				Window.x = x;
				Window.y = y;
			}

		});
		
		if(centered) {
			setX((videoMode.width() / 2) - width / 2);
			setY((videoMode.height() / 2) - height / 2);
		}
		
		if(vSyncEnabled) {
			glfwSwapInterval(1);
		}
		
		glfwShowWindow(id);
		createGLContext();
	}
	
	public static void createGLContext() {
		glfwMakeContextCurrent(id);
		createCapabilities();
	}
	
	public static void clearColor() {
		glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
	}
	
	public static void swapBuffers() {
		glfwSwapBuffers(id);
	}
	
	public static void pollEvents() {
		glfwPollEvents();
	}
	
	public static void setX(int x) {
		glfwSetWindowPos(id, x, Window.y);
		Window.x = x;
	}
	
	public static void setY(int y) {
		glfwSetWindowPos(id, Window.x, y);
		Window.y = y;
	}
	
	public static boolean isCloseRequested() {
		return glfwWindowShouldClose(id);
	}
	
	public static long getID() {
		return id;
	}
	
	public static String getTitle() {
		return title;
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}
	
	public static boolean isResizable() {
		return resizable;
	}
	
	public static boolean isCentered() {
		return centered;
	}
	
	public static boolean isVsyncEnabled() {
		return vSyncEnabled;
	}
	
	public static void setClearColor(Vector4f clearColor) {
		Window.clearColor = clearColor;
	}
	
	public static Vector4f getClearColor() {
		return clearColor;
	}
}
