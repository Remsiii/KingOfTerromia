package at.kaindorf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @author: Meister Michael
 * @date: 24.03.2021
 * @project: KingOfTerromia
 */
public class MenuScreen extends ScreenAdapter {

    SpriteBatch batch;
    Texture img,play,quit,opt;
    private Stage stage = new Stage();
    ImageButton start,option,beenden;
    public static Music music = Gdx.audio.newMusic(Gdx.files.internal("MainMenuOST.mp3"));

    public MenuScreen() {
        music.play();
        music.setLooping(true);
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        play = new Texture("play.png");
        quit = new Texture("quit.png");
        opt = new Texture("opt.png");

    }

    @Override
    public void render(float delta) {
        BitmapFont font = new BitmapFont();

        Gdx.gl.glClearColor(0, 1, 0, 1);
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
        start = new ImageButton(new TextureRegionDrawable(new TextureRegion(play)));
        beenden = new ImageButton(new TextureRegionDrawable(new TextureRegion(quit)));
        option = new ImageButton(new TextureRegionDrawable(new TextureRegion(opt)));

        start.setPosition(800,900);
        start.setSize(405,108);
        option.setPosition(800,700);
        option.setSize(405,108);
        beenden.setPosition(800,500);
        beenden.setSize(405,108);

        stage.addActor(start);
        stage.addActor(beenden);
        stage.addActor(option);
        Gdx.input.setInputProcessor(stage);

        start.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                KingOfTerromia.INSTANCE.setScreen(new GameScreen());
            }
        });

        beenden.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                System.exit(0);
            }
        });

        option.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                KingOfTerromia.INSTANCE.setScreen(new OptionScreen());
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        stage.dispose();
        play.dispose();
        opt.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }


}
