package com.lenovo.vcs.weaverth.relation.ui.opengl.sprite;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

import com.lenovo.vcs.weaverth.relation.ui.opengl.scene.LeGlBaseScene;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLMatrixState;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLShaderUtil;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLVectorUtil;

/**
 * 
 * @author xiaxveliang
 *
 */
public class LeGLBaseBallSprite extends LeGLBaseSprite {
	//
	private final float UNIT_SIZE = 1f;
	//
	private int mProgram;// 自定义渲染管线着色器程序id
	private int muMVPMatrixHandle;// 总变换矩阵引用
	private int maPositionHandle; // 顶点位置属性引用
	private int maTexCoorHandle; // 顶点纹理坐标属性引用
	//
	private int muMMatrixHandle;// 位置、旋转、缩放变换矩阵
	//
	private String mVertexShader;// 顶点着色器代码脚本
	private String mFragmentShader;// 片元着色器代码脚本
	//
	private FloatBuffer vertexBuffer;// 顶点坐标数据缓冲
	private FloatBuffer textureBuffer;// 顶点纹理数据缓冲
	private FloatBuffer normalBuffer;// 顶点法向量数据缓冲

	int vCount = 0;// 顶点个数
	float size;// 尺寸
	float angdegColSpan;// 球纵向切分角度
	float angdegRowSpan;// 球横向切分角度
	float xAngle = 0;// 绕z轴旋转的角度
	float yAngle = 0;// 绕y轴旋转的角度
	float zAngle = 0;// 绕z轴旋转的角度
	int textureId;// 纹理id

	//
	LeGlBaseScene context = null;

	/**
	 * 
	 * @param context
	 * @param scale
	 * @param r
	 */
	public LeGLBaseBallSprite(LeGlBaseScene context, float r) {// 大小，半径，高度，边数，纹理id
		super(context);
		//
		this.context = context;
		//
		initShader(context);
		//
		reSetR(r, 36, 36);
	}

	/**
	 * 设置半径R
	 * 
	 * @param r
	 */
	public void reSetR(float r) {
		this.reSetR(r, 36, 36);
	}

