package com.ghh.throwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public abstract class Monster extends Actor {
	public static enum MonsterState {
		WALKING, ATTACKED, DESTROYING
	}

	protected MonsterState	state			= MonsterState.WALKING;
	protected float			stateTime		= 0f;
	protected boolean		isDestroyed		= false;

	protected TextureRegion	currentFrame	= null;

	// private ScaleToAction scaleAction = Actions.scaleTo(0f, 0f, 0.2f);

	protected void update() {
		if (state == MonsterState.ATTACKED) {
			if (isAttackingFinished(stateTime)) {
				state = MonsterState.WALKING;
				stateTime = 0f;
			}

			if (getLife() <= 0) {
				state = MonsterState.DESTROYING;
				stateTime = 0f;
				// this.addAction(scaleAction);
				addAction(Actions.fadeOut(0.5f));
			}
		}

		if (state == MonsterState.DESTROYING && isDestroyingFinished(stateTime)) {
			isDestroyed = true;
		}

		if (state == MonsterState.WALKING) {
			walk();
		}

		updateFrame();
	}

	@Override
	public void act(float delta) {
		stateTime += delta;
		update();
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(batch.getColor().a, batch.getColor().g, batch.getColor().b, this.getColor().a);
		batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth() / 2, this.getHeight() / 2, this.getWidth(),
				this.getHeight(), getScaleX(), getScaleY(), 0f);
	}

	protected void walk() {
		float y = this.getY() - getSpeed() * Gdx.graphics.getDeltaTime();
		this.setY(y);
	}

	public void attacked(Weapon wp) {
		this.state = MonsterState.ATTACKED;
		this.stateTime = 0;
		updateLife(wp.getPower());
	}

	private void updateFrame() {
		switch (state) {
		case WALKING:
			currentFrame = getWalkFrame(stateTime);
			break;

		case ATTACKED:
			currentFrame = getAttactedFrame(stateTime);
			break;

		case DESTROYING:
			currentFrame = getDestroyingFrame(stateTime);
			break;
		}
	}
	
	public boolean isAlive() {
		return getLife() > 0;
	}
	
	protected abstract float getLife();

	protected abstract void updateLife(float lf);

	protected abstract float getSpeed();

	protected abstract TextureRegion getWalkFrame(float stateTime);

	protected abstract TextureRegion getAttactedFrame(float stateTime);

	protected abstract TextureRegion getDestroyingFrame(float stateTime);

	protected abstract boolean isAttackingFinished(float stateTime);

	protected abstract boolean isDestroyingFinished(float stateTime);

	public boolean isDestroyed() {
		return isDestroyed;
	}
}
