package com.turrets.render;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public class Shaders {
	
	private static final String TAG="Shaders";
	private String mFilename;
	private int mProgram;
	public static final int INVALID_ID = -1;
	
	public void loadShader(String vertexShaderCode, String fragmentShaderCode) {
		
		int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

		// create empty OpenGL ES Program
		mProgram = GLES20.glCreateProgram();

		// add the vertex shader to program
		GLES20.glAttachShader(mProgram, vertexShader);

		// add the fragment shader to program
		GLES20.glAttachShader(mProgram, fragmentShader);

		// creates OpenGL ES program executables
		GLES20.glLinkProgram(mProgram);
	}
	
	public void loadShader(Context context, String filename) {
		mFilename = filename;
		
		String vertexCode = readAsset(context, filename + ".vert");
		String fragmentCode = readAsset(context, filename + ".frag");
		
		loadShader(vertexCode, fragmentCode);
	}
	
	public void useProgram() {
		
		// Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);
	}
	
	public int getAttribute(String attribute) {
		
		int location = GLES20.glGetAttribLocation(mProgram, attribute);
		if(location == INVALID_ID)
			Log.e(TAG, "Can not find attribute " + attribute + " in shader " + mFilename);
		return location;
	}
	
	public int getUniform(String uniform) {
		
		int location = GLES20.glGetUniformLocation(mProgram, uniform);
		if(location == INVALID_ID)
			Log.e(TAG, "Can not find uniform " + uniform + " in shader " + mFilename);
		return location;
	}
	
	private static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
	
	private static String readAsset(Context context, String filename) {
		final StringBuilder body = new StringBuilder();
		
		InputStream stream = null;
		try {
			stream = context.getAssets().open(filename);
			
			final InputStreamReader inputStreamReader = new InputStreamReader(stream);
	
			final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	
			String nextLine;
	
			while ((nextLine = bufferedReader.readLine()) != null) {
				body.append(nextLine);
				body.append('\n');
			}
		
		} catch (IOException e) {
			Log.e(TAG, "Error reading file " + filename);
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		finally{
			if(stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
		return body.toString();
	}
}
