package fr.efrei.transfret;

import java.util.List;

public class CalculateurPrix {

    private final AssembleurPrix assembleur;

    public CalculateurPrix(AssembleurPrix assembleur) {
        this.assembleur = assembleur;
    }

    public double calculerPrixHT(List<Colis> colis, int distance, ModeTransport mode,
                                 boolean urgent, boolean assurance, TypeClient typeClient) {
        DonneesPrix donnees = new DonneesPrix(colis, distance, mode, urgent, assurance, typeClient);
        return Utils.arrondir(assembleur.assembler(donnees).calculer(donnees));
    }
}
