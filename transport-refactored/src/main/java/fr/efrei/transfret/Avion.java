package fr.efrei.transfret;

import java.util.List;

public class Avion extends Vehicule {

    private static final int DISTANCE_ROULAGE_PISTE_KM = 2;
    private static final int ALTITUDE_VOL_PIEDS = 35_000;

    public Avion(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax);
    }

    @Override
    public boolean peutPrendreEnCharge(ModeTransport mode, List<Colis> colis) {
        return mode == ModeTransport.AIR
                && colis.stream().noneMatch(c -> c.getType() == TypeMarchandise.DANGEREUX);
    }

    @Override
    public void partir(int distance, List<Colis> colis) {
        System.out.println("L'avion " + immatriculation + " roule sur la piste");
        System.out.println("L'avion " + immatriculation + " vole à " + ALTITUDE_VOL_PIEDS + " pieds");
    }
}
