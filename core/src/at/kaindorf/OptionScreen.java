package at.kaindorf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * @author: Meister Michael
 * @date: 14.04.2021
 * @project: KingOfTerromia
 */
public class OptionScreen extends ScreenAdapter {
    SpriteBatch batch;
    SelectBox<String> screenMode;
    Skin skin;
    private Stage stage = new Stage(new ScreenViewport());
    Dialog dialog;
    Slider musicVol;
    BitmapFont font = new BitmapFont();
    TextField volume;
    Texture mute,noMute, titleTex, background;
    ImageButton muteBt, title;
    boolean isMute = true;
    public static boolean startW = false;
    public OptionScreen() {
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        batch = new SpriteBatch();
        screenMode = new SelectBox<String>(skin);
        dialog = new Dialog("Setting",skin);
        musicVol = new Slider(0,1,0.01f,false,skin);
        volume = new TextField("",skin);
        mute = new Texture("mute.png");
        noMute = new Texture("noMute.png");
        titleTex = new Texture("optionsHeading.png");
        background = new Texture("optionScreenBackground.jpg");
    }

    @Override
    public void render(float delta) {
        batch.begin();
        stage.getBatch().begin();


        BitmapFont font = new BitmapFont();
        font.setColor(Color.BLACK);
        //SpriteBatch batch = new SpriteBatch();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();
        stage.act(delta);
        batch.end();
    }

    @Override
    public void show() {
        Table optionTable = new Table();
        muteBt = new ImageButton(new TextureRegionDrawable(new TextureRegion(mute)),
                new TextureRegionDrawable(new TextureRegion(mute)), new TextureRegionDrawable(new TextureRegion(noMute)));
        title = new ImageButton(new TextureRegionDrawable(new TextureRegion(titleTex)));
        screenMode.setItems("Fullscreen","Windowmode");
        screenMode.getStyle().listStyle.font.getData().scale(0.3f);
        musicVol.setValue(1);

        //Table zum Anzeigen der Elemente
        optionTable.add(title).colspan(3).center();
        optionTable.row();
        optionTable.add(screenMode).padTop(Gdx.graphics.getHeight()/25).colspan(3);
        optionTable.row();
        optionTable.add(volume).padTop(Gdx.graphics.getHeight()/25).padRight(Gdx.graphics.getHeight()/25);
        optionTable.add(musicVol).padTop(Gdx.graphics.getHeight()/25).padRight(Gdx.graphics.getHeight()/25);
        optionTable.add(muteBt).width(muteBt.getWidth()*0.1f).height(muteBt.getHeight()*0.1f)
                .padTop(Gdx.graphics.getHeight()/25).padRight(Gdx.graphics.getHeight()/25);
        optionTable.setFillParent(true);

        volume.setDisabled(true);
        volume.setText(String.format("%.0f%%",musicVol.getValue()*100));
        screenMode.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuScreen.clickEffect.play();
                if(screenMode.getSelected().equals("Fullscreen")){
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                }else{
                    Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                    stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                }
            }
        });

        musicVol.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(musicVol.isDragging()) {
                    if(!muteBt.isChecked()){
                        MenuScreen.music.setVolume(musicVol.getValue());
                    }
                    volume.setText(String.format("%.0f%%",musicVol.getValue()*100));
                }

            }
        });

        muteBt.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                MenuScreen.clickEffect.play();
                isMute = !isMute;
                if(isMute){
                    muteBt.setChecked(false);
                    MenuScreen.music.setVolume(musicVol.getValue());
                }else{
                    muteBt.setChecked(true);
                    MenuScreen.music.setVolume(0);

                }
            }
        });

        stage.addActor(optionTable);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }



}

