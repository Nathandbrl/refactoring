package fr.efrei.transfret;

public class DecorateurAssurance extends DecorateurPrix {

    private static final double TAUX_ASSURANCE = 0.02;
    private static final double ASSURANCE_MINIMUM = 15;

    public DecorateurAssurance(ComposantPrix composant) {
        super(composant);
    }

    @Override
    public double calculer(DonneesPrix donnees) {
        double prix = prixEnveloppe(donnees);
        if (!donnees.assurance()) {
            return prix;
        }
        return prix + Math.max(donnees.valeurTotale() * TAUX_ASSURANCE, ASSURANCE_MINIMUM);
    }
}
