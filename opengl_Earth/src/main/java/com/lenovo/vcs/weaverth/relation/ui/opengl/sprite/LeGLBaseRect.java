package com.lenovo.vcs.weaverth.relation.ui.opengl.sprite;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

import com.lenovo.vcs.weaverth.relation.ui.opengl.scene.LeGlBaseScene;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLMatrixState;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLShaderUtil;

public class LeGLBaseRect extends LeGLBaseSprite {
	int mProgram;// 自定义渲染管线着色器程序id
	int muMVPMatrixHandle;// 总变换矩阵引用
	int maPositionHandle; // 顶点位置属性引用
	int maTexCoorHandle; // 顶点纹理坐标属性引用

	String mVertexShader;// 顶点着色器
	String mFragmentShader;// 片元着色器

	private FloatBuffer mVertexBuffer;// 顶点坐标数据缓冲
	FloatBuffer mTexCoorBuffer;// 顶点纹理坐标数据缓冲
	int vCount;// 顶点数量

	float width;
	float height;

	float sEnd;// 按钮右下角的s、t值
	float tEnd;

	public LeGLBaseRect(LeGlBaseScene mv, float width, float height, // 矩形的宽高
			float sEnd, float tEnd // 右下角的s、t值
	) {
		super(mv);

		this.width = width;
		this.height = height;
		this.sEnd = sEnd;
		this.tEnd = tEnd;
		initVertexData();
		initShader(mv);

	}

	// 初始化顶点坐标与着色数据的方法
	public void initVertexData() {
		// 顶点个数
		vCount = 6;
		float vertices[] = { -width / 2.0f, height / 2.0f, 0, -width / 2.0f,
				-height / 2.0f, 0, width / 2.0f, height / 2.0f, 0,

				-width / 2.0f, -height / 2.0f, 0, width / 2.0f, -height / 2.0f,
				0, width / 2.0f, height / 2.0f, 0, };
		// 创建顶点坐标数据缓冲
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);

		float texCoor[] = new float[]// 纹理坐标
		{ 0, 0, 0, tEnd, sEnd, 0, 0, tEnd, sEnd, tEnd, sEnd, 0 };
		// 创建顶点纹理坐标数据缓冲
		ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		mTexCoorBuffer = cbb.asFloatBuffer();
		mTexCoorBuffer.put(texCoor);
		mTexCoorBuffer.position(0);
	}

	private void initShader(LeGlBaseScene mv) {
		// 加载顶点着色器的脚本内容
		mVertexShader = LeGLShaderUtil.loadFromAssetsFile("vertex_tex.sh",
				mv.getResources());
		// 加载片元着色器的脚本内容
		mFragmentShader = LeGLShaderUtil.loadFromAssetsFile("frag_tex.sh",
				mv.getResources());
		// 基于顶点着色器与片元着色器创建程序
		mProgram = LeGLShaderUtil.createProgram(mVertexShader, mFragmentShader);
		// 获取程序中顶点位置属性引用
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		// 获取程序中总变换矩阵id
		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		// 获取程序中顶点纹理坐标属性引用
		maTexCoorHandle = GLES20.glGetAttribLocation(mProgram, "aTexCoor");
	}

	public void drawSelf(int texId, long drawTime) {
		super.drawSelf(texId, drawTime);
		// 制定使用某套着色器程序
		GLES20.glUseProgram(mProgram);
		// 将最终变换矩阵传入着色器程序
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				LeGLMatrixState.getFinalMatrix(), 0);
		// 将顶点纹理坐标数据传入渲染管线
		GLES20.glVertexAttribPointer(maTexCoorHandle, 2, GLES20.GL_FLOAT,
				false, 2 * 4, mTexCoorBuffer);
		// 启用顶点位置数据
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		GLES20.glEnableVertexAttribArray(maTexCoorHandle);
		// 绑定纹理
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);

		// 将顶点法向量数据传入渲染管线
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
				false, 3 * 4, mVertexBuffer);
		// 绘制矩形
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
	}

}