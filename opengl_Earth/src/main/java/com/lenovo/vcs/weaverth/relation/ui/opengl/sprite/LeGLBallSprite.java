package com.lenovo.vcs.weaverth.relation.ui.opengl.sprite;

import com.lenovo.vcs.weaverth.relation.ui.opengl.scene.LeGlBaseScene;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.LeGLMatrixState;
import com.lenovo.vcs.weaverth.relation.ui.opengl.utils.Log;

/**
 * 
 * @author xiaxveliang
 *
 */
public class LeGLBallSprite extends LeGLBaseBallSprite {
	private static final String TAG = LeGLBallSprite.class.getSimpleName();

	public LeGLBallSprite(LeGlBaseScene context, float r) {
		super(context, r);
	}

	@Override
	public void drawSelf(int texId, long drawTime) {
		Log.d(TAG, "--drawSelf--");
		LeGLMatrixState.pushMatrix();
		
		Log.d(TAG, "this.getLeGLSpriteScale(): "+this.getLeGLSpriteScale());

		// 缩放
		LeGLMatrixState.scale(this.getLeGLSpriteScale(),
				this.getLeGLSpriteScale(), this.getLeGLSpriteScale());
		// 旋转
		LeGLMatrixState.rotate(this.getLeGLSpriteAngleX(), 1, 0, 0);
		// 旋转
		LeGLMatrixState.rotate(this.getLeGLSpriteAngleY(), 0, 1, 0);
		//
		super.drawSelf(texId, drawTime);

		LeGLMatrixState.popMatrix();

	}

}