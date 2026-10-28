package fr.efrei.transfret;

public class SupplementFragile extends DecorateurPrix {

    private static final double MAJORATION = 1.15;

    public SupplementFragile(ComposantPrix composant) {
        super(composant);
    }

    @Override
    public double calculer(DonneesPrix donnees) {
        double prix = prixEnveloppe(donnees);
        return donnees.contient(TypeMarchandise.FRAGILE) ? prix * MAJORATION : prix;
    }
}
