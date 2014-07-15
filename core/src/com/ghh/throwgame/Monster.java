package com.ghh.throwgame;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Monster extends Actor {
	public static enum STATUS {
		WALKING, ATTACKED, DESTROYING
	}

	private float			life			= 100f;
	private float			speed			= 100f;
	private float			width			= 64f;
	private float			height			= 64f;

	private Animation		walkAnimation;
	private Animation		attackAnimation;
	private Animation		destroyAnimation;

	private TextureRegion	currentFrame	= null;
	private STATUS			state			= STATUS.WALKING;
	private float			stateTime		= 0f;
	private boolean			isDestroyed		= false;

	// private ScaleToAction scaleAction = Actions.scaleTo(0f, 0f, 0.2f);

	public Monster(float x, float y) {
		this.setBounds(x, y, width, height);
		init();
	}

	private void init() {
		Texture texture = new Texture(Gdx.files.internal("m/slime.png"));
		TextureRegion[][] splits = TextureRegion.split(texture, 96, 96);

		// walk animation
		TextureRegion[] frames = Arrays.copyOfRange(splits[0], 0, 4);
		walkAnimation = new Animation(0.25f, frames);
		walkAnimation.setPlayMode(PlayMode.LOOP);

		// attacked animation
		frames = Arrays.copyOfRange(splits[1], 0, 2);
		attackAnimation = new Animation(0.25f, frames);

		// dying animation
		frames = Arrays.copyOfRange(splits[1], 2, 4);
		destroyAnimation = new Animation(0.25f, frames);

		// TODO Remove this section
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Monster.this.attacked();
			}
		});
	}

	private void update() {
		if (state != STATUS.DESTROYING) {
			if (state == STATUS.WALKING) {
				float y = this.getY() - speed * Gdx.graphics.getDeltaTime();
				this.setY(y);
			}

			if (state == STATUS.ATTACKED) {
				if (attackAnimation.isAnimationFinished(stateTime)) {
					state = STATUS.WALKING;
					stateTime = 0f;
				}
				if (life <= 0) {
					state = STATUS.DESTROYING;
					stateTime = 0f;
					// this.addAction(scaleAction);
					addAction(Actions.fadeOut(0.5f));
				}
			}

			if (this.getY() <= 0) {
				this.destroy();
			}
		} else {
			if (destroyAnimation.isAnimationFinished(stateTime)) {
				destroy();
			}
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

	public void attacked() {
		if (state != STATUS.DESTROYING) {
			this.state = STATUS.ATTACKED;
			this.stateTime = 0;
			this.life -= 20; // TODO
		}
	}

	private void updateFrame() {
		switch (state) {
		case WALKING:
			currentFrame = walkAnimation.getKeyFrame(stateTime, true);
			break;

		case ATTACKED:
			currentFrame = attackAnimation.getKeyFrame(stateTime);
			break;

		case DESTROYING:
			currentFrame = destroyAnimation.getKeyFrame(stateTime);
			break;
		}
	}

	private void destroy() {
		isDestroyed = true;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}
}
