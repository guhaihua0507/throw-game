package com.ghh.throwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class StartScreen implements Screen {
	private final static float	SCREEN_WIDTH			= 480f;
	private final static float	SCREEN_HEIGHT			= 800f;
	
	private ThrowGame	game;

	private Texture		startButton;
	private Image		image;
	private Stage		stage;

	public StartScreen(ThrowGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		stage = new Stage(new ScalingViewport(Scaling.fill, SCREEN_WIDTH, SCREEN_HEIGHT, new OrthographicCamera()));
		
		startButton = new Texture("button-start.png");
		image = new Image(startButton);
		image.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				StartScreen.this.game.startGame();
			}
		});
		image.setBounds(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT / 2 + 100, 100, 100);
		
		stage.addActor(image);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(8f / 255f, 125f / 255f, 37f / 255f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
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

	@Override
	public void dispose() {
	}

}
