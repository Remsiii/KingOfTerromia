package at.kaindorf.game;

import at.kaindorf.EndScreen;
import at.kaindorf.GameScreen;
import at.kaindorf.KingOfTerromia;
import at.kaindorf.beans.*;
import at.kaindorf.enums.AttackCardTypes;
import at.kaindorf.enums.DefenseCardTypes;
import at.kaindorf.enums.RessourceCardTypes;
import at.kaindorf.enums.RessourceTypes;
import com.badlogic.gdx.graphics.Texture;
import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author: Matthias Gürtler
 * @date: 07.04.2021
 * @project: KingOfTerromia
 */
public class PlayGame {
    private Player player;
    private Bot bot;
    private GameBeans game;
    private boolean won;

    public PlayGame() {
        player = new Player(200, new LinkedList<Card>() , new LinkedList<Card>(), new LinkedList<Card>(), new LinkedList<Card>(), 15, 0, 5);
        bot = new Bot(200, new LinkedList<Card>());
        game = new GameBeans(player,bot,0);
        startNextRound();
    }

    /**
     * Am Anfang der Runde (Spieler bekommt Handkarten und aktuelle Runde wird erhöht)
     */
    public void startNextRound()
    {
        List<Card> aktBotPlayedCards = bot.getAktCards();

        int anzDefPlayer = 0;
        int anzDefBot = 0;
        int anzAttackPlayer = 0;
        int anzAttackBot = 0;

        for (Card card:player.getPlayerCardsAttack()) {
            switch (((AttackCard) card).getAttackCardType())
            {
                case PIKEMEN:
                    anzAttackPlayer+=12;
                    break;
                case ARCHERS:
                    anzAttackPlayer+=15;
            }
        }
        for (Card card:bot.getAktCards()) {
            if(card instanceof AttackCard)
            {
                switch (((AttackCard) card).getAttackCardType())
                {
                    case PIKEMEN:
                        anzAttackBot+=12;
                        break;
                    case ARCHERS:
                        anzAttackBot+=15;
                }
            }

        }

        for (Card card:player.getPlayerCardsDef()) {
            switch (((DefenseCard) card).getDefenseCardType())
            {
                case TOWER:
                    anzDefPlayer+=8;
                    break;
                case WALL:
                    anzDefPlayer+=6;
            }
        }
        for (Card card:bot.getAktCards()) {
            if(card instanceof DefenseCard) {
                switch (((DefenseCard) card).getDefenseCardType()) {
                    case TOWER:
                        anzDefBot += 8;
                        break;
                    case WALL:
                        anzDefBot += 6;
                }
            }
        }
        if(anzAttackBot>anzDefPlayer) {
            if((player.getHp() - (anzAttackBot - anzDefPlayer)) <= 0){
                player.setHp(0);
                won = false;
                KingOfTerromia.INSTANCE.setScreen(new EndScreen(won));
            } else{
                player.setHp(player.getHp() - (anzAttackBot - anzDefPlayer));
            }
        }

        if(anzAttackPlayer>anzDefBot) {
            if((bot.getHp() - (anzAttackPlayer - anzDefBot)) <= 0){
                bot.setHp(0);
                won = true;
                KingOfTerromia.INSTANCE.setScreen(new EndScreen(won));
            } else{
                bot.setHp(bot.getHp() - (anzAttackPlayer - anzDefBot));
            }
        }

        game.setAktRound(game.getAktRound()+1);

        List<Card> handCards = new LinkedList<>();
        Random rand = new Random();
        if(game.getAktRound() == 1)
        {
            handCards.add(new RessourceCard(10,0,0,10, RessourceTypes.WOOD, RessourceCardTypes.LUMBERJACKHUT));
        }
        else if(game.getAktRound() < 10)
        {
            handCards.add(randomResCard());
            handCards.add(randomResCard());
            handCards.add(randomDefCard());
            handCards.add(randomAttackCard());

            //Zufall ob es am Anfang zwei AttackKarten oder zwei DefKarten geben soll
            int zz = rand.nextInt(2)+1;

            if(zz==1)
            {
                handCards.add(randomDefCard());
            }
            else
            {
                handCards.add(randomAttackCard());
            }
        }
        else if(game.getAktRound() >= 10 && game.getAktRound() < 20)
        {
            handCards.add(randomResCard());
            handCards.add(randomDefCard());
            handCards.add(randomAttackCard());

            //Zufall ob Attack oder Defense Karte
            for (int i = 0; i < 2; i++) {
                int zz = rand.nextInt(2)+1;
                if(zz==1)
                {
                    handCards.add(randomDefCard());
                }
                else
                {
                    handCards.add(randomAttackCard());
                }
            }
        }
        else
        {
            handCards.add(randomDefCard());
            handCards.add(randomAttackCard());
            //Zufall ob Attack oder Defense Karte
            for (int i = 0; i < 2; i++) {
                int zz = rand.nextInt(2)+1;
                if(zz==1)
                {
                    handCards.add(randomDefCard());
                }
                else
                {
                    handCards.add(randomAttackCard());
                }
            }
            int zz = rand.nextInt(3)+1;
            switch (zz)
            {
                case 1: handCards.add(randomDefCard());
                    break;
                case 2: handCards.add(randomAttackCard());
                    break;
                default: handCards.add(0,randomResCard());
            }
        }

        //Karten die der Bot spielt
        List<Card> botPlayedCards = new LinkedList<>();
        if(game.getAktRound() == 1)
        {

        }
        else if(game.getAktRound() < 4)
        {
            botPlayedCards.add(randomDefCard());
        }
        else if(game.getAktRound() >= 4 && game.getAktRound() < 7)
        {
            botPlayedCards.add(randomDefCard());
            botPlayedCards.add(randomAttackCard());
        }
        else if(game.getAktRound() >= 7 && game.getAktRound() < 17)
        {
            botPlayedCards.add(randomDefCard());
            botPlayedCards.add(randomAttackCard());
            int zz = rand.nextInt(3)+1;
            switch (zz)
            {
                case 1: botPlayedCards.add(randomDefCard());
                    break;
                case 2: botPlayedCards.add(randomAttackCard());
            }
        }
        else
        {
            botPlayedCards.add(randomDefCard());
            botPlayedCards.add(randomAttackCard());
            int zz = rand.nextInt(2)+1;
            if(zz==1)
            {
                botPlayedCards.add(randomDefCard());
            }
            else
            {
                botPlayedCards.add(randomAttackCard());
            }
            zz = rand.nextInt(3)+1;
            switch (zz)
            {
                case 1: botPlayedCards.add(randomDefCard());
                    break;
                case 2: botPlayedCards.add(randomAttackCard());
            }
        }

        for (Card card:player.getPlayerCardsRes()) {
            switch (((RessourceCard) card).getRessourceCardType())
            {
                 case HUNTERHUT:
                         player.setAktFood(player.getAktFood()+7);
                     break;
                 case LUMBERJACKHUT:
                         player.setAktWood(player.getAktWood()+10);
                     break;
                 case STONEMINE:
                         player.setAktStone(player.getAktStone()+5);
            }
        }

        player.setPlayerCardsDef(new LinkedList<Card>());
        player.setPlayerCardsAttack(new LinkedList<Card>());
        bot.setAktCards(botPlayedCards);
        player.setHandCards(handCards);
    }

