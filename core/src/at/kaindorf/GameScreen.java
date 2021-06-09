package at.kaindorf;

import at.kaindorf.beans.*;
import at.kaindorf.game.PlayGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ArrayMap;
import jdk.javadoc.internal.doclets.toolkit.util.Group;

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
    private List<Texture> playedCardsPlayerRes = new LinkedList<>();
    private List<Texture> playedCardsPlayerDef = new LinkedList<>();
    private List<Texture> playedCardsPlayerAttack = new LinkedList<>();
    private List<Texture> hpBot = new LinkedList<>();
    private List<Texture> hpPlayer = new LinkedList<>();
    private Texture nextRoundButton;
    private List<Texture> round = new LinkedList<>();
    private PlayGame playGame = new PlayGame();
    private ImageButton nextRoundIB;
    private List<ImageButton> handCardsPlayerIB = new LinkedList<>();
    private Stage stage = new Stage();
    private List<Texture> aktFoodList = new LinkedList<>();
    private List<Texture> aktStoneList = new LinkedList<>();
    private List<Texture> aktWoodList = new LinkedList<>();
    private Texture iconFood;
    private Texture iconWood;
    private Texture iconStone;
    private Texture backButton;
    private ImageButton backButtonIB;
    private Skin skin;


    private Texture background;

    public GameScreen() {
        batch = new SpriteBatch();

        nextRoundButton = new Texture("next_Round_button.png");
        backButton = new Texture("zurueck.png");
        iconFood = new Texture("bread.png");
        iconWood = new Texture("wood.png");
        iconStone = new Texture("stone.png");
        hpBot.add(new Texture("rounds/2.png"));
        hpBot.add(new Texture("rounds/0.png"));
        hpBot.add(new Texture("rounds/0.png"));
        hpPlayer.add(new Texture("rounds/2.png"));
        hpPlayer.add(new Texture("rounds/0.png"));
        hpPlayer.add(new Texture("rounds/0.png"));
        round.add(new Texture("rounds/1.png"));
        setPlayCards(playGame.getPlayer().getHandCards(),1);
        setPlayCards(playGame.getBot().getAktCards(),2);
        background = new Texture("Hintergrund.png");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0 , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (int i = 0; i < (""+playGame.getGame().getAktRound()).length(); i++) {
                batch.draw(round.get(i), Gdx.graphics.getWidth()-(50*(i+1)), Gdx.graphics.getHeight()-60,50,50);
        }
        updateHpPlayer();
        updateHpBot();
        updateResourcenAnzeige();
        stage.draw();
        stage.act(delta);

        nextRoundIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(nextRoundButton)));
        nextRoundIB.setSize(300,200);
        nextRoundIB.setPosition(Gdx.graphics.getWidth()-nextRoundIB.getWidth(),65);


        nextRoundIB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                nextRound();
            }
        });

        stage.addActor(nextRoundIB);
        Gdx.input.setInputProcessor(stage);
        printHandCards(delta);
        printBotCards();
        printPlayerCardsAttack();
        printPlayerCardsDef();
        printPlayerCardsRes();
        batch.end();

        /* Zurück Button zum Hauptmenü */
        backButtonIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(backButton)));

        backButtonIB.setSize(300,200);
        backButtonIB.setPosition(Gdx.graphics.getWidth()-backButtonIB.getWidth(),0);

        stage.addActor(backButtonIB);


        backButtonIB.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                /** Pop-Up Fenster **/
                Gdx.input.setInputProcessor(stage = new Stage());
                skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
                com.badlogic.gdx.scenes.scene2d.ui.Dialog dialog = new Dialog("Warning", skin, "dialog") {
                    public void result(Object obj) {
                        System.out.println("result "+obj);
                        if(obj.equals(true))
                        {
                            KingOfTerromia.INSTANCE.setScreen(new MenuScreen());
                        }else if(obj.equals(false))
                        {
                            // nothing should happen
                        }
                    }
                };
                dialog.text("Are you sure you want to quit?");
                dialog.button("Yes", true);
                dialog.button("No", false);
                dialog.key(Input.Keys.ENTER, true);
                stage.addActor(dialog);
                dialog.show(stage);

            }
        });



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
    public void setPlayCards(List<Card> cardsList, int type)
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
                            else if(type==3)
                                playedCardsPlayerAttack.add(new Texture("cards/attack/archers.jpg"));
                        break;
                    case PIKEMEN:
                            if(type==1)
                                handCardsPlayer.add(new Texture("cards/attack/pikemen.jpg"));
                            else if(type==2)
                                playedCardsBot.add(new Texture("cards/attack/pikemen.jpg"));
                            else if(type==3)
                                playedCardsPlayerAttack.add(new Texture("cards/attack/pikemen.jpg"));
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
                            else if(type==3)
                                playedCardsPlayerDef.add(new Texture("cards/defense/tower.jpg"));
                        break;
                    case WALL:
                            if(type==1)
                                handCardsPlayer.add(new Texture("cards/defense/wall.jpg"));
                            else if(type==2)
                                playedCardsBot.add(new Texture("cards/defense/wall.jpg"));
                            else if(type==3)
                                playedCardsPlayerDef.add(new Texture("cards/defense/wall.jpg"));
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
                            else if(type==3)
                                playedCardsPlayerRes.add(new Texture("cards/ressource/hunterhut.jpg"));
                        break;
                    case LUMBERJACKHUT:
                            if(type==1)
                                handCardsPlayer.add(new Texture("cards/ressource/lumberjackhut.jpg"));
                            else if(type==2)
                                playedCardsBot.add(new Texture("cards/ressource/lumberjackhut.jpg"));
                            else if(type==3)
                                playedCardsPlayerRes.add(new Texture("cards/ressource/lumberjackhut.jpg"));
                        break;
                    case STONEMINE:
                            if(type==1)
                                handCardsPlayer.add(new Texture("cards/ressource/stonemine.jpg"));
                            else if(type==2)
                                playedCardsBot.add(new Texture("cards/ressource/stonemine.jpg"));
                            else if(type==3)
                                playedCardsPlayerRes.add(new Texture("cards/ressource/stonemine.jpg"));
                }
            }

        }
    }

    /**
     * Handkarten anzeigen
     */
    public void printHandCards(final float delta)
    {

        int testZahl = - 720;
        int index = 0;
        stage.draw();
        stage.act(delta);
        handCardsPlayerIB.clear();
        for (Texture hcards : handCardsPlayer) {
            testZahl = testZahl + 240;
            if(hcards==null)
                break;
            handCardsPlayerIB.add(index,new ImageButton(new TextureRegionDrawable(new TextureRegion(hcards))));
            handCardsPlayerIB.get(index).setSize(240.0f, 300.0f);
            handCardsPlayerIB.get(index).setPosition(Gdx.graphics.getWidth()/2 - (240.0f/2) + testZahl, 0);
            final int fIndex = index;
            final float fdelta = delta;
            handCardsPlayerIB.get(index).addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y){
                    clickedCard(fIndex, fdelta);
                }
            });

            stage.addActor(handCardsPlayerIB.get(index));
            Gdx.input.setInputProcessor(stage);
            index++;
        }
    }

    /**
     * gespielte Bot Karten anzeigen
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
     * gespielte Attack Karten anzeigen
     */
    public void printPlayerCardsAttack()
    {
        int testZahl = - 720;
        for (Texture hcards : playedCardsPlayerAttack) {
            testZahl = testZahl + 240;
            batch.draw(hcards, Gdx.graphics.getWidth()/2 - (240.0f/2) + testZahl, Gdx.graphics.getHeight()-500.0f, 240.0f, 300.0f);
        }
    }

    /**
     * gespielte Def Karten anzeigen
     */
    public void printPlayerCardsDef()
    {
        int testZahl = - 720;
        for (Texture hcards : playedCardsPlayerDef) {
            testZahl = testZahl + 240;
            batch.draw(hcards, Gdx.graphics.getWidth()/2 - (240.0f/2) + testZahl, Gdx.graphics.getHeight()-700.0f, 240.0f, 300.0f);
        }
    }

    /**
     * gespielte Res Karten anzeigen
     */
    public void printPlayerCardsRes()
    {
        int testZahl = - 720;
        for (Texture hcards : playedCardsPlayerRes) {
            testZahl = testZahl + 240;
            batch.draw(hcards, Gdx.graphics.getWidth()/2 - (240.0f/2) + testZahl, Gdx.graphics.getHeight()-900.0f, 240.0f, 300.0f);
        }
    }

    /**
     * Auf nächste Runde aktualisieren
     */
    public void nextRound()
    {
        stage = new Stage();
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
        for (Texture hcards : playedCardsPlayerDef) {
            hcards.dispose();
        }
        for (Texture hcards : playedCardsPlayerAttack) {
            hcards.dispose();
        }
        for (Texture hcards : playedCardsPlayerRes) {
            hcards.dispose();
        }

        handCardsPlayer.clear();
        playedCardsBot.clear();
        playedCardsPlayerDef.clear();
        playedCardsPlayerAttack.clear();
        playedCardsPlayerRes.clear();

        playGame.startNextRound();

        setPlayCards(playGame.getPlayer().getHandCards(), 1);
        setPlayCards(playGame.getBot().getAktCards(), 2);
        setPlayCards(playGame.getPlayer().getPlayerCardsRes(), 3);
        batch.end();
    }

    /**
     * Spieler HP Anzeige Updaten
     */
    public void updateHpPlayer()
    {

        int aktHp = playGame.getGame().getPlayer().getHp();

        int aktHpCount = aktHp;
        hpPlayer.clear();

        for (int i = 0; i < (""+aktHpCount).length(); i++) {
            int aktNumber = aktHp%10;
            hpPlayer.add(0,new Texture("rounds/"+aktNumber+  ".png"));
            aktHp = aktHp / 10;
        }

        int stelleHp = -40;
        for (Texture texture : hpPlayer) {
            stelleHp += 50;
            batch.draw(texture, stelleHp, 10,50,50);
        }
    }

    /**
     * Bot HP Anzeige Updaten
     */
    public void updateHpBot()
    {
        int aktHp = playGame.getGame().getBot().getHp();

        int aktHpCount = aktHp;
        hpBot.clear();

        for (int i = 0; i < (""+aktHpCount).length(); i++) {
            int aktNumber = aktHp%10;
            hpBot.add(0,new Texture("rounds/"+aktNumber+  ".png"));
            aktHp = aktHp / 10;
        }

        int stelleHp = -40;
        for (Texture texture : hpBot) {
            stelleHp += 50;
            batch.draw(texture, stelleHp, Gdx.graphics.getHeight()-60,50,50);
        }
    }

    /**
     * Update Resourcen Anzeige
     */
    public void updateResourcenAnzeige()
    {
        int aktFood = playGame.getGame().getPlayer().getAktFood();
        int aktStone = playGame.getGame().getPlayer().getAktStone();
        int aktWood = playGame.getGame().getPlayer().getAktWood();

        int aktFoodCount = aktFood;
        int aktStoneCount = aktStone;
        int aktWoodCount = aktWood;

        aktFoodList.clear();
        aktStoneList.clear();
        aktWoodList.clear();

        for (int i = 0; i < (""+aktFoodCount).length(); i++) {
            int aktNumber = aktFood%10;
            aktFoodList.add(0,new Texture("rounds/"+aktNumber+  ".png"));
            aktFood = aktFood / 10;
        }
        for (int i = 0; i < (""+aktStoneCount).length(); i++) {
            int aktNumber = aktStone%10;
            aktStoneList.add(0,new Texture("rounds/"+aktNumber+  ".png"));
            aktStone = aktStone / 10;
        }
        for (int i = 0; i < (""+aktWoodCount).length(); i++) {
            int aktNumber = aktWood%10;
            aktWoodList.add(0,new Texture("rounds/"+aktNumber+  ".png"));
            aktWood = aktWood / 10;
        }

        int stelle = 40;
        batch.draw(iconFood, stelle, Gdx.graphics.getHeight()/2/2+60,50,50);
        stelle += 15;
        for (Texture texture : aktFoodList) {
            stelle += 40;
            batch.draw(texture, stelle, Gdx.graphics.getHeight()/2/2+60,40,40);
        }
        stelle = 40;
        batch.draw(iconStone, stelle, Gdx.graphics.getHeight()/2/2-60,50,50);
        stelle += 15;
        for (Texture texture : aktStoneList) {
            stelle += 40;
            batch.draw(texture, stelle, Gdx.graphics.getHeight()/2/2-60,40,40);
        }
        stelle = 40;
        batch.draw(iconWood, stelle, Gdx.graphics.getHeight()/2/2,50,50);
        stelle += 15;
        for (Texture texture : aktWoodList) {
            stelle += 40;
            batch.draw(texture, stelle, Gdx.graphics.getHeight()/2/2,40,40);
        }
    }

    /**
     * Wenn Karte geklickt wurde
     * @param index
     * @param delta
     */
    public void clickedCard(int index, float delta)
    {
        batch.begin();
        Card card = playGame.getPlayer().getHandCards().get(index);

        if(card.getCostFood() > playGame.getPlayer().getAktFood() || card.getCostStone() > playGame.getPlayer().getAktStone() || card.getCostWood() > playGame.getPlayer().getAktWood())
        {
            /** Pop-Up Fenster **/
            Gdx.input.setInputProcessor(stage);
            skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
            com.badlogic.gdx.scenes.scene2d.ui.Dialog dialog = new Dialog("Warning", skin, "dialog") {
                public void result(Object obj) {
                    System.out.println("result "+obj);
                }
            };
            dialog.text("You have too little resources to build that card");
            dialog.button("I Understand", true);
            dialog.key(Input.Keys.ENTER, true);
            stage.addActor(dialog);
            dialog.show(stage);
        }
        else
        {
            List<Card> aktHandCards = playGame.getPlayer().getHandCards();
            aktHandCards.remove(index);
            playGame.getPlayer().setHandCards(aktHandCards);
            playGame.getPlayer().setAktWood(playGame.getPlayer().getAktWood()-card.getCostWood());
            playGame.getPlayer().setAktFood(playGame.getPlayer().getAktFood()-card.getCostFood());
            playGame.getPlayer().setAktStone(playGame.getPlayer().getAktStone()-card.getCostStone());
            if(card instanceof RessourceCard)
            {
                List<Card> aktPlayerCardsRes = playGame.getPlayer().getPlayerCardsRes();
                aktPlayerCardsRes.add(card);
                playGame.getPlayer().setPlayerCardsRes(aktPlayerCardsRes);
                playedCardsPlayerRes.clear();
                setPlayCards(aktPlayerCardsRes,3);
                printPlayerCardsRes();
            }
            else if(card instanceof AttackCard)
            {
                List<Card> aktPlayerCardsAttack = playGame.getPlayer().getPlayerCardsAttack();
                aktPlayerCardsAttack.add(card);
                playGame.getPlayer().setPlayerCardsAttack(aktPlayerCardsAttack);
                playedCardsPlayerAttack.clear();
                setPlayCards(aktPlayerCardsAttack,3);
                printPlayerCardsAttack();
            }
            else{
                List<Card> aktPlayerCardDef = playGame.getPlayer().getPlayerCardsDef();
                aktPlayerCardDef.add(card);
                playGame.getPlayer().setPlayerCardsDef(aktPlayerCardDef);
                playedCardsPlayerDef.clear();
                setPlayCards(aktPlayerCardDef,3);
                printPlayerCardsDef();
            }

            handCardsPlayer.clear();
            setPlayCards(playGame.getPlayer().getHandCards(), 1);
        }
        batch.end();
        stage.clear();
        render(delta);
    }
}
