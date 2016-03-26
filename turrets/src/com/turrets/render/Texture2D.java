package com.turrets.render;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class Texture2D {
	
	private static final String TAG="Texture2D";
	
	public String getFilename() {
		return mFilename;
	}

	public int getHandle() {
		return mHandle;
	}

	private String mFilename;
	private int mHandle;
	
	public int loadTexture(Context context, String filename) {
		final int[] textureHandle = new int[1];

		GLES20.glGenTextures(1, textureHandle, 0);
		mFilename = filename;
		
		if (textureHandle[0] != 0) {
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inScaled = false; // No pre-scaling

			InputStream stream = null;

			try {
				
				stream = context.getAssets().open(filename);
				
				Log.d(TAG, "Decode bitmap " + filename);
				// Read in the resource
				final Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);

				Log.d(TAG, "Bind Texture handld");
				// Bind to the texture in OpenGL
				GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

				// Set filtering
				GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
				GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

				Log.d(TAG, "Load image to texture");
				// Load the bitmap into the bound texture.
				GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

				Log.d(TAG, "Done");
				// Recycle the bitmap, since its data has been loaded into
				// OpenGL.
				bitmap.recycle();
			} catch (IOException ex) {
				Log.e(TAG, "Error reading " + filename);
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
		}

		if (textureHandle[0] == 0) {
			throw new RuntimeException("Error loading texture.");
		}
		mHandle = textureHandle[0];
		return mHandle;
	}

}
