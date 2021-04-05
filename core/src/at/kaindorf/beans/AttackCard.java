package at.kaindorf.beans;

import at.kaindorf.enums.AttackCardTypes;

/**
 * @author: Matthias Gürtler
 * @date: 24.03.2021
 * @project: KingOfTerromia
 */
public class AttackCard extends Card {
    private int damage;
    private AttackCardTypes attackCardType;

    public AttackCard(int costWood, int costStone, int costFood, int damage, AttackCardTypes attackCardType) {
        super(costWood, costStone, costFood);
        this.damage = damage;
        this.attackCardType = attackCardType;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public AttackCardTypes getAttackCardType() {
        return attackCardType;
    }

    public void setAttackCardType(AttackCardTypes attackCardType) {
        this.attackCardType = attackCardType;
    }
}
