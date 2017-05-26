package com.lenovo.vcs.weaverth.relation.ui.opengl.sprite;

import com.lenovo.vcs.weaverth.relation.ui.opengl.animation.LeGLAnimation;
import com.lenovo.vcs.weaverth.relation.ui.opengl.animation.LeGLAnimation.LeGLAnimationListener;
import com.lenovo.vcs.weaverth.relation.ui.opengl.scene.LeGlBaseScene;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.Log;

/**
 * 基础的精灵类
 * 
 * @author xiaxl1
 */
public class LeGLBaseSprite implements LeGLAnimationListener {
	private static final String TAG = LeGLBaseSprite.class.getSimpleName();

	// 层移动动画的回调
	private LeGLSpriteAnimationListener mLeGLSpriteAnimationListener = null;

	// 接口定义
	public interface LeGLSpriteAnimationListener {
		/**
		 * 动画开始
		 */
		void onSpriteAnimaStart();

		/**
		 * 进度
		 */
		void onSpriteAnimaProgress(float percent);

		/**
		 * 动画结束
		 */
		void onSpriteAnimaFinish();
	}

	@Override
	public void onAnimaStart() {
		//
		if (mLeGLSpriteAnimationListener != null) {
			mLeGLSpriteAnimationListener.onSpriteAnimaStart();
		}
	}

	@Override
	public void onAnimaProgress(float percent) {
		//
		if (mLeGLSpriteAnimationListener != null) {
			mLeGLSpriteAnimationListener.onSpriteAnimaProgress(percent);
		}
	}

	@Override
	public void onAnimaFinish() {
		//
		if (mLeGLAnimation != null) {
			mLeGLAnimation = null;
		}

		if (mLeGLSpriteAnimationListener != null) {
			mLeGLSpriteAnimationListener.onSpriteAnimaFinish();
		}

	}

	// ---精灵的动画---
	private LeGLAnimation mLeGLAnimation = null;

	public LeGLAnimation getLeGLAnimation() {
		return mLeGLAnimation;
	}

	/**
	 * 开始缩放动画
	 * 
	 * @param fromScale
	 * @param toScale
	 * @param animaDuration
	 */
	public void startScaleAnimation(float fromScale, float toScale,
			int animaDuration, LeGLSpriteAnimationListener animationListener) {

		if (mLeGLAnimation == null) {
			//
			this.mLeGLSpriteAnimationListener = animationListener;
			// ------
			// 创建动画类
			mLeGLAnimation = new LeGLAnimation();
			// 动画完成状态的回调
			mLeGLAnimation.setAnimationListener(this);
			// 动画的开始与结束状态
			mLeGLAnimation.setAnimaScale(fromScale, toScale);
			// 动画时间
			mLeGLAnimation.setAnimaDuration(animaDuration);
			// 开始动画
			mLeGLAnimation.startAnimation();

			// ---请求界面刷新---
			this.getLeGlBaseScene().requestRender();
		}

	}

	/**
	 * 开始缩放动画
	 * 
	 * @param fromScale
	 * @param toScale
	 * @param animaDuration
	 */
	public void startRotateXYAnimation(float fromAngleX, float toAngleX,
			float fromAngleY, float toAngleY, int animaDuration,
			LeGLSpriteAnimationListener animationListener) {

		if (mLeGLAnimation == null) {
			//
			this.mLeGLSpriteAnimationListener = animationListener;
			// ------
			// 创建动画类
			mLeGLAnimation = new LeGLAnimation();
			// 动画完成状态的回调
			mLeGLAnimation.setAnimationListener(this);
			// 动画的开始与结束状态
			mLeGLAnimation.setAnimaRotate(fromAngleX, toAngleX, fromAngleY,
					toAngleY);
			// 动画时间
			mLeGLAnimation.setAnimaDuration(animaDuration);
			// 开始动画
			mLeGLAnimation.startAnimation();

			// ---请求界面刷新---
			this.getLeGlBaseScene().requestRender();
		}

	}

