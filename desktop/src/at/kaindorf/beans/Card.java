package at.kaindorf.beans;

/**
 * @author: Matthias Gürtler
 * @date: 24.03.2021
 * @project: KingOfTerromia
 */
public class Card {
    int costWood;
    int costStone;
    int costFood;

    /**
     * ToDo: Das man die Cards Craftet und zu Liste Hinzufügt
     */
    public void build(){

    }

    public Card(int costWood, int costStone, int costFood) {
        this.costWood = costWood;
        this.costStone = costStone;
        this.costFood = costFood;
    }

    public int getCostWood() {
        return costWood;
    }

    public void setCostWood(int costWood) {
        this.costWood = costWood;
    }

    public int getCostStone() {
        return costStone;
    }

    public void setCostStone(int costStone) {
        this.costStone = costStone;
    }

    public int getCostFood() {
        return costFood;
    }

    public void setCostFood(int costFood) {
        this.costFood = costFood;
    }
}
