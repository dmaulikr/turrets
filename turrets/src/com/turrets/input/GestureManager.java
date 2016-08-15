package com.turrets.input;

import com.jme3.math.Vector2f;

import android.util.SparseArray;
import android.view.MotionEvent;

public class GestureManager {

    private static GestureManager mInstance;
    
    public static GestureManager get() {
	if(mInstance == null) mInstance = new GestureManager();
	return mInstance;
    }
    
    private GestureManager() { }
    
    public SparseArray<Stroke> mStroke = new SparseArray<Stroke>();
    
    public void UpdateStrokeInfo(Vector2f point, MotionEvent event) {
	
    }
}
