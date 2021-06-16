package at.kaindorf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class DifficultyScreen extends ScreenAdapter {

    private String difficulty = "Medium";
    SpriteBatch batch;
    private Stage stage = new Stage(new ScreenViewport());
    Texture background, play, header, diff;
    ImageButton playBT,headerBT;
    private SelectBox<String> difficultyBox;
    Skin skin;
    private Texture backButton;
    private ImageButton backButtonIB;

    public DifficultyScreen() {
        batch = new SpriteBatch();
        background = new Texture("MenuScreenBackground.jpg");
        play = new Texture("play.png");
        header = new Texture("difficulty.png");
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        difficultyBox = new SelectBox<String>(skin);
        backButton = new Texture("pfeil-nach-unten.png");
    }

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

    @Override
    public void show() {
        difficultyBox.setItems("Easy","Medium","Hard");
        difficultyBox.setSelected("Medium");
        difficultyBox.getStyle().listStyle.font.getData().scale(0.3f);
        Gdx.input.setInputProcessor(stage);
        playBT = new ImageButton(new TextureRegionDrawable(new TextureRegion(play)));
        headerBT = new ImageButton(new TextureRegionDrawable(new TextureRegion(header)));
        Table tableDiff = new Table();
        tableDiff.row();
        tableDiff.add(headerBT).padTop(Gdx.graphics.getHeight()/10);
        tableDiff.row();
        tableDiff.add(difficultyBox).padTop(Gdx.graphics.getHeight()/10);
        tableDiff.row();
        tableDiff.add(playBT).padTop(Gdx.graphics.getHeight()/10);
        tableDiff.setFillParent(true);

        difficultyBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuScreen.clickEffect.play();
                if(difficultyBox.getSelected().equals("Easy")) {
                    difficulty = "Easy";
                } else if(difficultyBox.getSelected().equals("Medium")){
                    difficulty = "Medium";
                } else {
                    difficulty = "Hard";
                }
            }
        });

        playBT.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MenuScreen.clickEffect.play();
                KingOfTerromia.INSTANCE.setScreen(new GameScreen(difficulty));
            }
        });
        stage.addActor(tableDiff);

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



}
