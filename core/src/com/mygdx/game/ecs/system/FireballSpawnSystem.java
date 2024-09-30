package com.mygdx.game.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.mygdx.game.ecs.system.passive.EntityFactorySystem;

public class FireballSpawnSystem extends IntervalSystem {

    private EntityFactorySystem factory;

    public FireballSpawnSystem() {
        super(10f);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        factory = engine.getSystem(EntityFactorySystem.class);
    }

    @Override
    protected void updateInterval() {
    }
}
