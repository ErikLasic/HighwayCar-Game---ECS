package com.mygdx.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {
    public static final AssetDescriptor<BitmapFont> ARIAL32 =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_ARIAL_32, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> BACKGROUND =
            new AssetDescriptor<TextureAtlas>(AssetPaths.BACKGROUND, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> MODELS =
            new AssetDescriptor<TextureAtlas>(AssetPaths.MODELS, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> SPAWNABLES =
            new AssetDescriptor<TextureAtlas>(AssetPaths.SPAWNABLES, TextureAtlas.class);

    public static final AssetDescriptor<Sound> CAR_CRASH_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.CAR_CRASH_SOUND, Sound.class);

    public static final AssetDescriptor<Sound> CAR_CRUSH_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.CAR_CRUSH_SOUND, Sound.class);

    public static final AssetDescriptor<Sound> FIREBALL_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.FIREBALL_SOUND, Sound.class);

    public static final AssetDescriptor<Sound> FUEL_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.FUEL_SOUND, Sound.class);

    public static final AssetDescriptor<Sound> SHIELD_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.SHIELD_SOUND, Sound.class);

    private AssetDescriptors() {
    }
}
