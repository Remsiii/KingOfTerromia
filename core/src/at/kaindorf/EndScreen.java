package at.kaindorf;

import at.kaindorf.beans.GameBeans;
import at.kaindorf.game.PlayGame;
import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
/**
 * @author: Meister Michael
 * @date: 02.06.2021
 * @project: KingOfTerromia
 */
public class EndScreen extends ScreenAdapter {

    SpriteBatch batch;
    Texture win,loose, background ,menuTex;
    ImageButton gameLost, gameWon, menuBt;
    Skin skin;
    boolean won;
    private Stage stage = new Stage(new ScreenViewport());

    public EndScreen(Boolean won) {
        menuTex = new Texture("backtomainmenu.png");
        batch = new SpriteBatch();
        win = new Texture("wonText.png");
        loose = new Texture("deadText.png");
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        this.won = won;
    }

    @Override
    public void render(float delta) {
        batch.begin();
        stage.getBatch().begin();
        BitmapFont font = new BitmapFont();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font.setColor(Color.BLACK);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();
        stage.act(delta);
        batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        if(won){
            background = new Texture("endWallpaper.jpg");
        }else{
            background = new Texture("endWallpaper_death.jpg");
        }

        Table endTable = new Table();
        gameWon = new ImageButton(new TextureRegionDrawable(new TextureRegion(win)));
        gameLost = new ImageButton(new TextureRegionDrawable(new TextureRegion(loose)));
        menuBt = new ImageButton(new TextureRegionDrawable(new TextureRegion(menuTex)));

        menuBt.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                KingOfTerromia.INSTANCE.setScreen(new MenuScreen());
            }
        });

        if(won){
            endTable.add(gameWon).center();
        }else{
            endTable.add(gameLost).center();
        }
        endTable.row();
        endTable.add(menuBt).padTop(Gdx.graphics.getHeight()/10);
        endTable.setFillParent(true);

        stage.addActor(endTable);

    }



    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }
}
