package fr.efrei.transfret;

import java.util.Map;

public class TarifDeBase implements ComposantPrix {

    private final Map<ModeTransport, TarifTransport> tarifs;

    public TarifDeBase(Map<ModeTransport, TarifTransport> tarifs) {
        this.tarifs = Map.copyOf(tarifs);
    }

    @Override
    public double calculer(DonneesPrix donnees) {
        TarifTransport tarif = tarifs.get(donnees.mode());
        if (tarif == null) {
            throw new IllegalArgumentException("Aucun tarif défini pour le mode : " + donnees.mode());
        }
        return tarif.calculer(donnees.distance(), donnees.poidsTotal(), donnees.volumeTotal());
    }
}
