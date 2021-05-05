package at.kaindorf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
    Slider musicVol;
    BitmapFont font = new BitmapFont();
    TextField volume;
    Texture mute,noMute;
    ImageButton muteBt;
    boolean isMute = false;

    public OptionScreen() {
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        batch = new SpriteBatch();
        screenMode = new SelectBox<String>(skin);
        dialog = new Dialog("Setting",skin);
        musicVol = new Slider(0,1,0.01f,false,skin);
        volume = new TextField("",skin);
        mute = new Texture("mute.png");
    }

    @Override
    public void render(float delta) {
        BitmapFont font = new BitmapFont();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font.setColor(Color.BLACK);
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        stage.draw();
        stage.act(delta);
        batch.end();
    }

    @Override
    public void show() {
        //dialog.setSize(Gdx.graphics.getWidth()/2+100,Gdx.graphics.getHeight()/2+100);
        //dialog.setPosition(Gdx.graphics.getWidth()/2 - dialog.getWidth()/2,Gdx.graphics.getHeight()/2 - dialog.getHeight()/2);
        muteBt = new ImageButton(new TextureRegionDrawable(new TextureRegion(mute)));

        screenMode.setItems("Fullscreen","Windowmode");
        screenMode.getStyle().listStyle.font.getData().scale(0.5f);
        screenMode.setSize(320,60);
        screenMode.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        musicVol.setPosition(Gdx.graphics.getWidth()/2.5f,Gdx.graphics.getHeight()/2);
        volume.setPosition(Gdx.graphics.getWidth()/3.2f,Gdx.graphics.getHeight()/2);
        musicVol.setValue(1);
        //dialog.getContentTable().defaults().pad(10);
        //dialog.getContentTable().add(screenMode);
        volume.setDisabled(true);
        volume.setText(String.format("%.0f%%",musicVol.getValue()*100));
        screenMode.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(screenMode.getSelected().equals("Fullscreen")){
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }else{
                    Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                }
            }
        });

        musicVol.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(musicVol.isDragging()) {
                    MenuScreen.music.setVolume(musicVol.getValue());
                    volume.setText(String.format("%.0f%%",musicVol.getValue()*100));
                }

            }
        });

        muteBt.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                isMute = !isMute;
                if(isMute){
                    muteBt.setChecked(false);
                }else{
                    muteBt.setChecked(true);
                }
            }
        });

        stage.addActor(volume);
        stage.addActor(screenMode);
        stage.addActor(musicVol);
        stage.addActor(muteBt);
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
