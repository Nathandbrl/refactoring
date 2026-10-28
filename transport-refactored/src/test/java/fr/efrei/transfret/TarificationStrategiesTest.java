package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TarificationStrategiesTest {

    @Test
    void tarifRouteCombineDistanceEtPoids() {
        assertEquals(122, new TarifRoute().calculer(100, 20, 1), 0.001);
    }

    @Test
    void tarifRailAppliqueLeMinimumEtCalculeLeTarifNormal() {
        TarifRail tarif = new TarifRail();

        assertEquals(150, tarif.calculer(10, 1, 0), 0.001);
        assertEquals(470, tarif.calculer(500, 1_000, 0), 0.001);
    }

    @Test
    void tarifMerAppliqueLeMinimumEtCalculeLeTarifNormal() {
        TarifMer tarif = new TarifMer();

        assertEquals(500, tarif.calculer(100, 1, 0), 0.001);
        assertEquals(530, tarif.calculer(1_000, 1_000, 0), 0.001);
    }

    @Test
    void tarifAirUtiliseLePoidsVolumetriqueQuandIlEstPlusEleve() {
        assertEquals(1_563, new TarifAir().calculer(100, 100, 2), 0.001);
    }

    @Test
    void tarifAirUtiliseLePoidsReelQuandIlEstPlusEleve() {
        assertEquals(2_310, new TarifAir().calculer(100, 500, 1), 0.001);
    }

    @Test
    void politiqueParticulierNeChangePasLePrix() {
        assertEquals(1_234, new PolitiqueRemiseAucune().appliquer(1_234), 0.001);
    }

    @Test
    void politiqueEntrepriseChoisitLaRemiseSelonLeSeuil() {
        PolitiqueRemiseEntreprise politique = new PolitiqueRemiseEntreprise();

        assertEquals(4_500, politique.appliquer(5_000), 0.001);
        assertEquals(4_250.85, politique.appliquer(5_001), 0.001);
    }

    @Test
    void politiquePremiumAppliqueVingtPourCent() {
        assertEquals(987.2, new PolitiqueRemisePremium().appliquer(1_234), 0.001);
    }
}
