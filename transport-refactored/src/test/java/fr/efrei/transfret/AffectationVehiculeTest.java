package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AffectationVehiculeTest {

    @Test
    void choisitUnCamionAdrPourLesMarchandisesDangereuses() {
        Camion camionNonAdr = new Camion("A", 1_000, false);
        Camion camionAdr = new Camion("B", 1_000, true);
        AffectationVehicule affectation = new AffectationVehicule(List.of(camionNonAdr, camionAdr));
        List<Colis> colis = List.of(colis(TypeMarchandise.DANGEREUX));

        assertEquals(camionAdr, affectation.trouverVehicule(colis, ModeTransport.ROUTE));
    }

    @Test
    void affecteLesColisRefrigeresAuCamionFrigorifique() {
        Camion camion = new Camion("A", 1_000, false);
        CamionFrigorifique camionFrigorifique = new CamionFrigorifique("B", 1_000);
        AffectationVehicule affectation = new AffectationVehicule(List.of(camion, camionFrigorifique));
        List<Colis> colis = List.of(colis(TypeMarchandise.REFRIGERE));

        assertEquals(camionFrigorifique, affectation.trouverVehicule(colis, ModeTransport.ROUTE));
    }

    @Test
    void camionFrigorifiqueNEstPasUnSousTypeDeCamion() {
        assertFalse(Camion.class.isAssignableFrom(CamionFrigorifique.class));
    }

    @Test
    void compatibiliteDependDuModeEtDeLaCapaciteDuVehicule() {
        Train train = new Train("T", 10_000);
        List<Colis> colis = List.of(colis(TypeMarchandise.STANDARD));

        assertTrue(train.peutPrendreEnCharge(ModeTransport.RAIL, colis));
        assertFalse(train.peutPrendreEnCharge(ModeTransport.ROUTE, colis));
        assertNull(new AffectationVehicule(List.of(train))
                .trouverVehicule(List.of(new Colis("Lourd", 10_001, 1, 1, 1,
                        TypeMarchandise.STANDARD, 0)), ModeTransport.RAIL));
    }

    private Colis colis(TypeMarchandise type) {
        return new Colis(type.name(), 10, 10, 10, 10, type, 0);
    }
}
