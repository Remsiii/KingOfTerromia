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
    private SpriteBatch batch;
    private SelectBox<String> screenMode;
    private Skin skin;
    private Stage stage = new Stage(new ScreenViewport());
    private Dialog dialog;
    private Slider musicVol;
    private BitmapFont font = new BitmapFont();
    private TextField volume;
    private Texture mute,noMute, titleTex, background;
    private ImageButton muteBt, title;
    boolean isMute = true;
    public static boolean startW = false;
    private Texture backButton;
    private ImageButton backButtonIB;

    public OptionScreen() {
        backButton = new Texture("pfeil-nach-unten.png");
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
        muteBt = new ImageButton(new TextureRegionDrawable(new TextureRegion(mute)),
                new TextureRegionDrawable(new TextureRegion(mute)),
                new TextureRegionDrawable(new TextureRegion(noMute)));
        title = new ImageButton(new TextureRegionDrawable(new TextureRegion(titleTex)));
    }

    /**
     * Methode zum rendern von Texturen
     * @param delta
     */
    @Override
    public void render(float delta) {
        batch.begin();
        stage.getBatch().begin();
        BitmapFont font = new BitmapFont();

        //Hintergrund setzen
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        stage.getBatch().end();
        stage.draw();
        stage.act(delta);
        batch.end();
    }

    /**
     * Methode die aufgerufen wird, wenn dieser Screen zum aktuellen Screen wird.
     */
    @Override
    public void show() {
        Table optionTable = new Table();

        screenMode.setItems("Fullscreen","Windowmode");
        screenMode.getStyle().listStyle.font.getData().scale(0.3f);

        musicVol.setValue(MenuScreen.music.getVolume());


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

        //Dropdown Liste zum ändern des Anzeigemodus
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

        //Slider zum ändern der Musiklautstärke
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

        //Button um Musik zu muten
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

        /* Zurück Button zum Hauptmenü */

        backButtonIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(backButton)));

        backButtonIB.setPosition(40,10);
        backButtonIB.setSize(105,100);
        stage.addActor(backButtonIB);

        backButtonIB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                MenuScreen.clickEffect.play();
                KingOfTerromia.INSTANCE.setScreen(new MenuScreen());
            }
        });

    }

    /**
     * Methode die aufgerufen wird wenn sich die Größe des Fensters verändert.
     * Die Stage wird auf die entsprechende Größe angepasst.
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    /**
     * Alle ressourcen werden freigegeben und nicht mehr gerendert.
     */
    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }

    /**
     * Wird aufgerufen wenn der Screen gewechselt wird.
     */
    @Override
    public void hide() {
        this.dispose();
    }

}

