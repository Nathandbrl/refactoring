package fr.efrei.transfret;

public class SupplementDangereux extends DecorateurPrix {

    private static final double MAJORATION = 1.30;

    public SupplementDangereux(ComposantPrix composant) {
        super(composant);
    }

    @Override
    public double calculer(DonneesPrix donnees) {
        double prix = prixEnveloppe(donnees);
        return donnees.contient(TypeMarchandise.DANGEREUX) ? prix * MAJORATION : prix;
    }
}
