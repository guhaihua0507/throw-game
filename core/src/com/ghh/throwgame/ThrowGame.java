package com.ghh.throwgame;

import com.badlogic.gdx.Game;

public class ThrowGame extends Game {
	private GameScreen gameScreen;
	
	@Override
	public void create () {
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}
}
