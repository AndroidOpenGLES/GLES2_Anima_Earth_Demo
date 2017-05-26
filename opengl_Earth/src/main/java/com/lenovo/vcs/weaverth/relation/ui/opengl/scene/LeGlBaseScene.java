package com.lenovo.vcs.weaverth.relation.ui.opengl.scene;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * 场景的基础类
 * 
 * @author xiaxl
 *
 */
public abstract class LeGlBaseScene extends GLSurfaceView {

	/**
	 * LeBaseGLSurfaceView
	 * 
	 * @param context
	 */
	public LeGlBaseScene(Context context) {
		super(context);
		//
		initES2();
	}

	/**
	 * LeBaseGLSurfaceView
	 * 
	 * @param context
	 * @param attrs
	 */
	public LeGlBaseScene(Context context, AttributeSet attrs) {
		super(context, attrs);
		//
		initES2();
	}

	/**
	 * LeBaseGLSurfaceView
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public LeGlBaseScene(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		//
		initES2();
	}

	/**
	 * init es 2.0
	 */
	private void initES2() {
		// 使用OpenGL ES 2.0
		setEGLContextClientVersion(2);
	}

	/**
	 * 绘制方法
	 * 
	 * @param drawTime
	 */
	public void drawSelf(long drawTime) {

	}

	/**
	 * 
	 */
	@Override
	public void onResume() {
		super.onResume();

	}

	/**
	 * 
	 */
	@Override
	public void onPause() {
		super.onPause();

	}

}
