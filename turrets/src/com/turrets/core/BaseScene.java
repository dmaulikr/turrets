package com.turrets.core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.view.MotionEvent;

public abstract class BaseScene implements Renderer {

	private GLSurfaceView mView;
	
	protected GLSurfaceView getView(){
		return mView;
	}
	
	protected Context getContext(){
		return getView().getContext();
	}
	
	public BaseScene(GLSurfaceView view) {
		mView = view;
	}

	public abstract boolean onTouchEvent(MotionEvent e);
}
