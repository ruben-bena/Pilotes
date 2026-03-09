package com.rubenbellido.viewport;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
// Exemple amb ApplicationAdapter, però podria ser una Screen
public class Pilotes extends ApplicationAdapter {
    private SpriteBatch batch;
    public FitViewport viewport;
    Texture pilotaTexture;
    float posx, posy, velx, vely;
    float ballWidth = 1f;
    float ballHeight = 1f;
    int screenWidth = 8;
    int screenHeight = 5;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(screenWidth, screenHeight); // 8x5 unitats (amplada x alçada)

        // Creem la Texture de la pilota (format en pixels! , de moment)
        Pixmap pilotaPixmap = new Pixmap(100,100,Pixmap.Format.RGBA8888);
        pilotaPixmap.setColor(Color.RED);
        pilotaPixmap.fillCircle(50,50,50); // posx, posy, radi
        pilotaTexture = new Texture(pilotaPixmap);

        posx = posy = 0f;
        velx = vely = 2f; // velocitat d'1 m/s
    }

    @Override
    public void render() {
        // 1- INPUTS (controls usuari)...

        // 2- CALCULEM
        // primera prova: increment 0.01mts per iteració
        float delta = Gdx.graphics.getDeltaTime();
        posx = posx + velx * delta;
        posy = posy + vely * delta;

        if (posx + ballWidth > screenWidth || posx < 0) {
            velx *= -1;
        }
        if (posy + ballHeight > screenHeight || posy < 0) {
            vely *= -1;
        }

        // 3 - PINTAR
        // pintem background de color
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        // PUNT CLAU: ajustem càmera per traduir resolucions quan pintem
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(pilotaTexture,posx,posy,1f,1f);
        batch.end();
    }

    // SUPER IMPORTANT, sense resize no funciona el Viewport
    // és un error típic oblidar aquest mètode, esteu avisats
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
