package com.mygdx.game.common;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.game.ecs.component.BoundsComponent;
import com.mygdx.game.ecs.component.CarComponent;
import com.mygdx.game.ecs.component.DimensionComponent;
import com.mygdx.game.ecs.component.FireballComponent;
import com.mygdx.game.ecs.component.FuelComponent;
import com.mygdx.game.ecs.component.MovementComponentXYR;
import com.mygdx.game.ecs.component.PoliceCarComponent;
import com.mygdx.game.ecs.component.PositionComponent;
import com.mygdx.game.ecs.component.ShieldComponent;
import com.mygdx.game.ecs.component.TextureComponent;
import com.mygdx.game.ecs.component.ZOrderComponent;

//TODO Explain how Mappers work (see ComponentMapper and Entity implementation)
public final class Mappers {

    public static final ComponentMapper<CarComponent> CAR =
            ComponentMapper.getFor(CarComponent.class);

    public static final ComponentMapper<PoliceCarComponent> POLICE_CAR =
            ComponentMapper.getFor(PoliceCarComponent.class);

    public static final ComponentMapper<FireballComponent> FIREBALL =
            ComponentMapper.getFor(FireballComponent.class);

    public static final ComponentMapper<FuelComponent> FUEL =
            ComponentMapper.getFor(FuelComponent.class);

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);

    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<MovementComponentXYR> MOVEMENT =
            ComponentMapper.getFor(MovementComponentXYR.class);

    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);

    public static final ComponentMapper<ShieldComponent> SHIELD =
            ComponentMapper.getFor(ShieldComponent.class);

    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);

    public static final ComponentMapper<ZOrderComponent> Z_ORDER =
            ComponentMapper.getFor(ZOrderComponent.class);

    private Mappers() {
    }
}
