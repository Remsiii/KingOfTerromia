package at.kaindorf.beans;

import at.kaindorf.enums.RessourceCardTypes;
import at.kaindorf.enums.RessourceTypes;

/**
 * @author: Matthias Gürtler
 * @date: 24.03.2021
 * @project: KingOfTerromia
 */
public class RessourceCard extends Card{
    private int ressourcesPerRound;
    private RessourceTypes ressourceType;
    private RessourceCardTypes ressourceCardType;

    public RessourceCard(int costWood, int costStone, int costFood, int ressourcesPerRound, RessourceTypes ressourceType, RessourceCardTypes ressourceCardType) {
        super(costWood, costStone, costFood);
        this.ressourcesPerRound = ressourcesPerRound;
        this.ressourceType = ressourceType;
        this.ressourceCardType = ressourceCardType;
    }

    public int getRessourcesPerRound() {
        return ressourcesPerRound;
    }

    public void setRessourcesPerRound(int ressourcesPerRound) {
        this.ressourcesPerRound = ressourcesPerRound;
    }

    public RessourceTypes getRessourceType() {
        return ressourceType;
    }

    public void setRessourceType(RessourceTypes ressourceType) {
        this.ressourceType = ressourceType;
    }

    public RessourceCardTypes getRessourceCardType() {
        return ressourceCardType;
    }

    public void setRessourceCardType(RessourceCardTypes ressourceCardType) {
        this.ressourceCardType = ressourceCardType;
    }
}
