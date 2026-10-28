package fr.efrei.transfret;

import java.util.List;

public record DonneesPrix(List<Colis> colis, int distance, ModeTransport mode, boolean urgent,
                          boolean assurance, TypeClient typeClient) {

    private static final double CENTIMETRES_CUBES_PAR_METRE_CUBE = 1_000_000;

    public DonneesPrix {
        colis = List.copyOf(colis);
    }

    public double poidsTotal() {
        return colis.stream().mapToDouble(Colis::getPoids).sum();
    }

    public double volumeTotal() {
        return colis.stream()
                .mapToDouble(c -> c.getLongueur() * c.getLargeur() * c.getHauteur()
                        / CENTIMETRES_CUBES_PAR_METRE_CUBE)
                .sum();
    }

    public double valeurTotale() {
        return colis.stream().mapToDouble(Colis::getValeurDeclaree).sum();
    }

    public boolean contient(TypeMarchandise type) {
        return colis.stream().anyMatch(colis -> colis.getType() == type);
    }
}
