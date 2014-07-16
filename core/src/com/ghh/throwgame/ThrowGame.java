package com.ghh.throwgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class ThrowGame extends Game {
	private GameScreen		gameScreen;
	private LoadingScreen	loadScreen;

	protected AssetManager	manager;

	@Override
	public void create() {
		manager = new AssetManager();
		loadAssets();
		
		loadScreen = new LoadingScreen(this);
		setScreen(loadScreen);
	}

	public void startGame() {
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	public void loadAssets() {
		manager.load("m/slime.png", Texture.class);
		manager.load("m/1211.png", Texture.class);
		manager.load("bgsound.mp3", Music.class);
	}

	public void unloadAssets() {
		manager.unload("m/slime.png");
		manager.unload("m/1211.png");
		manager.unload("bgsound.mp3");
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		unloadAssets();
		super.dispose();
	}
}
