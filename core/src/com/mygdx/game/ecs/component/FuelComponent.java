package com.mygdx.game.ecs.component;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.ashley.core.Component;

public class FuelComponent implements Component, Pool.Poolable {

    public boolean hit;

    @Override
    public void reset() {
        hit = false;
    }
}
