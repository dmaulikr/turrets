package com.turrets.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.util.Log;

public class Geometry {

	public static final int FLOAT_SIZE = 4;
	public static final int SHORT_SIZE = 2;
	
	private FloatBuffer mVerts;
	private ShortBuffer mIndices;
	private int mVertByteStride;
	
	public static int getFloatSize() {
		return FLOAT_SIZE;
	}

	public static int getShortSize() {
		return SHORT_SIZE;
	}

	public ShortBuffer getIndices() {
		return mIndices;
	}
	
	public FloatBuffer getVerts() {
		return mVerts;
	}

	public int getVertByteStride() {
		return mVertByteStride;
	}

	public void Create(float[] verts, short[] indices, final int vertexStride) {
		
		mVertByteStride = vertexStride * FLOAT_SIZE;
		
		// initialize vertex byte buffer for shape coordinates
		ByteBuffer bb = ByteBuffer.allocateDirect(
				verts.length * FLOAT_SIZE);
		bb.order(ByteOrder.nativeOrder());
		mVerts = bb.asFloatBuffer();
		mVerts.put(verts);
		mVerts.position(0);

		// initialize byte buffer for the draw list
		ByteBuffer dlb = ByteBuffer.allocateDirect(
				// (# of coordinate values * 2 bytes per short)
				indices.length * SHORT_SIZE);
		dlb.order(ByteOrder.nativeOrder());
		mIndices = dlb.asShortBuffer();
		mIndices.put(indices);
		mIndices.position(0);
		Log.d("", "indices: " + mIndices.limit());
		Log.d("", "byte stride: " + mVertByteStride);
	}

}
