package com.lenovo.vcs.weaverth.relation.ui.opengl.utils;

//������񻯵Ĺ�����
public class LeGLVectorUtil {
	//��������񻯵ķ���
	public static float[] normalizeVector(float x, float y, float z){
		float mod=module(x,y,z);
		return new float[]{x/mod, y/mod, z/mod};//���ع�񻯺������
	}
	//��������ģ�ķ���
	public static float module(float x, float y, float z){
		return (float) Math.sqrt(x*x+y*y+z*z);
	}
}
