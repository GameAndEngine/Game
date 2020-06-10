package engine.utilities;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import engine.rendering.buffers.Texture;

public class TextureUtils {

	private static Texture emptyTexture;
	
	public static void init() {
		ByteBuffer buffer = BufferUtils.createByteBuffer(4);
		buffer.put(0, (byte) 255);
		buffer.put(1, (byte) 255);
		buffer.put(2, (byte) 255);
		buffer.put(3, (byte) 255);
		emptyTexture = new Texture(buffer, 1, 1);
	}
	
	public static Texture getEmptyTexture() {
		return emptyTexture;
	}
}
