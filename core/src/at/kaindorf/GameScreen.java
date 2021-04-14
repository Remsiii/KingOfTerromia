package at.kaindorf;

import at.kaindorf.beans.*;
import at.kaindorf.enums.AttackCardTypes;
import at.kaindorf.game.PlayGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: Matthias Gürtler
 * @date: 07.04.2021
 * @project: KingOfTerromia
 */
public class GameScreen extends ScreenAdapter {

    SpriteBatch batch;
    private List<Texture> handCards = new LinkedList<>();
    private Texture nextRoundButton;
    private Texture round;
    private PlayGame playGame = new PlayGame();

    public GameScreen() {
        batch = new SpriteBatch();
        nextRoundButton = new Texture("badlogic.jpg");
        round = new Texture("rounds/1.jpg");
        setHandCards();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(nextRoundButton, Gdx.graphics.getWidth()-nextRoundButton.getWidth(), 0);
        batch.draw(round, Gdx.graphics.getWidth()-50, Gdx.graphics.getHeight()-50,50,50);
        /**
        nextRoundButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                nextRound();
            }
        });
         **/
        printHandCards();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Texture hcards : handCards) {
            hcards.dispose();
        }
    }

    @Override
    public void hide() {
        this.dispose();
    }

    public void setHandCards()
    {


        //Schauen welche Handkarten er hat und diese auswählen
        for (Card card: playGame.getPlayer().getHandCards()) {
            if(card instanceof AttackCard) {
                switch (((AttackCard) card).getAttackCardType()) {
                    case ARCHERS:
                        handCards.add(new Texture("cards/attack/archers.png"));
                        break;
                    case PIKEMEN:
                        handCards.add(new Texture("cards/attack/pikemen.png"));
                }
            }
            else if(card instanceof DefenseCard)
            {
                switch (((DefenseCard) card).getDefenseCardType())
                {
                    case TOWER: handCards.add(new Texture("cards/defense/tower.png"));
                        break;
                    case WALL: handCards.add(new Texture("cards/defense/wall.png"));
                }
            }
            else
            {
                switch (((RessourceCard) card).getRessourceCardType())
                {
                    case HUNTERHUT: handCards.add(new Texture("cards/ressource/hunterhut.png"));
                        break;
                    case LUMBERJACKHUT: handCards.add(new Texture("cards/ressource/lumberjackhut.png"));
                        break;
                    case STONEMINE: handCards.add(new Texture("cards/ressource/stonemine.png"));
                }
            }

        }
    }

    public void printHandCards()
    {
        int testZahl = - 720;
        for (Texture hcards : handCards) {
            testZahl = testZahl + 240;
            batch.draw(hcards, Gdx.graphics.getWidth()/2 - (240.0f/2) + testZahl, Gdx.graphics.getHeight()/2 - 300.0f, 240.0f, 300.0f);
        }
    }

    public void nextRound()
    {
        int aktRound = playGame.getGame().getAktRound();
        aktRound++;
        playGame.getGame().setAktRound(aktRound);
        round = new Texture("rounds/"+aktRound+".jpg");
        batch.begin();
        batch.draw(round, Gdx.graphics.getWidth()-50, Gdx.graphics.getHeight()-50,50,50);
        batch.end();
    }

}
