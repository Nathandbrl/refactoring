package fr.efrei.transfret;

import java.util.List;

public class Camion extends Vehicule {

    static final double TEMPERATURE_REFRIGERATION = 4;

    protected boolean adr;

    public Camion(String immatriculation, double capaciteMax, boolean adr) {
        super(immatriculation, capaciteMax);
        this.adr = adr;
    }

    @Override
    public boolean peutPrendreEnCharge(ModeTransport mode, List<Colis> colis) {
        if (mode != ModeTransport.ROUTE || contient(colis, TypeMarchandise.REFRIGERE)) {
            return false;
        }
        return !contient(colis, TypeMarchandise.DANGEREUX) || adr;
    }

    @Override
    public void partir(int distance, List<Colis> colis) {
        System.out.println("Plein effectué pour " + immatriculation);
        System.out.println("Le camion " + immatriculation + " roule sur " + distance + " km");
    }

    protected static boolean contient(List<Colis> colis, TypeMarchandise type) {
        return colis.stream().anyMatch(c -> c.getType() == type);
    }
}
