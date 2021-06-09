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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class RuleScreen extends ScreenAdapter {

    private Texture background, menuTex;
    private ImageButton menuBt;
    SpriteBatch batch = new SpriteBatch();
    private Stage stage = new Stage(new ScreenViewport());

    public RuleScreen() {
        menuTex = new Texture("backtomainmenu.png");
        background = new Texture("rules.png");
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
        menuBt = new ImageButton(new TextureRegionDrawable(menuTex));

        menuBt.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                KingOfTerromia.INSTANCE.setScreen(new MenuScreen());
            }
        });

        stage.addActor(menuBt);
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
