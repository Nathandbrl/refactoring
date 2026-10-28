package fr.efrei.transfret;

import java.util.List;
import java.util.Map;

public class CalculateurPrix {

    private static final double MAJORATION_FRAGILE = 1.15;
    private static final double MAJORATION_DANGEREUX = 1.30;
    private static final double MAJORATION_REFRIGERE = 1.20;
    private static final double MAJORATION_URGENCE = 1.50;
    private static final double TAUX_ASSURANCE = 0.02;
    private static final double ASSURANCE_MINIMUM = 15;

    private final Map<ModeTransport, TarifTransport> tarifs;
    private final Map<TypeClient, PolitiqueRemise> politiquesRemise;

    public CalculateurPrix(Map<ModeTransport, TarifTransport> tarifs,
                           Map<TypeClient, PolitiqueRemise> politiquesRemise) {
        this.tarifs = Map.copyOf(tarifs);
        this.politiquesRemise = Map.copyOf(politiquesRemise);
    }

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

        TarifTransport tarif = tarifs.get(mode);
        if (tarif == null) {
            throw new IllegalArgumentException("Aucun tarif défini pour le mode : " + mode);
        }
        double prix = tarif.calculer(distance, poids, volume);

        if (fragile) prix *= MAJORATION_FRAGILE;
        if (dangereux) prix *= MAJORATION_DANGEREUX;
        if (refrigere) prix *= MAJORATION_REFRIGERE;
        if (urgent) prix *= MAJORATION_URGENCE;

        PolitiqueRemise politique = politiquesRemise.get(typeClient);
        if (politique == null) {
            throw new IllegalArgumentException("Aucune politique de remise définie pour le client : " + typeClient);
        }
        prix = politique.appliquer(prix);

        if (assurance) {
            prix += Math.max(valeur * TAUX_ASSURANCE, ASSURANCE_MINIMUM);
        }
        return Utils.arrondir(prix);
    }
}
