package fr.efrei.transfret;

public class SupplementRefrigere extends DecorateurPrix {

    private static final double MAJORATION = 1.20;

    public SupplementRefrigere(ComposantPrix composant) {
        super(composant);
    }

    @Override
    public double calculer(DonneesPrix donnees) {
        double prix = prixEnveloppe(donnees);
        return donnees.contient(TypeMarchandise.REFRIGERE) ? prix * MAJORATION : prix;
    }
}
