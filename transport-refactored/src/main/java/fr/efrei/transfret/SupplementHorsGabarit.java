package fr.efrei.transfret;

public class SupplementHorsGabarit extends DecorateurPrix {

    private static final double SEUIL_DIMENSION_CM = 200;
    private static final double MAJORATION = 1.10;

    public SupplementHorsGabarit(ComposantPrix composant) {
        super(composant);
    }

    @Override
    public double calculer(DonneesPrix donnees) {
        double prix = prixEnveloppe(donnees);
        boolean horsGabarit = donnees.colis().stream().anyMatch(colis ->
                colis.getLongueur() > SEUIL_DIMENSION_CM
                        || colis.getLargeur() > SEUIL_DIMENSION_CM
                        || colis.getHauteur() > SEUIL_DIMENSION_CM);
        return donnees.mode() == ModeTransport.FLUVIAL && horsGabarit ? prix * MAJORATION : prix;
    }
}
