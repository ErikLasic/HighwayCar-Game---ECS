package com.mygdx.game.ecs.system.passive;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.mygdx.game.ecs.component.PositionComponent;

public class ParticleSystem extends EntitySystem {
    private final AssetManager assetManager;

    public ParticleEffect carSmoke;
    public ParticleEffect exhaustSmoke;
    public ParticleEffect explosion;

    public ParticleSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        setProcessing(false); // passive system
        init();
    }

    private void init() {
        // Particle effects
        carSmoke = new ParticleEffect();
        exhaustSmoke = new ParticleEffect();
        explosion = new ParticleEffect();

        carSmoke.load(Gdx.files.internal("particle/car.smoke"), Gdx.files.internal("particle"));
        exhaustSmoke.load(Gdx.files.internal("particle/exhaust.smoke"), Gdx.files.internal("particle"));
        explosion.load(Gdx.files.internal("particle/explosion.pe"), Gdx.files.internal("particle"));
    }

    public void setCarSmoke(float carXPosition, float carYPosition) {
        carSmoke.setPosition(carXPosition, carYPosition);
        carSmoke.start();
    }

    public void setExhaustSmoke(float policeCarXPosition, float policeCarYPosition) {
        exhaustSmoke.setPosition(policeCarXPosition, policeCarYPosition);
        exhaustSmoke.start();
    }

    public void setExplosion(float policeCarXPosition, float policeCarYPosition) {
        explosion.setPosition(policeCarXPosition, policeCarYPosition);
        explosion.start();
    }
}
