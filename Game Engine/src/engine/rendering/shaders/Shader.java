package engine.rendering.shaders;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.LinkedHashMap;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import engine.framework.Engine;
import engine.framework.Logger;
import engine.utilities.StringUtils;

public class Shader {

	private LinkedHashMap<String, Integer> uniforms = new LinkedHashMap<String, Integer>();
	
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public Shader(String vertexFilePath, String fragmentFilePath) {
		programID = glCreateProgram();
		vertexShaderID = compileShader(vertexFilePath, GL_VERTEX_SHADER);
		fragmentShaderID = compileShader(fragmentFilePath, GL_FRAGMENT_SHADER);
		
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		
		glLinkProgram(programID);
		glValidateProgram(programID);
	}
	
	protected void addUniform(String name) {
		uniforms.put(name, glGetUniformLocation(programID, name));
		if(uniforms.get(name) == -1) {
			Logger.errorLogLine("Could not find uniform: " + name);
			System.exit(-1);
		}
	}
	
	protected void setUniform1f(String name, float value) {
		glUniform1f(uniforms.get(name), value);
	}
	
	protected void setUniform2f(String name, Vector2f value) {
		glUniform2f(uniforms.get(name), value.x, value.y);
	}
	
	protected void setUniform3f(String name, Vector3f value) {
		glUniform3f(uniforms.get(name), value.x, value.y, value.z);
	}
	
	protected void setUniform4f(String name, Vector4f value) {
		glUniform4f(uniforms.get(name), value.x, value.y, value.z, value.w);
	}
	
	protected void setMatrix4f(String name, Matrix4f value) {
		glUniformMatrix4fv(uniforms.get(name), false, value.get(matrixBuffer));
	}
	
	protected void setUniform1i(String name, int value) {
		glUniform1i(uniforms.get(name), value);
	}
	
	protected void setUniformBoolean(String name, boolean value) {
		glUniform1i(uniforms.get(name), value ? 1 : 0);
	}
	
	public void enable() {
		glUseProgram(programID);
	}
	
	public void disable() {
		glUseProgram(0);
	}
	
	private int compileShader(String filePath, int type) {
		int id = glCreateShader(type);
		glShaderSource(id, StringUtils.readFileAsLines(filePath));
		glCompileShader(id);
		
		if(glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
			Logger.errorLogLine("Compilation of the following shader failed: " + filePath);
			Logger.errorLogLine(glGetShaderInfoLog(id));
			Engine.exit(-1);
		}
		
		return id;
	}
	
	public void delete() {
		glDeleteProgram(programID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
	}
	
	public int getProgramID() {
		return programID;
	}
	
	public int getVertexShaderID() {
		return vertexShaderID;
	}
	
	public int getFragmentShaderID() {
		return fragmentShaderID;
	}
}
