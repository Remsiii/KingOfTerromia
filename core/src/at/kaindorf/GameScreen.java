package at.kaindorf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends ScreenAdapter {

    SpriteBatch batch;
    Texture imgStonemine;

    public GameScreen() {
        batch = new SpriteBatch();
        imgStonemine = new Texture("cards/attack/archers.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgStonemine, Gdx.graphics.getWidth() - 210.0f, Gdx.graphics.getHeight() - 300.0f, 130.0f, 180.0f);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgStonemine.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }
}
