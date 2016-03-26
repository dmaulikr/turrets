package com.turrets.render;

import java.nio.FloatBuffer;

import com.jme3.math.FastMath;
import com.jme3.math.Matrix4f;
import com.jme3.math.Vector3f;
import com.jme3.util.TempVars;

import android.util.Log;

public class Camera {
	private Matrix4f mView = new Matrix4f();
	private Matrix4f mProjection = new Matrix4f();
	private Matrix4f mWorld = new Matrix4f();
	private Matrix4f mWvp = new Matrix4f();
	private boolean mWvpDirty = true;
	
	/**
	 * Screen width
	 */
	private float mWidth = 100;
	
	/**
	 * Screen height
	 */
	private float mHeight = 100;
	
	/**
	 * Field of view in degrees 
	 */
	private float mFov = 45.0f;
	
	/**
	 * The position of the camera  in world space
	 */
	private Vector3f mPosition = Vector3f.ZERO;
	
	/**
	 * The angles of the world transformation in degrees. 
	 */
	private Vector3f mAngles = new Vector3f();
	
	public Camera() {
	}
	
	/**
	 * Sets the screen width and height. This is needed to calculate the 
	 * projection matrix.
	 * @param width
	 * @param height
	 */
	public void setScreenSize(float width, float height) {
		mWidth = width;
		mHeight = height;
		
		mWvpDirty = true;
	}
	
	/**
	 * This is passed in to the shader for rendering. 
	 * @return
	 */
	public FloatBuffer getWvp() {
		if(mWvpDirty) 
		{
			calculateWVP();
			mWvpDirty = false;
		}
		return mWvp.toFloatBuffer();
	}
	
	private void calculateWVP() {
		
		//calculate world matrix
		TempVars rotationAngles = TempVars.get();
		rotationAngles.fWdU[0] = mAngles.x;
		rotationAngles.fWdU[1] = mAngles.y;
		rotationAngles.fWdU[2] = mAngles.z;
		
		mWorld.loadIdentity();
		mWorld.setInverseRotationRadians(rotationAngles.fWdU);
		mWorld.setTranslation(mPosition);
		mWorld.transposeLocal();
		
		rotationAngles.release();
		
		Log.d("CAMERA", "Position " + mPosition.x + "," + mPosition.y+ "," + mPosition.z );
		
		//calculate view matrix
		mView.loadIdentity();
		mView.fromFrame(Vector3f.ZERO,
				Vector3f.UNIT_Z, Vector3f.UNIT_Y, Vector3f.UNIT_X.negate());
		
		//calculate projection matrix
		float aspect = mWidth / mHeight;
		mProjection.loadIdentity();
		mProjection.fromFov(mFov, aspect, 1.0f, 1000.0f);
		
		mWvp.loadIdentity();
		mWvp.multLocal(mWorld);
		mWvp.multLocal(mView);
		mWvp.multLocal(mProjection);
		
	}
	
	public void move(Vector3f offset) {
		mPosition.addLocal(offset);
		Log.d("CAMERA", "OFFSET " + offset.x + "," + offset.y+ "," + offset.z );
		mWvpDirty = true;
		
	}
	
	public void look(float xAngle, float yAngle) {
		mAngles.x += xAngle;
		mAngles.y += yAngle;
		
		Log.d("CAMERA", "Angle " + mAngles.x + "," + mAngles.y+ "," + mAngles.z );
		mWvpDirty = true;
		
	}
}
