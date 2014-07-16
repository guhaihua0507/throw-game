package com.ghh.throwgame.m;

import java.util.Arrays;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ghh.throwgame.Monster;

public class StoneMan extends Monster {
	protected AssetManager	assetManager;

	private float			width			= 64f;
	private float			height			= 64f;
	private float			life			= 150f;
	private float			speed			= 120f;
	
	private Animation		walkAnimation;
	private Animation		attackAnimation;
	private Animation		destroyAnimation;

	public StoneMan(AssetManager assetManager) {
		this.assetManager = assetManager;
		this.setBounds(0, 0, width, height);
		init();
	}

	private void init() {
		Texture texture = assetManager.get("m/1211.png");
		TextureRegion[][] splits = TextureRegion.split(texture, 64, 64);

		// walk animation
		TextureRegion[] frames = Arrays.copyOfRange(splits[0], 0, 4);
		walkAnimation = new Animation(0.25f, frames);
		walkAnimation.setPlayMode(PlayMode.LOOP);

		// attacked animation
		frames = Arrays.copyOfRange(splits[3], 0, 4);
		attackAnimation = new Animation(0.2f, frames);

		// dying animation
		frames = Arrays.copyOfRange(splits[1], 0, 4);
		destroyAnimation = new Animation(0.15f, frames);

		// TODO Remove this section
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				StoneMan.this.attacked();
			}
		});
	}

	@Override
	protected TextureRegion getWalkFrame(float stateTime) {
		return walkAnimation.getKeyFrame(stateTime, true);
	}

	@Override
	protected TextureRegion getAttactedFrame(float stateTime) {
		return attackAnimation.getKeyFrame(stateTime);
	}

	@Override
	protected TextureRegion getDestroyingFrame(float stateTime) {
		return destroyAnimation.getKeyFrame(stateTime);
	}

	@Override
	protected boolean isAttackingFinished(float stateTime) {
		return attackAnimation.isAnimationFinished(stateTime);
	}

	@Override
	protected boolean isDestroyingFinished(float stateTime) {
		return destroyAnimation.isAnimationFinished(stateTime);
	}

	@Override
	protected float getLife() {
		return life;
	}

	@Override
	protected void updateLife(float lf) {
		life -= lf;
		if (life < 0) {
			life = 0;
		}
	}

	@Override
	protected float getSpeed() {
		return speed;
	}
}
