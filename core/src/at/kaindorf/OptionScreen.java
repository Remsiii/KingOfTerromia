package at.kaindorf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    SelectBox<String> resolution;
    Skin skin;
    private Stage stage = new Stage();
    Dialog dialog;
    private Texture backButton;
    private ImageButton backButtonIB;

    public OptionScreen() {
        backButton = new Texture("zurueck.png");
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        batch = new SpriteBatch();
        screenMode = new SelectBox<String>(skin);
        resolution = new SelectBox<String>(skin);
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
        dialog.setSize(Gdx.graphics.getWidth()/2+100,Gdx.graphics.getHeight()/2+100);
        dialog.setPosition(Gdx.graphics.getWidth()/2 - dialog.getWidth()/2,Gdx.graphics.getHeight()/2 - dialog.getHeight()/2);
        screenMode.setItems("Vollbild","Fenstermodus");
        resolution.setItems(Gdx.graphics.getWidth()+"x"+Gdx.graphics.getHeight(),
                String.format("%.0fx%.0f",Gdx.graphics.getWidth()*0.8333333333333333,Gdx.graphics.getHeight()*0.8333333333333333),
                String.format("%.0fx%.0f",(Gdx.graphics.getWidth()*0.8333333333333333)*0.8,(Gdx.graphics.getHeight()*0.8333333333333333)*0.8));

        dialog.getContentTable().defaults().pad(10);
        dialog.getContentTable().add(screenMode);
        dialog.getContentTable().add(resolution);

        screenMode.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(screenMode.getSelected().equals("Vollbild")){
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }else{
                    Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                }
            }
        });

        resolution.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String[] res = resolution.getSelected().split("x");
                System.out.println(String.format("%s %s",res[0],res[1]));
                if(screenMode.getSelected().equals("Fenstermodus")){
                    Gdx.graphics.setWindowedMode(Integer.parseInt(res[0]),Integer.parseInt(res[1]));
                    stage.getViewport().update(Integer.parseInt(res[0]),Integer.parseInt(res[1]), true);
                }else{

                    stage.getViewport().update(Integer.parseInt(res[0]),Integer.parseInt(res[1]), true);
                }
            }
        });



        stage.addActor(dialog);
        Gdx.input.setInputProcessor(stage);

        /* Zurück Button zum Hauptmenü */
        backButtonIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(backButton)));

        backButtonIB.setPosition(40,0);
        backButtonIB.setSize(405,108);
        stage.addActor(backButtonIB);

        backButtonIB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                KingOfTerromia.INSTANCE.setScreen(new MenuScreen());
            }
        });

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
