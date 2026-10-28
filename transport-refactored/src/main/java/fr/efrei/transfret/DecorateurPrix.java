package fr.efrei.transfret;

public abstract class DecorateurPrix implements ComposantPrix {

    private final ComposantPrix composant;

    protected DecorateurPrix(ComposantPrix composant) {
        this.composant = composant;
    }

    protected final double prixEnveloppe(DonneesPrix donnees) {
        return composant.calculer(donnees);
    }
}
