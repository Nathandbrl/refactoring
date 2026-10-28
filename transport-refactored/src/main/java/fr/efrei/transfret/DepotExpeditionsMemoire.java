package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepotExpeditionsMemoire implements ExpeditionRepository {

    private final Map<String, Expedition> expeditions = new HashMap<>();

    @Override
    public void sauvegarder(Expedition expedition) {
        expeditions.put(expedition.getId(), expedition);
    }

    @Override
    public void mettreAJour(Expedition expedition) {
        expeditions.put(expedition.getId(), expedition);
    }

    @Override
    public Expedition trouver(String id) {
        return expeditions.get(id);
    }

    @Override
    public List<Expedition> toutes() {
        return new ArrayList<>(expeditions.values());
    }
}
