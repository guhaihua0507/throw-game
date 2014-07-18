package com.ghh.throwgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class ThrowGame extends Game {
	private GameScreen		gameScreen;
	private LoadingScreen	loadScreen;
	private StartScreen		startScreen;

	protected AssetManager	manager;

	@Override
	public void create() {
		manager = new AssetManager();
		loadAssets();

		startScreen = new StartScreen(this);
		setScreen(startScreen);
	}

	public void startGame() {
		Gdx.input.setCatchBackKey(true);
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	public void loadGame() {
		Gdx.input.setCatchBackKey(true);
		loadScreen = new LoadingScreen(this);
		setScreen(loadScreen);
	}

	public void gotoHome() {
		Gdx.input.setCatchBackKey(false);
		setScreen(startScreen);
	}

	public void loadAssets() {
		/*Monster texture*/
		manager.load("m/slime.png", Texture.class);
		manager.load("m/1.png", Texture.class);
		manager.load("m/2.png", Texture.class);
		manager.load("m/3.png", Texture.class);
		manager.load("m/4.png", Texture.class);
		manager.load("m/5.png", Texture.class);
		manager.load("m/6.png", Texture.class);
		manager.load("m/7.png", Texture.class);
		manager.load("m/8.png", Texture.class);
		
		/*Weapons texture*/
		manager.load("w/w1.png", Texture.class);
		manager.load("w/w2.png", Texture.class);
		manager.load("w/w3.png", Texture.class);
		
		manager.load("bgsound.mp3", Music.class);
	}

	public void unloadAssets() {
		/*Monster texture*/
		manager.unload("m/slime.png");
		manager.unload("m/1.png");
		manager.unload("m/2.png");
		manager.unload("m/3.png");
		manager.unload("m/4.png");
		manager.unload("m/5.png");
		manager.unload("m/6.png");
		manager.unload("m/7.png");
		manager.unload("m/8.png");
		
		/*Weapon texture*/
		manager.unload("w/w1.png");
		manager.unload("w/w2.png");
		manager.unload("w/w3.png");
		
		manager.unload("bgsound.mp3");
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		unloadAssets();
		manager.dispose();
		super.dispose();
	}
}
