package com.lenovo.vcs.weaverth.relation.ui.opengl.animation;

/**
 * 
 * 
 * @author xiaxl1
 *
 */
public class LeGLAnimation {

	// --------接口相关开始------
	/**
	 * 动画的开始与结束回调
	 * 
	 * @author xiaxl1
	 *
	 */
	public interface LeGLAnimationListener {
		/**
		 * 动画开始
		 */
		void onAnimaStart();

		/**
		 * 进度
		 */
		void onAnimaProgress(float percent);

		/**
		 * 动画结束
		 */
		void onAnimaFinish();
	}

	LeGLAnimationListener mSpriteAnimationListener = null;

	/**
	 * 
	 * @param spriteAnimationListener
	 */
	public void setAnimationListener(
			LeGLAnimationListener spriteAnimationListener) {
		mSpriteAnimationListener = spriteAnimationListener;
	}

	// --------接口相关结束-------

	// 动画结束
	private boolean isAnimaRuning = false;
	// 动画时长
	private int mAnimaDuration = 1000;
	// 动画开始时间
	private long mAnimaStartTime = 0;

	// ---alpha---
	private boolean isAlphaAnima = false;
	private float fromAlpha = 0;
	private float toAlpha = 0;
	private float currentAlpha = 0;

	// ---scale---
	private boolean isScaleAnima = false;
	private float fromScale = 0;
	private float toScale = 0;
	private float currentScale = 0;

	// ---rotate---
	private boolean isRotateAnima = false;
	private float fromAngleX = 0;
	private float toAngleX = 0;
	private float fromAngleY = 0;
	private float toAngleY = 0;
	private float currentAngleY = 0;
	private float currentAngleX = 0;

	/**
	 * 
	 * @param fromAlpha
	 * @param toAlpha
	 */
	public void setAnimaAlpha(float fromAlpha, float toAlpha) {
		isAlphaAnima = true;
		this.fromAlpha = fromAlpha;
		this.toAlpha = toAlpha;
		//
		this.currentAlpha = fromAlpha;
	}

	/**
	 * 
	 * @param fromScale
	 * @param toScale
	 */
	public void setAnimaScale(float fromScale, float toScale) {
		isScaleAnima = true;
		this.fromScale = fromScale;
		this.toScale = toScale;
		//
		this.currentScale = fromScale;

	}

	/**
	 * 
	 * @param fromScale
	 * @param toScale
	 */
	public void setAnimaRotate(float fromAngleX, float toAngleX,
			float fromAngleY, float toAngleY) {
		isRotateAnima = true;
		this.fromAngleX = fromAngleX;
		this.toAngleX = toAngleX;
		this.fromAngleY = fromAngleY;
		this.toAngleY = toAngleY;
		//
		this.currentAngleX = fromAngleX;
		this.currentAngleY = fromAngleY;
	}

	/**
	 * 开始动画
	 */
	public void startAnimation() {
		mAnimaStartTime = -1;
		isAnimaRuning = true;
	}

	/**
	 * 在ondraw中调用该方法.通过在ondraw中不断循环调用
	 * 
	 * @param drawTime
	 */
	public void runAnimation(long drawTime) {
		if (!isAnimaRuning) {
			return;
		}
		//
		if (mAnimaStartTime == -1) {
			mAnimaStartTime = drawTime;
			isAnimaRuning = true;
			// 回调接口,动画开始
			if (mSpriteAnimationListener != null) {
				mSpriteAnimationListener.onAnimaStart();
			}
		}
		//
		long runTime = drawTime - mAnimaStartTime;
		//
		float percent = (float) runTime / mAnimaDuration;
		//
		if (percent > 1) {

			/**
			 * 动画结束
			 */
			percent = 1;

			/**
			 * 更改动画帧数据
			 */
			changeFrameData(percent);

			// 动画结束
			isAnimaRuning = false;
			//
			isAlphaAnima = false;
			//
			isScaleAnima = false;
			//
			isRotateAnima = false;
			// 回调接口,动画结束
			if (mSpriteAnimationListener != null) {
				mSpriteAnimationListener.onAnimaFinish();
			}

			return;
		}

		changeFrameData(percent);

	}

	/**
	 * 更改动画帧数据
	 * 
	 * @param percent
	 */
	private void changeFrameData(float percent) {

		/**
		 * 进度
		 */
		if (mSpriteAnimationListener != null) {
			mSpriteAnimationListener.onAnimaProgress(percent);
		}

		/**
		 * 动画帧
		 */

		if (isAlphaAnima) {
			currentAlpha = fromAlpha + percent * (toAlpha - fromAlpha);
		}
		//
		if (isScaleAnima) {
			currentScale = fromScale + percent * (toScale - fromScale);
		}
		//
		if (isRotateAnima) {
			currentAngleX = fromAngleX + percent * (toAngleX - fromAngleX);
			currentAngleY = fromAngleY + percent * (toAngleY - fromAngleY);

		}
	}

	/**
	 * 设置动画的持续时间,默认为1秒钟
	 * 
	 * @param mAnimaDuration
	 */
	public void setAnimaDuration(int mAnimaDuration) {
		this.mAnimaDuration = mAnimaDuration;
	}

	/**
	 * 获取当前的Alpha数值
	 * 
	 * @return
	 */
	public float getCurrentAlpha() {
		return currentAlpha;
	}

	/**
	 * 获取当前的Scale大小
	 * 
	 * @return
	 */
	public float getCurrentScale() {
		return currentScale;
	}

	/**
	 * 获取当前Y的旋转角度
	 * 
	 * @return
	 */
	public float getCurrentAngleX() {
		return currentAngleX;
	}

	/**
	 * 获取当前Y的旋转角度
	 * 
	 * @return
	 */
	public float getCurrentAngleY() {
		return currentAngleY;
	}

	/**
	 * 动画的运行状态
	 * 
	 * @return
	 */
	public boolean isAnimaRuning() {
		return isAnimaRuning;
	}

	/**
	 * 是Alpha动画
	 * 
	 * @return
	 */
	public boolean isAlphaAnima() {
		return isAlphaAnima;
	}

	/**
	 * 是缩放动画
	 * 
	 * @return
	 */
	public boolean isScaleAnima() {
		return isScaleAnima;
	}

	public boolean isRotateAnima() {
		return isRotateAnima;
	}

}
