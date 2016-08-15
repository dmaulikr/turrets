package com.turrets.scenes;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.turrets.core.BaseScene;
import com.turrets.input.GestureManager;
import com.turrets.render.Camera;
import com.turrets.render.Shaders;
import com.turrets.render.Square;
import com.turrets.render.Texture2D;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

public class DefaultScene extends BaseScene {

    private static final String TAG = "DefaultScene";

    Square mTest;
    Camera mCamera = new Camera();
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    public DefaultScene(GLSurfaceView view) {
	super(view);

	Serializer serializer = new Persister();
	try {
	    getContext().getAssets().open("test.xml");
	} catch (IOException e) {
	    Log.e(TAG, "error reading test.xml");
	}

	// Example example = serializer.read(Example.class, source);

    }

    public void onDrawFrame(GL10 unused) {
	// Redraw background color
	GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
	mTest.draw();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
	GLES20.glViewport(0, 0, width, height);
	mCamera.setScreenSize(width, height);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	// Set the background frame color
	GLES20.glClearColor(0.0f, 0.5f, 0.2f, 1.0f);

	Shaders shader = new Shaders();
	shader.loadShader(getContext(), "default");

	Texture2D texture = new Texture2D();
	texture.loadTexture(getContext(), "font-default.png");

	mTest = new Square(shader, texture, mCamera);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
	// MotionEvent reports input details from the touch screen
	// and other input controls. In this case, you are only
	// interested in events where the touch position changed.

	float x = e.getX();
	float y = e.getY();
	float scaleCameraSpeed = .001f;
	
	Vector2f point = new Vector2f(e.getX(), e.getY());
	
	//GestureManager.get().UpdateStrokeInfo(point, event);
	switch (e.getAction()) {
	case MotionEvent.ACTION_DOWN:

	    mPreviousX = x;
	    mPreviousY = y;
	    break;

	case MotionEvent.ACTION_MOVE:

	    float dx = x - mPreviousX;
	    float dy = y - mPreviousY;

	    mCamera.move(new Vector3f(0, 0, -scaleCameraSpeed * dy));

	    getView().requestRender();

	    Log.d(TAG, "dx: " + dx + " dy: " + dy);

	    mPreviousX = x;
	    mPreviousY = y;

	    break;
	case MotionEvent.ACTION_CANCEL:
	    break;

	}

	return true;
    }

}
