package com.lenovo.vcs.weaverth.relation.ui.opengl.scene;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.lenovo.vcs.weaverth.relation.ui.opengl.R;
import com.lenovo.vcs.weaverth.relation.ui.opengl.sprite.LeGLBaseBallSprite;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLConstant;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLMatrixState;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLTextureFactory;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.Log;

/**
 * 场景的渲染器
 * 
 * @author xiaxl1
 *
 */
public class LeGLSceneRenderer implements GLSurfaceView.Renderer {

	private static final String TAG = LeGLSceneRenderer.class.getSimpleName();

	public LeGlBaseScene mGLSurfaceView;

	public LeGLSceneRenderer(LeGlBaseScene glSurfaceView) {
		this.mGLSurfaceView = glSurfaceView;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		//
		long drawTime = System.currentTimeMillis();
		// 清除深度缓冲与颜色缓冲
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		//
        //开启混合   
        gl.glEnable(GL10.GL_BLEND); 
        gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
		//
		mGLSurfaceView.drawSelf(drawTime);

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		// 设置屏幕背景色RGBA
		//GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		// 启用深度测试
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		// 设置为打开背面剪裁
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		// 初始化变换矩阵
		LeGLMatrixState.setInitStack();

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		// viewPort
		GLES20.glViewport(0, 0, width, height);
		//
		float ratio = (float) width / height;
		// set project frustum mestrix
		LeGLMatrixState.setProjectOrtho(-ratio, ratio, -1, 1,
				LeGLConstant.PROJECTION_NEAR, LeGLConstant.PROJECTION_FAR);
//		LeGLMatrixState.setProjectFrustum(-ratio, ratio, -1, 1,
//				LeGLConstant.PROJECTION_NEAR, LeGLConstant.PROJECTION_FAR);
		
		// camera
		LeGLMatrixState.setCamera(LeGLConstant.EYE_X, LeGLConstant.EYE_Y,
				LeGLConstant.EYE_Z, LeGLConstant.VIEW_CENTER_X,
				LeGLConstant.VIEW_CENTER_Y, LeGLConstant.VIEW_CENTER_Z, 0f, 1f,
				0f);

	}

}
