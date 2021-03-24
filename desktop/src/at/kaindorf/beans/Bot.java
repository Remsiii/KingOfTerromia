package at.kaindorf.beans;

import java.util.List;

/**
 * @author: Matthias Gürtler
 * @date: 24.03.2021
 * @project: KingOfTerromia
 */
public class Bot {
    private int hp;
    private List<Card> aktCards; //Aktuelle Karten die bot gecrafted hat

    public Bot(int hp, List<Card> aktCards) {
        this.hp = hp;
        this.aktCards = aktCards;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public List<Card> getAktCards() {
        return aktCards;
    }

    public void setAktCards(List<Card> aktCards) {
        this.aktCards = aktCards;
    }
}
