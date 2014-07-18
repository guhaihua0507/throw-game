package com.ghh.throwgame;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class GameScreen implements Screen {
	private final static float	WIDTH					= 480f;
	private final static float	HEIGHT					= 768f;
	private final static int	COLUMNS					= 5;

	private ThrowGame			game;
	private MonsterFactory		mFactory;

	private float[]				respawnSpots;

	private Stage				stage;
	private Group				monsterGroup			= new Group();
	private Group				weaponGroup				= new Group();

	private Random				random					= new Random();
	private long				monsterInterval			= 1500;
	private long				lastMonsterCreateTime	= 0;

	public GameScreen(ThrowGame game) {
		this.game = game;
		mFactory = new MonsterFactory(game.manager);
		
		respawnSpots = new float[COLUMNS];
		float cWidth = WIDTH / COLUMNS;
		for (int i = 0; i < COLUMNS; i++) {
			respawnSpots[i] = cWidth / 2 + cWidth * i;
		}
	}

	@Override
	public void show() {
		stage = new Stage(new ScalingViewport(Scaling.stretch, WIDTH, HEIGHT, new OrthographicCamera()));

		Gdx.input.setInputProcessor(stage);
		stage.addListener(new InputListener() {
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				if (keycode == Keys.BACK) {
					game.gotoHome();
				}
				return true;
			}
		});

		stage.addActor(monsterGroup);
		stage.addActor(weaponGroup);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(8f / 255f, 138f / 255f, 8f / 255f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

		update();
	}

	private void update() {
		Iterator<Actor> itr = monsterGroup.getChildren().iterator();
		while (itr.hasNext()) {
			Actor a = itr.next();
			if (a instanceof Monster) {
				if (((Monster) a).isDestroyed()) {
					monsterGroup.removeActor(a);
				}
			}
		}

		/*
		 * itr = weaponGroup.getChildren().iterator(); while (itr.hasNext()) {
		 * Actor a = itr.next(); if (a instanceof Monster) { if (((Monster)
		 * a).isDestroyed()) { weaponGroup.removeActor(a); } } }
		 */

		if (lastMonsterCreateTime == 0 || (TimeUtils.millis() - lastMonsterCreateTime) >= monsterInterval) {
			respawnMonster();
		}
	}

	private void respawnMonster() {
		float centerX = respawnSpots[random.nextInt(COLUMNS)];

		Monster monster = mFactory.create();

		monster.setPosition(centerX - monster.getWidth() / 2, HEIGHT + monster.getHeight());

		monsterGroup.addActorAt(0, monster);

		lastMonsterCreateTime = TimeUtils.millis();
	}

	@Override
	public void hide() {
		System.out.println("-------------------hide game screen");
	}

	@Override
	public void dispose() {
		System.out.println("-------------------dispose game screen");
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
