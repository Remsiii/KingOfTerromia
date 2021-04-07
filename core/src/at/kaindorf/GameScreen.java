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

    public GameScreen() {
        batch = new SpriteBatch();
        PlayGame playGame = new PlayGame();

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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        int testZahl = 10;
        for (Texture hcards : handCards) {
            testZahl = testZahl + 40;
            batch.draw(hcards, Gdx.graphics.getWidth() - 210.0f, Gdx.graphics.getHeight() - 300.0f, 130.0f + testZahl, 180.0f);
        }
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


}
