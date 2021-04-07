package at.kaindorf.game;

import at.kaindorf.beans.*;
import at.kaindorf.enums.AttackCardTypes;
import at.kaindorf.enums.DefenseCardTypes;
import at.kaindorf.enums.RessourceCardTypes;
import at.kaindorf.enums.RessourceTypes;
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

    public PlayGame() {
        player = new Player(200, new LinkedList<Card>() , new LinkedList<Card>(), 15, 0, 5);
        bot = new Bot(200, new LinkedList<Card>());
        game = new GameBeans(player,bot,0);
        startNextRound();
    }

    /**
     * Am Anfang der Runde (Spieler bekommt Handkarten und aktuelle Runde wird erhöht)
     */
    public void startNextRound()
    {
        game.setAktRound(game.getAktRound()+1);

        List<Card> handCards = new LinkedList<>();
        handCards.add(randomResCard());
        handCards.add(randomResCard());
        handCards.add(randomDefCard());
        handCards.add(randomAttackCard());

        //Zufall ob es am Anfang zwei AttackKarten oder zwei DefKarten geben soll
        Random rand = new Random();
        int zz = rand.nextInt(2)+1;

        if(zz==1)
        {
            handCards.add(randomDefCard());
        }
        else
        {
            handCards.add(randomAttackCard());
        }

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
}
