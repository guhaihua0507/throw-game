package com.ghh.throwgame;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;

public class WeaponFactory {
	private String[]		assets	= { "w/w1.png", "w/w2.png", "w/w3.png" };
	private float[]			power	= { 15, 20, 30 };

	private AssetManager	assetManager;
	private Random			random	= new Random();

	public WeaponFactory(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public Weapon create() {
		int r = random.nextInt(assets.length);
		return new Weapon(assetManager, assets[r], power[r]);
	}
}
