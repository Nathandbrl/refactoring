package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecorateursPrixTest {

    private static final ComposantPrix PRIX_FIXE = donnees -> 100;

    @Test
    void supplementFragileEstAppliqueUneSeuleFois() {
        DonneesPrix donnees = donnees(
                List.of(colis(TypeMarchandise.FRAGILE), colis(TypeMarchandise.FRAGILE)), false, false,
                TypeClient.PARTICULIER);

        assertEquals(115, new SupplementFragile(PRIX_FIXE).calculer(donnees), 0.001);
    }

    @Test
    void supplementDangereuxEstAppliqueSiUnColisEstConcerne() {
        DonneesPrix donnees = donnees(List.of(colis(TypeMarchandise.DANGEREUX)), false, false,
                TypeClient.PARTICULIER);

        assertEquals(130, new SupplementDangereux(PRIX_FIXE).calculer(donnees), 0.001);
    }

    @Test
    void supplementRefrigereEstAppliqueSiUnColisEstConcerne() {
        DonneesPrix donnees = donnees(List.of(colis(TypeMarchandise.REFRIGERE)), false, false,
                TypeClient.PARTICULIER);

        assertEquals(120, new SupplementRefrigere(PRIX_FIXE).calculer(donnees), 0.001);
    }

    @Test
    void urgenceEtAssuranceNeChangentPasLePrixQuandEllesSontDesactivees() {
        DonneesPrix donnees = donnees(List.of(colis(TypeMarchandise.STANDARD)), false, false,
                TypeClient.PARTICULIER);

        ComposantPrix composant = new DecorateurAssurance(new SupplementUrgence(PRIX_FIXE));

        assertEquals(100, composant.calculer(donnees), 0.001);
    }

    @Test
    void assuranceEstAjouteeApresLaRemiseEtLesSupplement() {
        DonneesPrix donnees = donnees(
                List.of(new Colis("Fragile", 0, 0, 0, 0, TypeMarchandise.FRAGILE, 1_000)),
                true, true, TypeClient.ENTREPRISE);
        AssembleurPrix assembleur = new AssembleurPrix(
                Map.of(ModeTransport.ROUTE, (distance, poids, volume) -> 1_200),
                Map.of(TypeClient.ENTREPRISE, new PolitiqueRemiseEntreprise())
        );

        assertEquals(1_883, assembleur.assembler(donnees).calculer(donnees), 0.001);
    }

    @Test
    void assuranceAppliqueLeMinimum() {
        DonneesPrix donnees = donnees(
                List.of(new Colis("Standard", 0, 0, 0, 0, TypeMarchandise.STANDARD, 100)),
                false, true, TypeClient.PARTICULIER);

        assertEquals(115, new DecorateurAssurance(PRIX_FIXE).calculer(donnees), 0.001);
    }

    private DonneesPrix donnees(List<Colis> colis, boolean urgent, boolean assurance, TypeClient typeClient) {
        return new DonneesPrix(colis, 1, ModeTransport.ROUTE, urgent, assurance, typeClient);
    }

    private Colis colis(TypeMarchandise type) {
        return new Colis(type.name(), 0, 0, 0, 0, type, 0);
    }
}
