package fr.efrei.transfret;

public class SupplementUrgence extends DecorateurPrix {

    private static final double MAJORATION = 1.50;

    public SupplementUrgence(ComposantPrix composant) {
        super(composant);
    }

    @Override
    public double calculer(DonneesPrix donnees) {
        double prix = prixEnveloppe(donnees);
        return donnees.urgent() ? prix * MAJORATION : prix;
    }
}