	/**
	 * 开始Alpha动画
	 * 
	 * @param fromAlpha
	 * @param toAlpha
	 * @param animaDuration
	 */
	public void startAlphaAnimation(float fromAlpha, float toAlpha,
			int animaDuration, LeGLSpriteAnimationListener animationListener) {

		if (mLeGLSpriteAnimationListener == null) {
			//
			this.mLeGLSpriteAnimationListener = animationListener;
			// ------
			// 创建动画类
			mLeGLAnimation = new LeGLAnimation();
			// 动画完成状态的回调
			mLeGLAnimation.setAnimationListener(this);
			// 动画的开始与结束状态
			mLeGLAnimation.setAnimaAlpha(fromAlpha, toAlpha);
			// 动画时间
			mLeGLAnimation.setAnimaDuration(animaDuration);
			// 开始动画
			mLeGLAnimation.startAnimation();

			// ---请求界面刷新---
			this.getLeGlBaseScene().requestRender();
		}

	}

	// 精灵缩放大小
	private float mLeGLSpriteScale = 1;
	// 精灵的alpha数值
	private float mLeGLSpriteAlpha = 1;
	//
	private float mLeGLSpriteAngleX = 0;
	private float mLeGLSpriteAngleY = 0;

	private LeGlBaseScene leGlBaseScene = null;

	public LeGLBaseSprite(LeGlBaseScene scene) {
		this.leGlBaseScene = scene;

	}

	public LeGlBaseScene getLeGlBaseScene() {
		return leGlBaseScene;
	}

	/**
	 * 绘制方法
	 * 
	 * @param drawTime
	 */
	public void drawSelf(int texId, long drawTime) {

		// ---运行动画---
		if (mLeGLAnimation != null) {
			// 运行动画
			mLeGLAnimation.runAnimation(drawTime);
			// 请求刷新界面
			this.getLeGlBaseScene().requestRender();
		}

		// ---设置画笔Alpha动画数据---
		if (mLeGLAnimation != null && mLeGLAnimation.isAlphaAnima()) {
			float alpha = mLeGLAnimation.getCurrentAlpha();
			this.setLeGLSpriteAlpha(alpha);
		}
		// ---设置Scale动画数据---
		if (mLeGLAnimation != null && mLeGLAnimation.isScaleAnima()) {
			float scale = mLeGLAnimation.getCurrentScale();
			Log.d(TAG, "scale: " + scale);
			this.setLeGLSpriteScale(scale);
		}
		// ---设置Rotate动画数据---
		if (mLeGLAnimation != null && mLeGLAnimation.isRotateAnima()) {
			float angleX = mLeGLAnimation.getCurrentAngleX();
			float angleY = mLeGLAnimation.getCurrentAngleY();
			Log.d(TAG, "angleX: " + angleX);
			Log.d(TAG, "angleY: " + angleY);
			this.setLeGLSpriteAngleXY(angleX, angleY);
		}

	}

	public float getLeGLSpriteScale() {
		return mLeGLSpriteScale;
	}

	public void setLeGLSpriteScale(float mLeGLSpriteScale) {
		this.mLeGLSpriteScale = mLeGLSpriteScale;
	}

	public float getLeGLSpriteAlpha() {
		return mLeGLSpriteAlpha;
	}

	public void setLeGLSpriteAlpha(float mLeGLSpriteAlpha) {
		this.mLeGLSpriteAlpha = mLeGLSpriteAlpha;
	}

	public float getLeGLSpriteAngleX() {
		return mLeGLSpriteAngleX;
	}

	public float getLeGLSpriteAngleY() {
		return mLeGLSpriteAngleY;
	}

	public void setLeGLSpriteAngleXY(float mLeGLSpriteAngleX,
			float mLeGLSpriteAngleY) {
		this.mLeGLSpriteAngleX = mLeGLSpriteAngleX;
		this.mLeGLSpriteAngleY = mLeGLSpriteAngleY;
	}

}
