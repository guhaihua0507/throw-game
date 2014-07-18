package com.ghh.throwgame;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
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
	private MonsterFactory		monsterFactory;
	private WeaponFactory		weaponFactory;

	private float[]				respawnSpots;

	private Stage				stage;
	private Rectangle			glViewPoint;
	private Group				monsterGroup			= new Group();
	private Group				weaponGroup				= new Group();

	private Random				random					= new Random();
	private long				monsterInterval			= 1500;
	private long				lastMonsterCreateTime	= 0;

	private Weapon				currentWeapon;

	public GameScreen(ThrowGame game) {
		this.game = game;
		monsterFactory = new MonsterFactory(game.manager);
		weaponFactory = new WeaponFactory(game.manager);

		respawnSpots = new float[COLUMNS];
		float cWidth = WIDTH / COLUMNS;
		for (int i = 0; i < COLUMNS; i++) {
			respawnSpots[i] = cWidth / 2 + cWidth * i;
		}
	}

	@Override
	public void show() {
		glViewPoint = new Rectangle(0, 0, WIDTH, HEIGHT);

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

		respawnWeapon();
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
		/*
		 * update monsters
		 */
		Iterator<Actor> itr = monsterGroup.getChildren().iterator();
		while (itr.hasNext()) {
			Actor a = itr.next();
			if (a instanceof Monster) {
				Monster ms = (Monster) a;
				if (ms.isDestroyed()) {
					monsterGroup.removeActor(a);
				} else {
					if (ms.getY() + ms.getHeight() < 0) {
						monsterGroup.removeActor(ms);
					}
				}
			}
		}

		if (lastMonsterCreateTime == 0 || (TimeUtils.millis() - lastMonsterCreateTime) >= monsterInterval) {
			respawnMonster();
		}

		/*
		 * update weapons
		 */
		itr = weaponGroup.getChildren().iterator();
		while (itr.hasNext()) {
			Actor a = itr.next();
			if (a instanceof Weapon) {
				Weapon wp = (Weapon) a;

				checkIfHitMonster(wp);

				if (wp.isDestroyed()) {
					weaponGroup.removeActor(wp);
				} else {
					Rectangle rc = new Rectangle(wp.getX(), wp.getY(), wp.getWidth(), wp.getHeight());
					if (!glViewPoint.overlaps(rc)) {
						weaponGroup.removeActor(wp);
					}
				}
			}
		}

		if (!currentWeapon.isIdle()) {
			respawnWeapon();
		}
	}

	private void checkIfHitMonster(Weapon wp) {
		if (!wp.isFling()) {
			return;
		}
		Rectangle rcw = new Rectangle(wp.getX() + 5, wp.getY() + 5, wp.getWidth() - 10, wp.getHeight() - 10);
		System.out.println(wp.getWidth() + ":" + wp.getHeight());
		Iterator<Actor> it = monsterGroup.getChildren().iterator();
		while (it.hasNext()) {
			Actor a = it.next();
			if (!(a instanceof Monster)) {
				continue;
			}
			Monster ms = (Monster) a;
			if (!ms.isAlive()) {
				continue;
			}
			Rectangle rcm = new Rectangle(ms.getX(), ms.getY(), ms.getWidth(), ms.getHeight());
			if (rcw.overlaps(rcm)) {
				wp.attack(ms);
				ms.attacked(wp);
				break;
			}
		}
	}

	private void respawnMonster() {
		float centerX = respawnSpots[random.nextInt(COLUMNS)];

		Monster monster = monsterFactory.create();

		monster.setPosition(centerX - monster.getWidth() / 2, HEIGHT + monster.getHeight());

		monsterGroup.addActorAt(0, monster);

		lastMonsterCreateTime = TimeUtils.millis();
	}

	private void respawnWeapon() {
		currentWeapon = weaponFactory.create();
		currentWeapon.setCenterPosition(WIDTH / 2, 150f);
		weaponGroup.addActor(currentWeapon);
	}


	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void hide() {
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
