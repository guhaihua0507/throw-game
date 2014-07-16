package com.ghh.throwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements Screen {
	private ThrowGame	game;

	private SpriteBatch	batch;
	private BitmapFont	font;

	public LoadingScreen(ThrowGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		font = new BitmapFont();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(8f / 255f, 125f / 255f, 37f / 255f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (game.manager.update()) {
			game.startGame();
			this.dispose();
		} else {
			batch.begin();
			String progress = (int)game.manager.getProgress() * 100 + "%";
			TextBounds tb = font.getBounds(progress);
			font.draw(batch, progress, Gdx.graphics.getWidth() / 2 - tb.width / 2, Gdx.graphics.getHeight() / 2 + tb.height);
			batch.end();
		}
	}

	@Override
	public void dispose() {
		font.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
