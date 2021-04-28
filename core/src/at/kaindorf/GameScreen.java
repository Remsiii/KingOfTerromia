package at.kaindorf;

import at.kaindorf.beans.*;
import at.kaindorf.enums.AttackCardTypes;
import at.kaindorf.game.PlayGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private List<Texture> handCardsPlayer = new LinkedList<>();
    private List<Texture> playedCardsBot = new LinkedList<>();
    private List<Texture> hpBot = new LinkedList<>();
    private List<Texture> hpPlayer = new LinkedList<>();
    private Texture nextRoundButton;
    private List<Texture> round = new LinkedList<>();
    private PlayGame playGame = new PlayGame();
    private ImageButton nextRoundIB;
    private Stage stage = new Stage();

    public GameScreen() {
        batch = new SpriteBatch();
        nextRoundButton = new Texture("badlogic.jpg");
        hpBot.add(new Texture("rounds/2.png"));
        hpBot.add(new Texture("rounds/0.png"));
        hpBot.add(new Texture("rounds/0.png"));
        hpPlayer.add(new Texture("rounds/2.png"));
        hpPlayer.add(new Texture("rounds/0.png"));
        hpPlayer.add(new Texture("rounds/0.png"));
        round.add(new Texture("rounds/1.png"));
        setHandCards(playGame.getPlayer().getHandCards(),1);
        setHandCards(playGame.getBot().getAktCards(),2);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (int i = 0; i < (""+playGame.getGame().getAktRound()).length(); i++) {
            batch.draw(round.get(i), Gdx.graphics.getWidth()-(50*(i+1)), Gdx.graphics.getHeight()-50,50,50);
        }
        int stelleHp = -50;
        for (Texture texture : hpPlayer) {
            stelleHp += 50;
            batch.draw(texture, stelleHp, 0,50,50);
        }
        stelleHp = -50;
        for (Texture texture : hpPlayer) {
            stelleHp += 50;
            batch.draw(texture, stelleHp, Gdx.graphics.getHeight()-50,50,50);
        }
        stage.draw();
        stage.act(delta);
        nextRoundIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(nextRoundButton)));
        nextRoundIB.setPosition(Gdx.graphics.getWidth()-405,0);
        nextRoundIB.setSize(405,108);
        nextRoundIB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                nextRound();
            }
        });
        stage.addActor(nextRoundIB);
        Gdx.input.setInputProcessor(stage);
        printHandCards();
        printBotCards();
        batch.end();
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

    /**
     * Handkarten setzten
     */
    public void setHandCards(List<Card> cardsList, int type)
    {
        //Schauen welche Handkarten er hat und diese auswählen
        for (Card card: cardsList) {
            if(card instanceof AttackCard) {
                switch (((AttackCard) card).getAttackCardType()) {
                    case ARCHERS:
                            if(type==1)
                                handCardsPlayer.add(new Texture("cards/attack/archers.jpg"));
                            else if(type==2)
                                playedCardsBot.add(new Texture("cards/attack/archers.jpg"));
                        break;
                    case PIKEMEN:
                            if(type==1)
                                handCardsPlayer.add(new Texture("cards/attack/pikemen.jpg"));
                            else if(type==2)
                                playedCardsBot.add(new Texture("cards/attack/pikemen.jpg"));
                }
            }
            else if(card instanceof DefenseCard)
            {
                switch (((DefenseCard) card).getDefenseCardType())
                {
                    case TOWER:
                            if(type==1)
                                handCardsPlayer.add(new Texture("cards/defense/tower.jpg"));
                            else if(type==2)
                                playedCardsBot.add(new Texture("cards/defense/tower.jpg"));
                        break;
                    case WALL:
                            if(type==1)
                                handCardsPlayer.add(new Texture("cards/defense/wall.jpg"));
                            else if(type==2)
                                playedCardsBot.add(new Texture("cards/defense/wall.jpg"));
                }
            }
            else
            {
                switch (((RessourceCard) card).getRessourceCardType())
                {
                    case HUNTERHUT:
                            if(type==1)
                                handCardsPlayer.add(new Texture("cards/ressource/hunterhut.jpg"));
                            else if(type==2)
                                playedCardsBot.add(new Texture("cards/ressource/hunterhut.jpg"));
                        break;
                    case LUMBERJACKHUT:
                            if(type==1)
                                handCardsPlayer.add(new Texture("cards/ressource/lumberjackhut.jpg"));
                            else if(type==2)
                                playedCardsBot.add(new Texture("cards/ressource/lumberjackhut.jpg"));
                        break;
                    case STONEMINE:
                            if(type==1)
                                handCardsPlayer.add(new Texture("cards/ressource/stonemine.jpg"));
                            else if(type==2)
                                playedCardsBot.add(new Texture("cards/ressource/stonemine.jpg"));
                }
            }

        }
    }

    /**
     * Handkarten anzeigen
     */
    public void printHandCards()
    {
        int testZahl = - 720;
        for (Texture hcards : handCardsPlayer) {
            testZahl = testZahl + 240;
            batch.draw(hcards, Gdx.graphics.getWidth()/2 - (240.0f/2) + testZahl, Gdx.graphics.getHeight()/2 - 300.0f, 240.0f, 300.0f);
        }
    }

    /**
     * Handkarten anzeigen
     */
    public void printBotCards()
    {
        int testZahl = - 720;
        for (Texture hcards : playedCardsBot) {
            testZahl = testZahl + 240;
            batch.draw(hcards, Gdx.graphics.getWidth()/2 - (240.0f/2) + testZahl, Gdx.graphics.getHeight()-350.0f, 240.0f, 300.0f);
        }
    }

    /**
     * Auf nächste Runde aktualisieren
     */
    public void nextRound()
    {
        int aktRound = playGame.getGame().getAktRound();
        aktRound++;

        int aktRoundCount = aktRound;

        round.clear();

        batch.begin();
        for (int i = 0; i < (""+aktRoundCount).length(); i++) {
            int aktNumber = aktRound%10;
            round.add(new Texture("rounds/"+aktNumber+  ".png"));
            aktRound = aktRound / 10;
        }

        for (Texture hcards : handCardsPlayer) {
            hcards.dispose();
        }
        for (Texture hcards : playedCardsBot) {
            hcards.dispose();
        }

        handCardsPlayer.clear();
        playedCardsBot.clear();

        playGame.startNextRound();
        setHandCards(playGame.getPlayer().getHandCards(), 1);
        setHandCards(playGame.getBot().getAktCards(), 2);
        batch.end();
    }
}
