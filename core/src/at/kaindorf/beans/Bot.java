package at.kaindorf.beans;

import at.kaindorf.enums.BotDifficult;

import java.util.List;

/**
 * @author: Matthias Gürtler
 * @date: 24.03.2021
 * @project: KingOfTerromia
 */
public class Bot {
    private int hp;
    private List<Card> aktCards; //Aktuelle Karten die bot gecrafted hat
    private BotDifficult botDifficult;

    public Bot(int hp, List<Card> aktCards, BotDifficult botDifficult) {
        this.hp = hp;
        this.aktCards = aktCards;
        this.botDifficult = botDifficult;
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

    public BotDifficult getBotDifficult() {
        return botDifficult;
    }

    public void setBotDifficult(BotDifficult botDifficult) {
        this.botDifficult = botDifficult;
    }
}
