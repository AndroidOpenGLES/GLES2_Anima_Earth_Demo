package com.lenovo.vcs.weaverth.relation.ui.opengl.utils;

/**
 * 
 * @author xiaxl1
 *
 */
public class Log {

	private static String TAG = "relation: ";
	private static boolean isShow = true;

	public static void i(String tag, String msg) {

		if (isShow == false) {
			return;
		}
		android.util.Log.i(createTAG(tag), msg);

	}

	public static void e(String tag, String msg) {
		if (isShow == false) {
			return;
		}
		android.util.Log.e(createTAG(tag), msg);
	}

	public static void w(String tag, String msg) {
		if (isShow == false) {
			return;
		}
		android.util.Log.w(createTAG(tag), msg);
	}

	public static void d(String tag, String msg) {
		if (isShow == false) {
			return;
		}
		android.util.Log.d(createTAG(tag), msg);
	}

	public static void v(String tag, String msg) {
		if (isShow == false) {
			return;
		}
		android.util.Log.v(createTAG(tag), msg);
	}

	private static String createTAG(String tag) {
		StringBuffer sb = new StringBuffer();
		sb.append(TAG);
		sb.append(tag);
		return sb.toString();
	}

}
