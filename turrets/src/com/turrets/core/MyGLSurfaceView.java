package com.turrets.core;


import com.turrets.scenes.DefaultScene;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLSurfaceView  extends GLSurfaceView {
	
    private final BaseScene mScene;

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mScene = new DefaultScene(this);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mScene);
        
    }
   
    @Override
    public boolean onTouchEvent(MotionEvent e) {
    	return mScene.onTouchEvent(e);
    }
}
