package at.kaindorf.beans;

import java.util.List;

/**
 * @author: Matthias Gürtler
 * @date: 24.03.2021
 * @project: KingOfTerromia
 */
public class Player {
    private int hp;
    private List<Card> playerCards; //aktuell gecrafteden Karten
    private List<Card> handCards;
    private int aktWood;
    private int aktStone;
    private int aktFood;

    public Player(int hp, List<Card> playerCards, List<Card> handCards, int aktWood, int aktStone, int aktFood) {
        this.hp = hp;
        this.playerCards = playerCards;
        this.handCards = handCards;
        this.aktWood = aktWood;
        this.aktStone = aktStone;
        this.aktFood = aktFood;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public List<Card> getHandCards() {
        return handCards;
    }

    public void setHandCards(List<Card> handCards) {
        this.handCards = handCards;
    }

    public int getAktWood() {
        return aktWood;
    }

    public void setAktWood(int aktWood) {
        this.aktWood = aktWood;
    }

    public int getAktStone() {
        return aktStone;
    }

    public void setAktStone(int aktStone) {
        this.aktStone = aktStone;
    }

    public int getAktFood() {
        return aktFood;
    }

    public void setAktFood(int aktFood) {
        this.aktFood = aktFood;
    }
}
