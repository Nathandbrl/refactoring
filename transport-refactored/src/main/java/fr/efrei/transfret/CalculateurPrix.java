package fr.efrei.transfret;

import java.util.List;

public class CalculateurPrix {

    private static final double TARIF_ROUTE_PAR_KILOMETRE = 1.20;
    private static final double TARIF_ROUTE_PAR_KILOGRAMME = 0.10;
    private static final double TARIF_RAIL_PAR_KILOMETRE = 0.80;
    private static final double TARIF_RAIL_PAR_KILOGRAMME = 0.07;
    private static final double MINIMUM_RAIL = 150;
    private static final double TARIF_MER_PAR_KILOMETRE = 0.50;
    private static final double TARIF_MER_PAR_KILOGRAMME = 0.03;
    private static final double MINIMUM_MER = 500;
    private static final double TARIF_AIR_PAR_KILOMETRE = 0.60;
    private static final double POIDS_VOLUMETRIQUE_PAR_METRE_CUBE = 167;
    private static final double TARIF_AIR_PAR_KILOGRAMME_TAXABLE = 4.50;
    private static final double MAJORATION_FRAGILE = 1.15;
    private static final double MAJORATION_DANGEREUX = 1.30;
    private static final double MAJORATION_REFRIGERE = 1.20;
    private static final double MAJORATION_URGENCE = 1.50;
    private static final double SEUIL_REMISE_ENTREPRISE = 5_000;
    private static final double REMISE_ENTREPRISE_STANDARD = 0.90;
    private static final double REMISE_ENTREPRISE_IMPORTANTE = 0.85;
    private static final double REMISE_PREMIUM = 0.80;
    private static final double TAUX_ASSURANCE = 0.02;
    private static final double ASSURANCE_MINIMUM = 15;

    public double calculerPrixHT(List<Colis> colis, int distance, ModeTransport mode,
                                 boolean urgent, boolean assurance, TypeClient typeClient) {
        double poids = 0;
        double volume = 0;
        double valeur = 0;
        boolean fragile = false;
        boolean dangereux = false;
        boolean refrigere = false;
        for (Colis c : colis) {
            poids += c.getPoids();
            volume += c.getVolume();
            valeur += c.getValeurDeclaree();
            if (c.getType() == TypeMarchandise.FRAGILE) fragile = true;
            if (c.getType() == TypeMarchandise.DANGEREUX) dangereux = true;
            if (c.getType() == TypeMarchandise.REFRIGERE) refrigere = true;
        }

        double prix;
        if (mode == ModeTransport.ROUTE) {
            prix = distance * TARIF_ROUTE_PAR_KILOMETRE + poids * TARIF_ROUTE_PAR_KILOGRAMME;
        } else if (mode == ModeTransport.RAIL) {
            prix = distance * TARIF_RAIL_PAR_KILOMETRE + poids * TARIF_RAIL_PAR_KILOGRAMME;
            prix = Math.max(prix, MINIMUM_RAIL);
        } else if (mode == ModeTransport.MER) {
            prix = distance * TARIF_MER_PAR_KILOMETRE + poids * TARIF_MER_PAR_KILOGRAMME;
            prix = Math.max(prix, MINIMUM_MER);
        } else if (mode == ModeTransport.AIR) {
            double poidsTaxable = Math.max(poids, volume * POIDS_VOLUMETRIQUE_PAR_METRE_CUBE);
            prix = distance * TARIF_AIR_PAR_KILOMETRE + poidsTaxable * TARIF_AIR_PAR_KILOGRAMME_TAXABLE;
        } else {
            throw new IllegalArgumentException("Mode inconnu : " + mode);
        }

        if (fragile) prix *= MAJORATION_FRAGILE;
        if (dangereux) prix *= MAJORATION_DANGEREUX;
        if (refrigere) prix *= MAJORATION_REFRIGERE;
        if (urgent) prix *= MAJORATION_URGENCE;

        if (typeClient == TypeClient.ENTREPRISE) {
            prix *= prix > SEUIL_REMISE_ENTREPRISE
                    ? REMISE_ENTREPRISE_IMPORTANTE
                    : REMISE_ENTREPRISE_STANDARD;
        } else if (typeClient == TypeClient.PREMIUM) {
            prix *= REMISE_PREMIUM;
        }

        if (assurance) {
            prix += Math.max(valeur * TAUX_ASSURANCE, ASSURANCE_MINIMUM);
        }
        return Utils.arrondir(prix);
    }
}
