package com.mygdx.game.ecs.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.ecs.component.BoundsComponent;
import com.mygdx.game.ecs.component.CarComponent;
import com.mygdx.game.ecs.component.CleanUpComponent;
import com.mygdx.game.ecs.component.DimensionComponent;
import com.mygdx.game.ecs.component.FireballComponent;
import com.mygdx.game.ecs.component.FuelComponent;
import com.mygdx.game.ecs.component.MovementComponentXYR;
import com.mygdx.game.ecs.component.PoliceCarComponent;
import com.mygdx.game.ecs.component.PositionComponent;
import com.mygdx.game.ecs.component.ShieldComponent;
import com.mygdx.game.ecs.component.TextureComponent;
import com.mygdx.game.ecs.component.WorldWrapComponent;
import com.mygdx.game.ecs.component.ZOrderComponent;

public class EntityFactorySystem extends EntitySystem {
    private static final int BACKGROUND_Z_ORDER = 1;
    private static final int POLICE_CAR_Z_ORDER = 2;
    private static final int FUEL_Z_ORDER = 3;
    private static final int FIREBALL_Z_ORDER = 4;
    private static final int SHIELD_Z_ORDER = 5;
    private static final int CAR_Z_ORDER = 6;

    private final AssetManager assetManager;

    private PooledEngine engine;

    private TextureAtlas backgroundAtlas;
    private TextureAtlas modelsAtlas;
    private TextureAtlas spawnablesAtlas;

    public EntityFactorySystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        setProcessing(false);   // passive system
        init();
    }

    private void init() {
        backgroundAtlas = assetManager.get(AssetDescriptors.BACKGROUND);
        modelsAtlas = assetManager.get(AssetDescriptors.MODELS);
        spawnablesAtlas = assetManager.get(AssetDescriptors.SPAWNABLES);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = (PooledEngine) engine;
    }

    public void createBackground() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = 0;
        position.y = 0;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.WIDTH;
        dimension.height = GameConfig.HEIGHT;

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = backgroundAtlas.findRegion(RegionNames.BACKGROUND);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = BACKGROUND_Z_ORDER;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(texture);
        entity.add(zOrder);
        engine.addEntity(entity);
    }

    public void createCar() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = GameConfig.WIDTH / 2f - GameConfig.CAR_WIDTH / 2f;
        position.y = 20;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.CAR_WIDTH;
        dimension.height = GameConfig.CAR_HEIGHT;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        MovementComponentXYR movement = engine.createComponent(MovementComponentXYR.class);

        CarComponent car = engine.createComponent(CarComponent.class);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = modelsAtlas.findRegion(RegionNames.CAR_MODEL);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = CAR_Z_ORDER;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movement);
        entity.add(car);
        entity.add(worldWrap);
        entity.add(texture);
        entity.add(zOrder);
        engine.addEntity(entity);
    }

    public void createPoliceCar() {
        PositionComponent position = engine.createComponent(PositionComponent.class);

        position.x = MathUtils.random(0, GameConfig.WIDTH - GameConfig.POLICE_CAR_WIDTH);
        position.y = GameConfig.HEIGHT;

        MovementComponentXYR movementComponent = engine.createComponent(MovementComponentXYR.class);
        movementComponent.ySpeed = -GameConfig.POLICE_CAR_SPEED_X_MIN * MathUtils.random(1f, 2f);

        float randFactor = MathUtils.random(1f, 1.5f);
        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.POLICE_CAR_WIDTH * randFactor;
        dimension.height = GameConfig.POLICE_CAR_HEIGHT * randFactor;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        PoliceCarComponent policeCar = engine.createComponent(PoliceCarComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = modelsAtlas.findRegion(RegionNames.POLICE_CAR_MODEL);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = POLICE_CAR_Z_ORDER;

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movementComponent);
        entity.add(policeCar);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }

    public void createFuel() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = MathUtils.random(0, GameConfig.WIDTH - GameConfig.FUEL_WIDTH);
        position.y = GameConfig.HEIGHT;

        MovementComponentXYR movementComponent = engine.createComponent(MovementComponentXYR.class);
        movementComponent.ySpeed = -GameConfig.FUEL_SPEED_X_MIN * MathUtils.random(1f, 2f);

        FuelComponent fuel = engine.createComponent(FuelComponent.class);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.FUEL_WIDTH;
        dimension.height = GameConfig.FUEL_HEIGHT;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = spawnablesAtlas.findRegion(RegionNames.FUEL);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = FUEL_Z_ORDER;

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movementComponent);
        entity.add(fuel);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }

    public void createShield() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = MathUtils.random(0, GameConfig.WIDTH - GameConfig.SHIELD_WIDTH);
        position.y = GameConfig.HEIGHT;

        ShieldComponent shield = engine.createComponent(ShieldComponent.class);

        MovementComponentXYR movementComponent = engine.createComponent(MovementComponentXYR.class);
        movementComponent.ySpeed = -GameConfig.SHIELD_SPEED_X_MIN * MathUtils.random(1f, 2f);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.SHIELD_WIDTH;
        dimension.height = GameConfig.SHIELD_HEIGHT;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = spawnablesAtlas.findRegion(RegionNames.SHIELD);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = SHIELD_Z_ORDER;

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(shield);
        entity.add(movementComponent);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }

    public void createFireball(PositionComponent carPosition) {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = carPosition.x;
        position.y = carPosition.y;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.FIREBALL_WIDTH;
        dimension.height = GameConfig.FIREBALL_HEIGHT;

        MovementComponentXYR movementComponent = engine.createComponent(MovementComponentXYR.class);
        movementComponent.ySpeed = GameConfig.MAX_FIREBALL_Y_SPEED;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(GameConfig.FIREBALL_WIDTH, GameConfig.FIREBALL_HEIGHT);

        FireballComponent fireball = engine.createComponent(FireballComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = spawnablesAtlas.findRegion(RegionNames.FIREBALL);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = FIREBALL_Z_ORDER;

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(fireball);
        entity.add(movementComponent);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(worldWrap);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }
}