	/**
	 * 
	 * @param r
	 * @param nCol
	 * @param nRow
	 */
	private void reSetR(float r, int nCol, int nRow) {

		// 改变尺寸
		size = UNIT_SIZE;
		r *= size;
		angdegColSpan = 360.0f / nCol;
		angdegRowSpan = 180.0f / nRow;
		vCount = 3 * nCol * nRow * 2;// 顶点个数，共有nColumn*nRow*2个三角形，每个三角形都有三个顶点
		// 坐标数据初始化
		float[] vertices = new float[vCount * 3];
		float[] textures = new float[vCount * 2];// 顶点纹理S、T坐标值数组
		// 坐标数据初始化
		int count = 0;
		int stCount = 0;
		for (float angdegCol = 0; Math.ceil(angdegCol) < 360; angdegCol += angdegColSpan) {
			double angradCol = Math.toRadians(angdegCol);// 当前列弧度
			double angradColNext = Math.toRadians(angdegCol + angdegColSpan);// 下一列弧度
			for (float angdegRow = 0; Math.ceil(angdegRow) < 180; angdegRow += angdegRowSpan) {
				double angradRow = Math.toRadians(angdegRow);// 当前行弧度
				double angradRowNext = Math
						.toRadians(angdegRow + angdegRowSpan);// 下一行弧度
				float rCircle = (float) (r * Math.sin(angradRow));// 当前行上圆的半径
				float rCircleNext = (float) (r * Math.sin(angradRowNext));// 下一行上圆的半径

				// 当前行，当前列---0
				vertices[count++] = (float) (-rCircle * Math.sin(angradCol));
				vertices[count++] = (float) (r * Math.cos(angradRow));
				vertices[count++] = (float) (-rCircle * Math.cos(angradCol));

				textures[stCount++] = (float) (angradCol / (2 * Math.PI));// st坐标
				textures[stCount++] = (float) (angradRow / Math.PI);
				// 下一行，当前列---2
				vertices[count++] = (float) (-rCircleNext * Math.sin(angradCol));
				vertices[count++] = (float) (r * Math.cos(angradRowNext));
				vertices[count++] = (float) (-rCircleNext * Math.cos(angradCol));

				textures[stCount++] = (float) (angradCol / (2 * Math.PI));// st坐标
				textures[stCount++] = (float) (angradRowNext / Math.PI);
				// 下一行，下一列---3
				vertices[count++] = (float) (-rCircleNext * Math
						.sin(angradColNext));
				vertices[count++] = (float) (r * Math.cos(angradRowNext));
				vertices[count++] = (float) (-rCircleNext * Math
						.cos(angradColNext));

				textures[stCount++] = (float) (angradColNext / (2 * Math.PI));// st坐标
				textures[stCount++] = (float) (angradRowNext / Math.PI);

				// 当前行，当前列---0
				vertices[count++] = (float) (-rCircle * Math.sin(angradCol));
				vertices[count++] = (float) (r * Math.cos(angradRow));
				vertices[count++] = (float) (-rCircle * Math.cos(angradCol));

				textures[stCount++] = (float) (angradCol / (2 * Math.PI));// st坐标
				textures[stCount++] = (float) (angradRow / Math.PI);
				// 下一行，下一列---3
				vertices[count++] = (float) (-rCircleNext * Math
						.sin(angradColNext));
				vertices[count++] = (float) (r * Math.cos(angradRowNext));
				vertices[count++] = (float) (-rCircleNext * Math
						.cos(angradColNext));

				textures[stCount++] = (float) (angradColNext / (2 * Math.PI));// st坐标
				textures[stCount++] = (float) (angradRowNext / Math.PI);
				// 当前行，下一列---1
				vertices[count++] = (float) (-rCircle * Math.sin(angradColNext));
				vertices[count++] = (float) (r * Math.cos(angradRow));
				vertices[count++] = (float) (-rCircle * Math.cos(angradColNext));

				textures[stCount++] = (float) (angradColNext / (2 * Math.PI));// st坐标
				textures[stCount++] = (float) (angradRow / Math.PI);
			}
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);// 创建顶点坐标数据缓冲
		vbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		vertexBuffer = vbb.asFloatBuffer();// 转换为float型缓冲
		vertexBuffer.put(vertices);// 向缓冲区中放入顶点坐标数据
		vertexBuffer.position(0);// 设置缓冲区起始位置
		// 法向量数据初始化
		for (int i = 0; i < vertices.length; i += 3) {// 将顶点向量规格化
			float[] vec = LeGLVectorUtil.normalizeVector(vertices[i],
					vertices[i + 1], vertices[i + 2]);
			vertices[i] = vec[0];
			vertices[i + 1] = vec[1];
			vertices[i + 2] = vec[2];
		}
		ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length * 4);// 创建顶点法向量数据缓冲
		nbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		normalBuffer = nbb.asFloatBuffer();// 转换为float型缓冲
		normalBuffer.put(vertices);// 向缓冲区中放入顶点法向量数据
		normalBuffer.position(0);// 设置缓冲区起始位置
		// st坐标数据初始化
		ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length * 4);// 创建顶点纹理数据缓冲
		tbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		textureBuffer = tbb.asFloatBuffer();// 转换为float型缓冲
		textureBuffer.put(textures);// 向缓冲区中放入顶点纹理数据
		textureBuffer.position(0);// 设置缓冲区起始位置

	}

	/**
	 * 自定义初始化着色器的initShader方法
	 * 
	 * @param context
	 */
	public void initShader(LeGlBaseScene context) {
		// 加载顶点着色器的脚本内容
		mVertexShader = LeGLShaderUtil.loadFromAssetsFile("vertex_tex.sh",
				context.getResources());
		// 加载片元着色器的脚本内容
		mFragmentShader = LeGLShaderUtil.loadFromAssetsFile("frag_tex.sh",
				context.getResources());
		// 基于顶点着色器与片元着色器创建程序
		mProgram = LeGLShaderUtil.createProgram(mVertexShader, mFragmentShader);
		// 获取程序中顶点位置属性引用id
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		// 获取程序中顶点纹理坐标属性引用id
		maTexCoorHandle = GLES20.glGetAttribLocation(mProgram, "aTexCoor");
		// 获取程序中总变换矩阵引用id
		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

		// 获取位置、旋转变换矩阵引用id
		muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix");

	}

	/**
	 * 
	 * @param texId
	 */
	public void drawSelf(int texId, long drawTime) {
		super.drawSelf(texId, drawTime);

		// 制定使用某套shader程序
		GLES20.glUseProgram(mProgram);
		// 将最终变换矩阵传入shader程序
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				LeGLMatrixState.getFinalMatrix(), 0);
		// 将位置、旋转变换矩阵传入shader程序
		GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false,
				LeGLMatrixState.getMMatrix(), 0);

		// 传送顶点位置数据
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
				false, 3 * 4, vertexBuffer);
		// 传送顶点纹理坐标数据
		GLES20.glVertexAttribPointer(maTexCoorHandle, 2, GLES20.GL_FLOAT,
				false, 2 * 4, textureBuffer);

		// 启用顶点位置数据
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		// 启用顶点纹理数据
		GLES20.glEnableVertexAttribArray(maTexCoorHandle);
		// 绑定纹理
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);

		// 绘制纹理矩形
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
	}

}