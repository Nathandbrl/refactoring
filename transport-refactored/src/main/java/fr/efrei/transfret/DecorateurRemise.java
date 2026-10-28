package fr.efrei.transfret;

public class DecorateurRemise extends DecorateurPrix {

    private final PolitiqueRemise politique;

    public DecorateurRemise(ComposantPrix composant, PolitiqueRemise politique) {
        super(composant);
        this.politique = politique;
    }

    @Override
    public double calculer(DonneesPrix donnees) {
        return politique.appliquer(prixEnveloppe(donnees));
    }
}
