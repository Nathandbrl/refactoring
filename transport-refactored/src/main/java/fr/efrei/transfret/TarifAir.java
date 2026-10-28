package fr.efrei.transfret;

public class TarifAir implements TarifTransport {

    private static final double PRIX_PAR_KILOMETRE = 0.60;
    private static final double POIDS_VOLUMETRIQUE_PAR_METRE_CUBE = 167;
    private static final double PRIX_PAR_KILOGRAMME_TAXABLE = 4.50;

    @Override
    public double calculer(int distance, double poids, double volume) {
        double poidsTaxable = Math.max(poids, volume * POIDS_VOLUMETRIQUE_PAR_METRE_CUBE);
        return distance * PRIX_PAR_KILOMETRE + poidsTaxable * PRIX_PAR_KILOGRAMME_TAXABLE;
    }
}
