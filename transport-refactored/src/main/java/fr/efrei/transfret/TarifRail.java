package fr.efrei.transfret;

public class TarifRail implements TarifTransport {

    private static final double PRIX_PAR_KILOMETRE = 0.80;
    private static final double PRIX_PAR_KILOGRAMME = 0.07;
    private static final double PRIX_MINIMUM = 150;

    @Override
    public double calculer(int distance, double poids, double volume) {
        return Math.max(distance * PRIX_PAR_KILOMETRE + poids * PRIX_PAR_KILOGRAMME, PRIX_MINIMUM);
    }
}
