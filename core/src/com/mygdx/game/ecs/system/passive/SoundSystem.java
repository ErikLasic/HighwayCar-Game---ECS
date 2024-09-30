package com.mygdx.game.ecs.system.passive;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.assets.AssetDescriptors;

public class SoundSystem extends EntitySystem {

    private final AssetManager assetManager;

    private Sound carCrashSound;
    private Sound carCrushSound;
    private Sound fireballSound;
    private Sound fuelSound;
    private Sound shieldSound;

    public SoundSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        setProcessing(false); // passive system
        init();
    }

    private void init() {
        carCrashSound = assetManager.get(AssetDescriptors.CAR_CRASH_SOUND);
        carCrushSound = assetManager.get(AssetDescriptors.CAR_CRUSH_SOUND);
        fireballSound = assetManager.get(AssetDescriptors.FIREBALL_SOUND);
        fuelSound = assetManager.get(AssetDescriptors.FUEL_SOUND);
        shieldSound = assetManager.get(AssetDescriptors.SHIELD_SOUND);
    }

    public void setCarCrashSound() {
        carCrashSound.play();
    }
    public void setCarCrushSound() {
        carCrushSound.play();
    }
    public void setFireballSound() {
        fireballSound.play();
    }
    public void setFuelSound() {
        fuelSound.play();
    }
    public void setShieldSound() {
        shieldSound.play();
    }
}
