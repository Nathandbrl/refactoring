package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtensionFluvialeTest {

    @Test
    void tarifFluvialAppliqueLeMinimumEtLeTarifAuPoids() {
        TarifFluvial tarif = new TarifFluvial();

        assertEquals(300, tarif.calculer(365, 100, 0), 0.001);
        assertEquals(600, tarif.calculer(1_000, 10_000, 0), 0.001);
    }

    @Test
    void horsGabaritAugmenteLePrixFluvialUneSeuleFois() {
        AssembleurPrix assembleur = assembleurTarifFixe(300);
        DonneesPrix donnees = donnees(List.of(
                colis(200, 80, 80),
                colis(201, 80, 80),
                colis(250, 80, 80)
        ), ModeTransport.FLUVIAL);

        assertEquals(330, assembleur.assembler(donnees).calculer(donnees), 0.001);
    }

    @Test
    void limiteDeDeuxMetresEstIncluseEtLeSupplémentNeSAppliquePasSurRoute() {
        AssembleurPrix assembleur = assembleurTarifFixe(300);
        DonneesPrix dimensionLimite = donnees(List.of(colis(200, 100, 100)), ModeTransport.FLUVIAL);
        DonneesPrix horsGabaritRoute = donnees(List.of(colis(201, 100, 100)), ModeTransport.ROUTE);

        assertEquals(300, assembleur.assembler(dimensionLimite).calculer(dimensionLimite), 0.001);
        assertEquals(300, assembleur.assembler(horsGabaritRoute).calculer(horsGabaritRoute), 0.001);
    }

    @Test
    void penicheEstCompatibleAvecLeFluvialMaisPasAvecLesColisRefrigeres() {
        Peniche peniche = new Peniche("PEN-0001", 2_500_000);
        Colis standard = colis(100, 100, 100);
        Colis refrigere = new Colis("Frais", 1, 100, 100, 100, TypeMarchandise.REFRIGERE, 0);

        assertTrue(peniche.peutPrendreEnCharge(ModeTransport.FLUVIAL, List.of(standard)));
        assertFalse(peniche.peutPrendreEnCharge(ModeTransport.ROUTE, List.of(standard)));
        assertFalse(peniche.peutPrendreEnCharge(ModeTransport.FLUVIAL, List.of(refrigere)));
    }

    @Test
    void trajetParisLeHavreEtDelaiFluvialSontConnus() {
        assertEquals(365, Utils.getDistance("Paris", "Le Havre"));
        assertEquals(365, Utils.getDistance("Le Havre", "Paris"));
        assertEquals(2, new EstimateurDelai().calculer(365, ModeTransport.FLUVIAL, false));
        assertEquals(1, new EstimateurDelai().calculer(360, ModeTransport.FLUVIAL, false));
    }

    @Test
    void fluvialRefuseLurgenceEtLeRefrigere() {
        ValidateurExpedition validateur = new ValidateurExpedition();
        Client client = new Client("Client", "client@mail.fr", "0600000000", "Adresse",
                TypeClient.PARTICULIER, null);
        Colis standard = colis(100, 100, 100);
        Colis refrigere = new Colis("Frais", 1, 100, 100, 100, TypeMarchandise.REFRIGERE, 0);

        assertTrue(validateur.valider(client, List.of(standard), ModeTransport.FLUVIAL, false));
        assertFalse(validateur.valider(client, List.of(standard), ModeTransport.FLUVIAL, true));
        assertFalse(validateur.valider(client, List.of(refrigere), ModeTransport.FLUVIAL, false));
    }

    @Test
    void expeditionFluvialeUtiliseLaPenicheEtAppliqueLeSupplément() {
        ContexteApplication contexte = new ContexteApplication();
        Client client = contexte.getServiceClient().creerClient("Client", "client@mail.fr", "0600000000",
                "Adresse", TypeClient.PARTICULIER, null);
        Colis colis = new Colis("Palette longue", 100, 201, 80, 80, TypeMarchandise.STANDARD, 0);

        Expedition expedition = contexte.getServiceExpedition().creerExpedition(client, List.of(colis),
                "Paris", "Le Havre", ModeTransport.FLUVIAL, false, false);

        assertNotNull(expedition);
        assertEquals("PEN-0001", expedition.getVehicule().getImmatriculation());
        assertEquals(330, expedition.getPrixHT(), 0.001);
        assertEquals(2, contexte.getServiceExpedition().calculerDelai(365, ModeTransport.FLUVIAL, false));
        assertTrue(contexte.getServiceExpedition().demarrerExpedition(expedition.getId()));
    }

    private AssembleurPrix assembleurTarifFixe(double prix) {
        return new AssembleurPrix(
                Map.of(
                        ModeTransport.FLUVIAL, (distance, poids, volume) -> prix,
                        ModeTransport.ROUTE, (distance, poids, volume) -> prix
                ),
                Map.of(TypeClient.PARTICULIER, new PolitiqueRemiseAucune())
        );
    }

    private DonneesPrix donnees(List<Colis> colis, ModeTransport mode) {
        return new DonneesPrix(colis, 365, mode, false, false, TypeClient.PARTICULIER);
    }

    private Colis colis(double longueur, double largeur, double hauteur) {
        return new Colis("Colis", 1, longueur, largeur, hauteur, TypeMarchandise.STANDARD, 0);
    }
}
