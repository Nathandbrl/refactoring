package fr.efrei.transfret;

import java.util.Map;

public class AssembleurPrix {

    private final Map<ModeTransport, TarifTransport> tarifs;
    private final Map<TypeClient, PolitiqueRemise> politiquesRemise;

    public AssembleurPrix(Map<ModeTransport, TarifTransport> tarifs,
                          Map<TypeClient, PolitiqueRemise> politiquesRemise) {
        this.tarifs = Map.copyOf(tarifs);
        this.politiquesRemise = Map.copyOf(politiquesRemise);
    }

    public ComposantPrix assembler(DonneesPrix donnees) {
        ComposantPrix composant = new TarifDeBase(tarifs);
        if (donnees.contient(TypeMarchandise.FRAGILE)) {
            composant = new SupplementFragile(composant);
        }
        if (donnees.contient(TypeMarchandise.DANGEREUX)) {
            composant = new SupplementDangereux(composant);
        }
        if (donnees.contient(TypeMarchandise.REFRIGERE)) {
            composant = new SupplementRefrigere(composant);
        }
        if (donnees.urgent()) {
            composant = new SupplementUrgence(composant);
        }
        composant = new SupplementHorsGabarit(composant);

        PolitiqueRemise politique = politiquesRemise.get(donnees.typeClient());
        if (politique == null) {
            throw new IllegalArgumentException(
                    "Aucune politique de remise définie pour le client : " + donnees.typeClient());
        }
        composant = new DecorateurRemise(composant, politique);
        if (donnees.assurance()) {
            composant = new DecorateurAssurance(composant);
        }
        return composant;
    }
}
