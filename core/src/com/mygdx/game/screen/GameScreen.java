package com.mygdx.game.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HighwayCarECS;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.ecs.component.BoundsComponent;
import com.mygdx.game.ecs.component.PositionComponent;
import com.mygdx.game.ecs.component.CarComponent;
import com.mygdx.game.ecs.system.FuelSpawnSystem;
import com.mygdx.game.ecs.system.FireballSpawnSystem;
import com.mygdx.game.ecs.system.HudRenderSystem;
import com.mygdx.game.ecs.system.ShieldSpawnSystem;
import com.mygdx.game.ecs.system.CarInputSystem;
import com.mygdx.game.ecs.system.PoliceCarSpawnSystem;
import com.mygdx.game.ecs.system.debug.DebugCameraSystem;
import com.mygdx.game.ecs.system.debug.DebugInputSystem;
import com.mygdx.game.ecs.system.debug.DebugRenderSystem;
import com.mygdx.game.ecs.system.debug.GridRenderSystem;
import com.mygdx.game.ecs.system.BoundsSystem;
import com.mygdx.game.ecs.system.CleanUpSystem;
import com.mygdx.game.ecs.system.CollisionSystem;
import com.mygdx.game.ecs.system.MovementSystem;
import com.mygdx.game.ecs.system.RenderSystem;
import com.mygdx.game.ecs.system.WorldWrapSystem;
import com.mygdx.game.ecs.system.passive.EntityFactorySystem;
import com.mygdx.game.ecs.system.passive.ParticleSystem;
import com.mygdx.game.ecs.system.passive.SoundSystem;
import com.mygdx.game.ecs.system.passive.StartUpSystem;

import javax.swing.text.Position;

public class GameScreen extends ScreenAdapter {

    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;
    private PooledEngine engine;
    private BitmapFont font;

    private long counter = 500;

    public GameScreen(HighwayCarECS game) {
        assetManager = game.getAssetManager();
        batch = game.getBatch();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, camera);
        hudViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer = new ShapeRenderer();
        font = assetManager.get(AssetDescriptors.ARIAL32);

        engine = new PooledEngine();

        // passive systems
        engine.addSystem(new EntityFactorySystem(assetManager));
        engine.addSystem(new ParticleSystem(assetManager));
        engine.addSystem(new SoundSystem(assetManager));
        engine.addSystem(new StartUpSystem());  // called only at the start, to generate first entities

        engine.addSystem(new CarInputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new WorldWrapSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new FireballSpawnSystem());
        engine.addSystem(new PoliceCarSpawnSystem());
        engine.addSystem(new ShieldSpawnSystem());
        engine.addSystem(new FuelSpawnSystem());
        engine.addSystem(new CleanUpSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new RenderSystem(batch, viewport));
        engine.addSystem(new HudRenderSystem(batch, hudViewport, font));

        GameManager.INSTANCE.resetResult();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if (Gdx.input.isKeyPressed(Input.Keys.R)) GameManager.INSTANCE.resetResult();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && GameManager.fireballReloaded()) {
            SoundSystem sound = engine.getSystem(SoundSystem.class);
            GameManager.INSTANCE.setFireballTimer();

            EntityFactorySystem factory = engine.getSystem(EntityFactorySystem.class);
            ImmutableArray<Entity> car = engine.getEntitiesFor(Family.all(CarComponent.class, BoundsComponent.class).get());

            for (Entity cars : car) {
                PositionComponent carPosition = Mappers.POSITION.get(cars);
                factory.createFireball(carPosition);
                sound.setFireballSound();
            }
        }

        ScreenUtils.clear(Color.BLACK);

        if (GameManager.INSTANCE.isTimeToDropFuel()) {
            GameManager.INSTANCE.setFuel(GameManager.INSTANCE.getFuel()-10);
        }

        if (GameManager.INSTANCE.isShieldActive()) {
            GameManager.INSTANCE.startCounter();
            if (GameManager.INSTANCE.getCounter() == 0) {
                GameManager.INSTANCE.setShieldStatus(false);
                GameManager.INSTANCE.setCounter();
            }
        }

        if (GameManager.INSTANCE.isGameOver()) {
            engine.update(0);
        } else {
            engine.update(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        engine.removeAllEntities();
    }
}
