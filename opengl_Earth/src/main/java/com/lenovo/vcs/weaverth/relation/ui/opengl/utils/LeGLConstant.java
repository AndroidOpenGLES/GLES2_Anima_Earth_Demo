package com.lenovo.vcs.weaverth.relation.ui.opengl.utils;

/**
 * 近平面的可视区间为2 ~ -22
 * 
 * @author xiaxl1
 *
 */
public class LeGLConstant {

	private static final String TAG = LeGLConstant.class.getSimpleName();

	/**
	 * near far
	 */
	public static final float PROJECTION_NEAR = 4;
	public static final float PROJECTION_FAR = 100;

	/**
	 * camera position
	 */
	public static final float EYE_X = 0f;
	public static final float EYE_Y = 0f;
	public static final float EYE_Z = 8f;
	public static final float VIEW_CENTER_X = 0f;
	public static final float VIEW_CENTER_Y = 0f;
	public static final float VIEW_CENTER_Z = 0f;

	/**
	 * 动画时间
	 */
	public static final int LEGL_ANIMATION_TIME = 3000;

}
