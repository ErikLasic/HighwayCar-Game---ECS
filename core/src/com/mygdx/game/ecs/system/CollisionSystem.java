package com.mygdx.game.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.ecs.component.FuelComponent;
import com.mygdx.game.ecs.component.BoundsComponent;
import com.mygdx.game.ecs.component.FireballComponent;
import com.mygdx.game.ecs.component.PositionComponent;
import com.mygdx.game.ecs.component.ShieldComponent;
import com.mygdx.game.ecs.component.CarComponent;
import com.mygdx.game.ecs.component.PoliceCarComponent;
import com.mygdx.game.ecs.system.passive.ParticleSystem;
import com.mygdx.game.ecs.system.passive.SoundSystem;

public class CollisionSystem extends EntitySystem {

    private static final Family CAR_FAMILY = Family.all(CarComponent.class, BoundsComponent.class).get();
    private static final Family FIREBALL_FAMILY = Family.all(FireballComponent.class, BoundsComponent.class).get();
    private static final Family POLICE_CAR_FAMILY = Family.all(PoliceCarComponent.class, BoundsComponent.class).get();
    private static final Family SHIELD_FAMILY = Family.all(ShieldComponent.class, BoundsComponent.class).get();
    private static final Family FUEL_FAMILY = Family.all(FuelComponent.class, BoundsComponent.class).get();

    private SoundSystem soundSystem;
    private ParticleSystem particleSystem;

    public CollisionSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        soundSystem = engine.getSystem(SoundSystem.class);
        particleSystem = engine.getSystem(ParticleSystem.class);
    }

    @Override
    public void update(float deltaTime) {
        if (GameManager.INSTANCE.isGameOver()) return;

        ImmutableArray<Entity> car = getEngine().getEntitiesFor(CAR_FAMILY);
        ImmutableArray<Entity> fireballs = getEngine().getEntitiesFor(FIREBALL_FAMILY);
        ImmutableArray<Entity> police_cars = getEngine().getEntitiesFor(POLICE_CAR_FAMILY);
        ImmutableArray<Entity> shields = getEngine().getEntitiesFor(SHIELD_FAMILY);
        ImmutableArray<Entity> fuels = getEngine().getEntitiesFor(FUEL_FAMILY);

        for (Entity carEntity : car) {
            BoundsComponent carBounds = Mappers.BOUNDS.get(carEntity);

            for (Entity shieldEntity : shields) {
                ShieldComponent shield = Mappers.SHIELD.get(shieldEntity);

                if (shield.hit) {
                    continue;
                }

                BoundsComponent shieldBounds = Mappers.BOUNDS.get(shieldEntity);

                if (Intersector.overlaps(carBounds.rectangle, shieldBounds.rectangle)) {
                    GameManager.INSTANCE.setShieldStatus(true);
                    shield.hit = true;
                    soundSystem.setShieldSound();
                    getEngine().removeEntity(shieldEntity);
                }
            }

            for (Entity fuelEntity : fuels) {
                FuelComponent fuel = Mappers.FUEL.get(fuelEntity);

                if (fuel.hit) {
                    continue;
                }

                BoundsComponent fuelBounds = Mappers.BOUNDS.get(fuelEntity);

                if (Intersector.overlaps(carBounds.rectangle, fuelBounds.rectangle)) {
                    fuel.hit = true;
                    GameManager.INSTANCE.reFuel();
                    soundSystem.setFuelSound();
                    getEngine().removeEntity(fuelEntity);
                }
            }

            for (Entity policeCarEntity : police_cars) {
                PoliceCarComponent police = Mappers.POLICE_CAR.get(policeCarEntity);

                if (police.hit) {
                    continue;
                }

                BoundsComponent policeBounds = Mappers.BOUNDS.get(policeCarEntity);

                if (Intersector.overlaps(carBounds.rectangle, policeBounds.rectangle)) {
                    PositionComponent policePosition = Mappers.POSITION.get(policeCarEntity);

                    if (GameManager.INSTANCE.isShieldActive()) {
                        police.hit = true;
                        getEngine().removeEntity(policeCarEntity);
                        particleSystem.setExplosion(policePosition.x, policePosition.y);
                        soundSystem.setCarCrashSound();
                       continue;
                    }
                    particleSystem.setExplosion(policePosition.x, policePosition.y);
                    police.hit = true;
                    GameManager.INSTANCE.damage();
                    soundSystem.setCarCrashSound();
                    getEngine().removeEntity(policeCarEntity);
                }
            }
        }

        for (Entity fireballEntity : fireballs) {
            BoundsComponent fireballBounds = Mappers.BOUNDS.get(fireballEntity);

            for (Entity policeCarEntity : police_cars) {
                PoliceCarComponent police = Mappers.POLICE_CAR.get(policeCarEntity);
                BoundsComponent policeBounds = Mappers.BOUNDS.get(policeCarEntity);

                if (Intersector.overlaps(policeBounds.rectangle, fireballBounds.rectangle)) {
                    PositionComponent policePosition = Mappers.POSITION.get(policeCarEntity);
                    particleSystem.setExplosion(
                            policePosition.x + GameConfig.POLICE_CAR_WIDTH,
                            policePosition.y
                    );

                    police.hit = true;
                    getEngine().removeEntity(fireballEntity);
                    getEngine().removeEntity(policeCarEntity);
                    soundSystem.setCarCrushSound();
                    GameManager.INSTANCE.incScore();
                    GameConfig.POLICE_CAR_SPEED_X_MIN += 0.1f;
                }
            }
        }

        for (Entity policeEntity : police_cars) {
            BoundsComponent policeBounds = Mappers.BOUNDS.get(policeEntity);

            if (policeBounds.rectangle.y < 0-policeBounds.rectangle.x) {
                getEngine().removeEntity(policeEntity);
            }
        }
    }
}
