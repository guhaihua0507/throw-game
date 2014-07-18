package com.ghh.throwgame.m;

import java.util.Arrays;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ghh.throwgame.Monster;

public class GenericMonster extends Monster {
	private AssetManager	assetManager;
	private String			assetName;

	private float			width	= 80f;
	private float			height	= 80f;
	private float			life;
	private float			speed;

	private Animation		walkAnimation;
	private Animation		attackAnimation;
	private Animation		destroyAnimation;

	public GenericMonster(AssetManager assetManager, String assetName, float life, float speed) {
		this.assetManager = assetManager;
		this.assetName = assetName;
		this.life = life;
		this.speed = speed;
		this.setBounds(0, 0, width, height);
		init();
	}

	private void init() {
		Texture texture = assetManager.get(assetName);
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
