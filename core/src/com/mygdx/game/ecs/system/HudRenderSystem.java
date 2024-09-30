package com.mygdx.game.ecs.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.GameManager;

public class HudRenderSystem extends EntitySystem {

    private static final float PADDING = 20f;

    private final SpriteBatch batch;
    private final Viewport hudViewport;
    private final BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();


    public HudRenderSystem(SpriteBatch batch, Viewport hudViewport, BitmapFont font) {
        this.batch = batch;
        this.hudViewport = hudViewport;
        this.font = font;
    }

    @Override
    public void update(float deltaTime) {
        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();

        float y = hudViewport.getWorldHeight() - PADDING;

        font.setColor(Color.RED);
        String health = "HEALTH: " + GameManager.INSTANCE.getHealth();
        layout.setText(font, health);
        font.draw(batch, layout, PADDING, y);

        font.setColor(Color.BLACK);
        String score = "SCORE: " + GameManager.INSTANCE.getScore();
        layout.setText(font, score);
        float scoreX = (hudViewport.getWorldWidth() - layout.width)/ 2 - PADDING;
        font.draw(batch, layout, scoreX, y);

        if (GameManager.INSTANCE.isShieldActive()) {
            font.setColor(Color.YELLOW);
            String counter = "SHIELD COUNTER: " + GameManager.INSTANCE.getCounter();
            layout.setText(font, counter);
            float counterX = (hudViewport.getWorldWidth() - layout.width)/ 2 - PADDING;
            font.draw(batch, layout, counterX, y-40f);
        }

        font.setColor(Color.ORANGE);
        String fuel = "FUEL: " + GameManager.INSTANCE.getFuel();
        layout.setText(font, fuel);
        float fuelX = hudViewport.getWorldWidth() - layout.width - PADDING;
        font.draw(batch,layout,fuelX,y);

        if (GameManager.INSTANCE.isGameOver()) {
            font.setColor(Color.RED);
            layout.setText(font, "THE END");
            float endX = (hudViewport.getWorldWidth() + layout.width) / 2 - layout.width;
            float endY = (hudViewport.getWorldHeight() + layout.height) / 2 - layout.height;
            font.draw(batch, layout, endX, endY);
        }

        batch.end();
    }

}
