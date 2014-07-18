package com.ghh.throwgame;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class Weapon extends Actor {
	public static enum WeaponState {
		idle, fling, attacking, destroying
	}

	private AssetManager	assetManager;
	private String			assetName;

	private float			width		= 60f;
	private float			height		= 60f;
	private float			power		= 0f;
	private float			speedx		= 0f;
	private float			speedy		= 0f;

	private WeaponState		state		= WeaponState.idle;
	private float			stateTime	= 0;
	private TextureRegion	currentFrame;
	private boolean			isDestroyed	= false;

	private Animation		idleAnimation;
	private Animation		flingAnimation;
	private Animation		attackingAnimation;
	private Animation		destroyingAnimation;

	public Weapon(AssetManager assetManager, String assetName, float power) {
		this.assetManager = assetManager;
		this.assetName = assetName;
		this.power = power;
		setBounds(0, 0, width, height);
		init();
	}

	private void init() {
		Texture texture = assetManager.get(assetName);
		TextureRegion[][] splits = TextureRegion.split(texture, 64, 64);

		TextureRegion[] frames = Arrays.copyOfRange(splits[0], 0, 4);
		idleAnimation = new Animation(0.25f, frames);
		idleAnimation.setPlayMode(PlayMode.LOOP);

		frames = Arrays.copyOfRange(splits[3], 0, 4);
		flingAnimation = new Animation(0.2f, frames);
		flingAnimation.setPlayMode(PlayMode.LOOP);

		frames = Arrays.copyOfRange(splits[1], 0, 2);
		attackingAnimation = new Animation(0.1f, frames);

		frames = Arrays.copyOfRange(splits[0], 0, 2);
		destroyingAnimation = new Animation(0.2f, frames);

		this.addListener(new ActorGestureListener() {
			@Override
			public void fling(InputEvent event, float velocityX, float velocityY, int button) {
				if (state == WeaponState.idle) {
					setSpeed(velocityX, velocityY);
					state = WeaponState.fling;
				}
			}
		});
	}

	private void update() {
		if (state == WeaponState.attacking) {
			if(attackingAnimation.isAnimationFinished(stateTime)) {
				stateTime = 0;
				state = WeaponState.destroying;
				addAction(Actions.fadeOut(0.4f));
			}
		}

		if (state == WeaponState.destroying) {
			if (destroyingAnimation.isAnimationFinished(stateTime)) {
				isDestroyed = true;
			}
		}
		
		if (state == WeaponState.fling) {
			float deltaTime = Gdx.graphics.getDeltaTime();
			setX(getX() + (speedx * deltaTime));
			setY(getY() + (speedy * deltaTime));
		}
		
		updateFrame();
	}

	private void updateFrame() {
		switch (state) {
		case idle:
			currentFrame = getIdleFrame();
			break;
		case fling:
			currentFrame = getFlingFrame();
			break;
		case attacking:
			currentFrame = getAttackingFrame();
			break;
		case destroying:
			currentFrame = getDestroyingFrame();
			break;
		}
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
		batch.draw(currentFrame, this.getX(), this.getY(), this.getCenterX(), this.getCenterY(), this.getWidth(), this.getHeight(), getScaleX(), getScaleY(), 0f);
	}

	private void setSpeed(float speedx, float speedy) {
		this.speedx = speedx;
		this.speedy = speedy;
	}

	private TextureRegion getIdleFrame() {
		return idleAnimation.getKeyFrame(stateTime, true);
	}

	private TextureRegion getFlingFrame() {
		return flingAnimation.getKeyFrame(stateTime);
	}

	private TextureRegion getAttackingFrame() {
		return attackingAnimation.getKeyFrame(stateTime);
	}

	private TextureRegion getDestroyingFrame() {
		return destroyingAnimation.getKeyFrame(stateTime);
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
	public boolean isIdle() {
		return state == WeaponState.idle;
	}
	
	public boolean isFling() {
		return state == WeaponState.fling;
	}

	public float getPower() {
		return power;
	}

	public void attack(Monster ms) {
		state = WeaponState.attacking;
		stateTime = 0;
	}
}
