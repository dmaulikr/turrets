package com.turrets.render;

import android.opengl.GLES20;

public class Mesh {

	private static final String TAG = "Mesh";

	private Geometry mGeometry;
	
	private Shaders mShader;
	private Texture2D mTexture;
	private Camera mCamera;
	private int mPositionHandle;
	private int mColorHandle;

	// number of coordinates per vertex in this array
	static final int VERTEX_STRIDE = 5;
	
	//vertex dec position, texturecoord, textureid
	static float squareCoords[] = { 
			-1.0f, 1.0f, 3.0f, 0, 0, // top left
			-1.0f, -1.0f, 3.0f, 0, 1, // bottom left
			1.0f, -1.0f, 3.0f, 1, 1, // bottom right
			1.0f, 1.0f, 3.0f, 1, 0 }; // top right

	// Set color with red, green, blue and alpha (opacity) values
	float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

	private short indices[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

	public Mesh(Shaders shader, Texture2D texture, Camera camera) {
		mShader = shader;
		mTexture = texture;
		mCamera = camera;
		
		mGeometry = new Geometry();
		mGeometry.Create(squareCoords, indices, VERTEX_STRIDE);

	}

	public void draw() {
		// Add program to OpenGL ES environment
		mShader.useProgram();

		int mTextureUniformHandle = mShader.getUniform("u_Texture");
		// Log.d(TAG, "7");
		// Set the active texture unit to texture unit 0.
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

		// Log.d(TAG, "8");
		// Bind the texture to this unit.
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture.getHandle());

		// Tell the texture uniform sampler to use this texture in the shader by
		// binding to texture unit 0.
		GLES20.glUniform1i(mTextureUniformHandle, 0);

		// get handle to vertex shader's vPosition member
		mPositionHandle = mShader.getAttribute("a_Position");

		// Enable a handle to the triangle vertices
		GLES20.glEnableVertexAttribArray(mPositionHandle);

		// Prepare the triangle coordinate data
		GLES20.glVertexAttribPointer(mPositionHandle, 
				3, GLES20.GL_FLOAT, false, mGeometry.getVertByteStride(),
				mGeometry.getVerts().position(0));

		// get handle to fragment shader's vColor member
		mColorHandle = mShader.getUniform("u_Color");

		int mTextureCoordinateHandle = mShader.getAttribute("a_TexCoordinate");

		// Enable a handle to the triangle vertices
		GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

		// Log.d(TAG, "6");
		// Prepare the triangle coordinate data
		GLES20.glVertexAttribPointer(mTextureCoordinateHandle,
				2, GLES20.GL_FLOAT, false, mGeometry.getVertByteStride(),
				mGeometry.getVerts().position(3));

		int wvpHandle =  mShader.getUniform("u_MVPMatrix");
		GLES20.glUniformMatrix4fv(wvpHandle, 1, false, mCamera.getWvp());
		
		// Set color for drawing the triangle
		GLES20.glUniform4fv(mColorHandle, 1, color, 0);
		// Log.d(TAG, "9");
		GLES20.glDrawElements(GLES20.GL_TRIANGLES,
				mGeometry.getIndices().limit(),
				GLES20.GL_UNSIGNED_SHORT, mGeometry.getIndices());

		// Log.d(TAG, "10");
		// Disable vertex array
		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureCoordinateHandle);
	}

}
