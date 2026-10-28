package fr.efrei.transfret;

public class PolitiqueRemisePremium implements PolitiqueRemise {

    private static final double REMISE = 0.80;

    @Override
    public double appliquer(double prix) {
        return prix * REMISE;
    }
}
