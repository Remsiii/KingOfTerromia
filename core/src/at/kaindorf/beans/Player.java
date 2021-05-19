package at.kaindorf.beans;

import java.util.List;

/**
 * @author: Matthias Gürtler
 * @date: 24.03.2021
 * @project: KingOfTerromia
 */
public class Player {
    private int hp;
    private List<Card> playerCardsRes;
    private List<Card> playerCardsAttack;
    private List<Card> playerCardsDef;
    private List<Card> handCards;
    private int aktWood;
    private int aktStone;
    private int aktFood;

    public Player(int hp, List<Card> playerCardsRes, List<Card> playerCardsAttack, List<Card> playerCardsDef, List<Card> handCards, int aktWood, int aktStone, int aktFood) {
        this.hp = hp;
        this.playerCardsRes = playerCardsRes;
        this.playerCardsAttack = playerCardsAttack;
        this.playerCardsDef = playerCardsDef;
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

    public List<Card> getPlayerCardsRes() {
        return playerCardsRes;
    }

    public void setPlayerCardsRes(List<Card> playerCardsRes) {
        this.playerCardsRes = playerCardsRes;
    }

    public List<Card> getPlayerCardsAttack() {
        return playerCardsAttack;
    }

    public void setPlayerCardsAttack(List<Card> playerCardsAttack) {
        this.playerCardsAttack = playerCardsAttack;
    }

    public List<Card> getPlayerCardsDef() {
        return playerCardsDef;
    }

    public void setPlayerCardsDef(List<Card> playerCardsDef) {
        this.playerCardsDef = playerCardsDef;
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
