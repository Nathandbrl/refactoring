package fr.efrei.transfret;

import java.util.List;

public interface ExpeditionRepository {

    void sauvegarder(Expedition expedition);

    void mettreAJour(Expedition expedition);

    Expedition trouver(String id);

    List<Expedition> toutes();
}
