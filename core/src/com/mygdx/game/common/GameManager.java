package com.mygdx.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.HighwayCarECS;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private final Preferences PREFS;

    private int health;
    private int fuel;
    private int score;

    private long counter = 500;
    private static long lastFuelDropTime = TimeUtils.nanoTime();
    private static final long FUEL_DROP_TIME = 1000000000;

    private boolean shieldActive = false;

    private static long lastFireballTime;
    private static final long FIREBALL_RELOAD = 1000000000;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(HighwayCarECS.class.getSimpleName());
    }

    public int getHealth() {
        return health;
    }
    public int getFuel() {
        return fuel;
    }
    public int getScore() {
        return score;
    }
    public long getCounter() {
        return counter;
    }

    public void resetResult() {
        health = 3;
        fuel = 100;
        score = 0;
    }

    public static boolean fireballReloaded() {
        return (TimeUtils.nanoTime() - lastFireballTime > FIREBALL_RELOAD);
    }

    public void setFireballTimer() {
        lastFireballTime = TimeUtils.nanoTime();
    }

    public void incScore() {
        score++;
    }

    public boolean isShieldActive() {
        return shieldActive;
    }

    public void setShieldStatus(boolean shield) {
        this.shieldActive = shield;
    }

    public boolean isTimeToDropFuel() {
        return (TimeUtils.nanoTime() - lastFuelDropTime > FUEL_DROP_TIME);
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
        lastFuelDropTime = TimeUtils.nanoTime();
        if (fuel <= 0) {
            this.fuel = 0;
        }
    }

    public void startCounter() {
        counter--;
    }
    public void setCounter() {
        counter = 500;
    }

    public void reFuel() {
        fuel = 100;
    }

    public boolean isGameOver() {
        return health <= 0 || fuel <= 0;
    }

    public void damage() {
        health -= 1;
        if (health == 0) {
        }
    }
}