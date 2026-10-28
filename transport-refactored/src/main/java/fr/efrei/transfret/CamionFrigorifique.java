package fr.efrei.transfret;

import java.util.List;

public class CamionFrigorifique extends Vehicule {

    public CamionFrigorifique(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax);
    }

    @Override
    public boolean peutPrendreEnCharge(ModeTransport mode, List<Colis> colis) {
        return mode == ModeTransport.ROUTE && !colis.isEmpty()
                && colis.stream().allMatch(c -> c.getType() == TypeMarchandise.REFRIGERE);
    }

    @Override
    public void partir(int distance, List<Colis> colis) {
        System.out.println("Plein effectué pour " + immatriculation);
        System.out.println("Le camion " + immatriculation + " roule sur " + distance + " km");
        System.out.println("Réfrigération activée à " + Camion.TEMPERATURE_REFRIGERATION
                + "°C sur " + immatriculation);
    }
}
