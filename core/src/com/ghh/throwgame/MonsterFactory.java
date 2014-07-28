package com.ghh.throwgame;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.ghh.throwgame.m.GenericMonster;

public class MonsterFactory {
	private String[] assets = {"m/1.png", "m/2.png", "m/3.png", "m/4.png", "m/5.png", "m/6.png", "m/7.png", "m/8.png"};
	private float[] mLife = {100f, 80f, 160f, 180f, 60f, 180f, 80f, 130f};
	private float[] mSpeed = {90f, 80f, 80f, 90f, 130f, 80f, 150f, 90f};
	
	private AssetManager manager;
	
	private Random random = new Random();
	public MonsterFactory(AssetManager manager) {
		this.manager = manager;
	}
	
	public Monster create() {
		int r = random.nextInt(assets.length);
		return new GenericMonster(manager, assets[r], mLife[r], mSpeed[r]);
	}
}
