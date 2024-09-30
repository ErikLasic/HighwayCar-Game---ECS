package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;

import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.ecs.system.debug.support.ViewportUtils;
import com.mygdx.game.screen.GameScreen;

public class HighwayCarECS extends Game {
	private AssetManager assetManager;
	private SpriteBatch batch;

	@Override
	public void create () {
		ViewportUtils.DEFAULT_CELL_SIZE = 32;

		assetManager = new AssetManager();

		batch = new SpriteBatch();
		assetManager.load(AssetDescriptors.BACKGROUND);
		assetManager.load(AssetDescriptors.MODELS);
		assetManager.load(AssetDescriptors.SPAWNABLES);
		assetManager.load(AssetDescriptors.ARIAL32);
		assetManager.load(AssetDescriptors.CAR_CRASH_SOUND);
		assetManager.load(AssetDescriptors.CAR_CRUSH_SOUND);
		assetManager.load(AssetDescriptors.FUEL_SOUND);
		assetManager.load(AssetDescriptors.FIREBALL_SOUND);
		assetManager.load(AssetDescriptors.SHIELD_SOUND);
		assetManager.finishLoading();
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		assetManager.dispose();
		batch.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
