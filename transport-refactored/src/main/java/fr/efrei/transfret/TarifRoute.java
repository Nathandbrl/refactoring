package fr.efrei.transfret;

public class TarifRoute implements TarifTransport {

    private static final double PRIX_PAR_KILOMETRE = 1.20;
    private static final double PRIX_PAR_KILOGRAMME = 0.10;

    @Override
    public double calculer(int distance, double poids, double volume) {
        return distance * PRIX_PAR_KILOMETRE + poids * PRIX_PAR_KILOGRAMME;
    }
}
