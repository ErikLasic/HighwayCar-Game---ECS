package com.mygdx.game;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {
    private static final boolean DRAW_DEBUG_OUTLINE = false;

    private static final String RAW_ASSETS_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/background",   // the directory containing individual images to be packed
                ASSETS_PATH + "/background",   // the directory where the pack file will be written
                "background"   // the name of the pack file / atlas name
        );

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/models",   // the directory containing individual images to be packed
                ASSETS_PATH + "/models",   // the directory where the pack file will be written
                "models"   // the name of the pack file / atlas name
        );

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/spawnables",   // the directory containing individual images to be packed
                ASSETS_PATH + "/spawnables",   // the directory where the pack file will be written
                "spawnables"   // the name of the pack file / atlas name
        );

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/particle",   // the directory containing individual images to be packed
                ASSETS_PATH + "/particle",   // the directory where the pack file will be written
                "particle"   // the name of the pack file / atlas name
        );
    }
}
