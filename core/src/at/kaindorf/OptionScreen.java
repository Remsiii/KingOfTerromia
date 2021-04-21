package at.kaindorf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
/**
 * @author: Meister Michael
 * @date: 14.04.2021
 * @project: KingOfTerromia
 */
public class OptionScreen extends ScreenAdapter {
    SpriteBatch batch;
    SelectBox<String> screenMode;
    Skin skin;
    private Stage stage = new Stage();
    Dialog dialog;

    public OptionScreen() {
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        batch = new SpriteBatch();
        screenMode = new SelectBox<String>(skin);
        dialog = new Dialog("Setting",skin);

    }

    @Override
    public void render(float delta) {
        BitmapFont font = new BitmapFont();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font.setColor(Color.RED);
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        stage.draw();
        stage.act(delta);
        batch.end();
    }

    @Override
    public void show() {
        dialog.setSize(500,500);
        dialog.setPosition(Gdx.graphics.getWidth()/2-100,Gdx.graphics.getHeight()/2-100);
        screenMode.setItems("Vollbild","Fenstermodus");

        dialog.getContentTable().defaults().pad(10);
        dialog.getContentTable().add(screenMode);

        stage.addActor(dialog);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }
}
