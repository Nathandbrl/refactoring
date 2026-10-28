package fr.efrei.transfret;

import java.util.List;

public class Flotte {

    public List<Vehicule> creerVehicules() {
        return List.of(
                new Camion("AB-123-CD", 19_000, false),
                new Camion("EF-456-GH", 12_000, true),
                new CamionFrigorifique("IJ-789-KL", 8_000),
                new Train("TR-0001", 600_000),
                new Bateau("CMA-CGM-01", 20_000_000, true),
                new Avion("F-GTXA", 100_000)
        );
    }
}
