package at.kaindorf.beans;

import at.kaindorf.enums.DefenseCardTypes;

/**
 * @author: Matthias Gürtler
 * @date: 24.03.2021
 * @project: KingOfTerromia
 */
public class DefenseCard extends Card{
    private int defense;
    private DefenseCardTypes defenseCardType;


    public DefenseCard(int costWood, int costStone, int costFood, int defense, DefenseCardTypes defenseCardType) {
        super(costWood, costStone, costFood);
        this.defense = defense;
        this.defenseCardType = defenseCardType;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public DefenseCardTypes getDefenseCardType() {
        return defenseCardType;
    }

    public void setDefenseCardType(DefenseCardTypes defenseCardType) {
        this.defenseCardType = defenseCardType;
    }
}
