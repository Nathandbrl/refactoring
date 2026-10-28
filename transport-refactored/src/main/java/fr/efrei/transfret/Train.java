package fr.efrei.transfret;

import java.util.List;

public class Train extends Vehicule {

    public Train(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax);
    }

    @Override
    public boolean peutPrendreEnCharge(ModeTransport mode, List<Colis> colis) {
        return mode == ModeTransport.RAIL;
    }

    @Override
    public void partir(int distance, List<Colis> colis) {
        System.out.println("Le train " + immatriculation + " circule sur " + distance + " km de voies ferrées");
    }

}
