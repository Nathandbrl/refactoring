package fr.efrei.transfret;

public class PolitiqueRemiseEntreprise implements PolitiqueRemise {

    private static final double SEUIL_REMISE_IMPORTANTE = 5_000;
    private static final double REMISE_STANDARD = 0.90;
    private static final double REMISE_IMPORTANTE = 0.85;

    @Override
    public double appliquer(double prix) {
        return prix * (prix > SEUIL_REMISE_IMPORTANTE ? REMISE_IMPORTANTE : REMISE_STANDARD);
    }
}
