package at.kaindorf.beans;

/**
 * @author: Matthias Gürtler
 * @date: 24.03.2021
 * @project: KingOfTerromia
 */
public class GameBeans {
    private Player player;
    private Bot bot;
    private int aktRound;

    public GameBeans(Player player, Bot bot, int aktRound) {
        this.player = player;
        this.bot = bot;
        this.aktRound = aktRound;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public int getAktRound() {
        return aktRound;
    }

    public void setAktRound(int aktRound) {
        this.aktRound = aktRound;
    }
}