    /**
     * zufällige Attack Card
     * @return
     */
    public AttackCard randomAttackCard()
    {
        Random rand = new Random();

        AttackCard attackCard = null;
        int zz = rand.nextInt(2)+1;
        switch (zz) //Switch damit man es schnell erweiter kann (Auswahl welche AttackKarte er bekommt)
        {
            case 1: attackCard = new AttackCard(5,0,10,15,AttackCardTypes.ARCHERS);
                break;
            case 2: attackCard = new AttackCard(6,0,7,12,AttackCardTypes.PIKEMEN);
        }

        return attackCard;
    }

    /**
     * zufällige Defense Card
     * @return
     */
    public DefenseCard randomDefCard()
    {
        Random rand = new Random();

        DefenseCard defenseCard = null;
        int zz = rand.nextInt(2)+1;
        switch (zz) //Switch damit man es schnell erweiter kann (Auswahl welche DefKarte er bekommt)
        {
            case 1: defenseCard = new DefenseCard(5,10,0,8, DefenseCardTypes.TOWER);
                break;
            case 2: defenseCard = new DefenseCard(0,15,0,6,DefenseCardTypes.WALL);
        }

        return defenseCard;
    }

    /**
     * zufällige Ressource Card
     * @return
     */
    public RessourceCard randomResCard()
    {
        Random rand = new Random();

        RessourceCard ressourceCard = null;
        int zz = rand.nextInt(3)+1;
        switch (zz) //Switch damit man es schnell erweiter kann (Auswahl welche AttackKarte er bekommt)
        {
            case 1: ressourceCard = new RessourceCard(10,0,0,7, RessourceTypes.FOOD, RessourceCardTypes.HUNTERHUT);
                break;
            case 2: ressourceCard = new RessourceCard(10,0,0,10, RessourceTypes.WOOD, RessourceCardTypes.LUMBERJACKHUT);
                break;
            case 3: ressourceCard = new RessourceCard(10,0,5,5, RessourceTypes.STONE, RessourceCardTypes.STONEMINE);
        }

        return ressourceCard;
    }

    public Player getPlayer() {
        return player;
    }

    public Bot getBot() {
        return bot;
    }

    public GameBeans getGame() {
        return game;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void setGame(GameBeans game) {
        this.game = game;
    }

    public boolean isWon() {
        return won;
    }
}
