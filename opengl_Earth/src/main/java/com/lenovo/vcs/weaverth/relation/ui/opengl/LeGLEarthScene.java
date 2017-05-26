package com.lenovo.vcs.weaverth.relation.ui.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.lenovo.vcs.weaverth.relation.ui.opengl.scene.LeGLSceneRenderer;
import com.lenovo.vcs.weaverth.relation.ui.opengl.scene.LeGlBaseScene;
import com.lenovo.vcs.weaverth.relation.ui.opengl.sprite.LeGLBallSprite;
import com.lenovo.vcs.weaverth.relation.ui.opengl.sprite.LeGLBaseSprite.LeGLSpriteAnimationListener;
import com.lenovo.vcs.weaverth.relation.ui.opengl.sprite.LeGLRect;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLConstant;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLMatrixState;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLTextureFactory;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.Log;

/*
 * GL SurfaceView
 */
public class LeGLEarthScene extends LeGlBaseScene {

	private static final String TAG = LeGLEarthScene.class.getSimpleName();

	public LeGLEarthScene(Context context) {
		super(context);

		// 初始化render
		initRender();

	}

	public LeGLEarthScene(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 初始化render
		initRender();

	}

	public LeGLEarthScene(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		// 初始化render
		initRender();

	}

	public void initRender() {
		// 初始化render
		LeGLSceneRenderer render = new LeGLSceneRenderer(this);
		this.setRenderer(render);
		// 渲染模式(被动渲染)
		this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//
		this.setSceneWidthAndHeight(this.getMeasuredWidth(),
				this.getMeasuredHeight());
	}

	@Override
	public void setRenderer(Renderer renderer) {

		// setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		// //
		// this.setZOrderOnTop(true);
		// //
		// getHolder().setFormat(PixelFormat.TRANSLUCENT);

		super.setRenderer(renderer);
	}

	@Override
	public void drawSelf(long drawTime) {
		super.drawSelf(drawTime);

		if (isInintFinsh == false) {
			//
			initUI();
			//
			initTexture();
			//
			isInintFinsh = true;

			// 旋转动画
			startEarthRotateAnimation(new LeGLSpriteAnimationListener() {

				@Override
				public void onSpriteAnimaStart() {

				}

				@Override
				public void onSpriteAnimaProgress(float percent) {

				}

				@Override
				public void onSpriteAnimaFinish() {
					// 放大动画
					startEarthScaleAnimation(null);

				}
			});

		}

		/**
		 * 绘制背景
		 */
		LeGLMatrixState.pushMatrix();
		LeGLMatrixState.translate(0, 0, -40);
		mLeBgSprite.drawSelf(textureBgId, drawTime);
		LeGLMatrixState.popMatrix();

		/**
		 * 绘制地球
		 */
		LeGLMatrixState.pushMatrix();
		// LeGLMatrixState.rotate(yAngle, 0, 1, 0);

		mLeBallSprite.drawSelf(textureEarthId, drawTime);

		LeGLMatrixState.popMatrix();

	}

	/**
	 * 数据
	 */
	// 是否初始化
	private boolean isInintFinsh = false;
	// 宽
	private float mSceneWidth = 720;
	// 高
	private float mSceneHeight = 1280;

	/**
	 * 纹理id
	 */
	// ---纹理id开始---
	// 系统分配的纹理id
	private int textureEarthId = 0;
	//
	private int textureBgId = 0;

	/**
	 * UI
	 */
	// ------
	// 球
	private LeGLBallSprite mLeBallSprite = null;
	// 背景精灵
	private LeGLRect mLeBgSprite = null;

	/**
	 * 初始化场景中的精灵实体类
	 */
	private void initUI() {

		/**
		 * ----精灵球---
		 */
		// 屏幕对角线的一半
		float diagonal_line = (float) Math.sqrt((this.getSceneWidth() / 2f)
				* (this.getSceneWidth() / 2f) + (this.getSceneHeight() / 2f)
				* (this.getSceneHeight() / 2f));
		//
		float radius = diagonal_line / this.getSceneHeight() * 2;

		Log.d(TAG, "radius: " + radius);
		// 球
		mLeBallSprite = new LeGLBallSprite(this, radius);
		//
		mLeBallSprite.setLeGLSpriteScale(0.3f);
		//

		/**
		 * ---背景矩形---
		 */
		float width = 2 * (this.getSceneWidth() / (float) this.getSceneHeight());
		float height = 2;
		int repeatCountT = 24;
		int repeatCountS = (int) (repeatCountT * (this.getSceneWidth() / (float) this
				.getSceneHeight()));
		// 背景矩形
		mLeBgSprite = new LeGLRect(this, width, height, repeatCountS,
				repeatCountT);

	}

	/**
	 * 初始化纹理
	 */
	private void initTexture() {
		// 两球之间连线的纹理图片
		textureEarthId = LeGLTextureFactory.getTextureIdByDrawableId(
				this.getContext(), R.drawable.relation_earch);
		// 背景图片
		textureBgId = LeGLTextureFactory.getTextureIdByDrawableId(
				this.getContext(), R.drawable.relation_bg);
	}

	public float getSceneWidth() {
		return mSceneWidth;
	}

	public float getSceneHeight() {
		return mSceneHeight;
	}

	public void setSceneWidthAndHeight(float mSceneWidth, float mSceneHeight) {
		this.mSceneWidth = mSceneWidth;
		this.mSceneHeight = mSceneHeight;
	}

	// private final float TOUCH_SCALE_FACTOR = 180.0f / 320;// 角度缩放比例
	// private float mPreviousY;// 上次的触控位置Y坐标
	// private float mPreviousX;// 上次的触控位置X坐标
	//
	// public float yAngle = 0;
	//
	// // 触摸事件回调方法
	// @Override
	// public boolean onTouchEvent(MotionEvent e) {
	// float y = e.getY();
	// float x = e.getX();
	// switch (e.getAction()) {
	// case MotionEvent.ACTION_MOVE:
	// float dy = y - mPreviousY;// 计算触控笔Y位移
	// float dx = x - mPreviousX;// 计算触控笔X位移
	// //
	// yAngle += dx * TOUCH_SCALE_FACTOR;// 设置绕y轴旋转角度
	// // //
	// // xAngle += dy * TOUCH_SCALE_FACTOR;//设置绕y轴旋转角度
	// }
	// mPreviousY = y;// 记录触控笔位置
	// mPreviousX = x;// 记录触控笔位置
	//
	// this.requestRender();
	//
	// return true;
	// }

	/**
	 * 开始旋转动画
	 */
	public void startEarthRotateAnimation(LeGLSpriteAnimationListener listener) {
		if (mLeBallSprite != null) {
			//
			mLeBallSprite.startRotateXYAnimation(0, 0, 0, 270,
					LeGLConstant.LEGL_ANIMATION_TIME, listener);
		}

	}

	/**
	 * 地球的放大动画
	 * 
	 * @param listener
	 */
	public void startEarthScaleAnimation(LeGLSpriteAnimationListener listener) {
		if (mLeBallSprite != null) {
			mLeBallSprite.startScaleAnimation(0.3f, 1.0f,
					LeGLConstant.LEGL_ANIMATION_TIME, listener);
		}
	}

}