package com.lenovo.vcs.weaverth.relation.ui.opengl.sprite;

import com.lenovo.vcs.weaverth.relation.ui.opengl.scene.LeGlBaseScene;

public class LeGLRect extends LeGLBaseRect {

	public LeGLRect(LeGlBaseScene mv, float width, float height, float sEnd,
			float tEnd) {
		super(mv, width, height, sEnd, tEnd);
	}

	@Override
	public void drawSelf(int texId, long drawTime) {
		super.drawSelf(texId, drawTime);
	}

}