package fr.efrei.transfret;

import java.util.List;

public class Peniche extends Vehicule {

    public Peniche(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax);
    }

    @Override
    public boolean peutPrendreEnCharge(ModeTransport mode, List<Colis> colis) {
        return mode == ModeTransport.FLUVIAL
                && colis.stream().noneMatch(c -> c.getType() == TypeMarchandise.REFRIGERE);
    }

    @Override
    public void partir(int distance, List<Colis> colis) {
        System.out.println("La péniche " + immatriculation + " navigue sur " + distance + " km de voies fluviales");
    }
}
