package fr.efrei.transfret;

import java.util.List;

public class AffectationVehicule {

    private final List<Vehicule> vehicules;

    public AffectationVehicule(List<Vehicule> vehicules) {
        this.vehicules = List.copyOf(vehicules);
    }

    // retourne le premier véhicule disponible et compatible, null si aucun
    public Vehicule trouverVehicule(List<Colis> colis, ModeTransport mode) {
        double poids = colis.stream().mapToDouble(Colis::getPoids).sum();
        return vehicules.stream()
                .filter(Vehicule::estDisponible)
                .filter(v -> v.getCapaciteMax() - v.getChargeActuelle() >= poids)
                .filter(v -> v.peutPrendreEnCharge(mode, colis))
                .findFirst()
                .orElse(null);
    }
}
