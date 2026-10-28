package fr.efrei.transfret;

public class EstimateurDelai {

    private static final double VITESSE_ROUTE_KM_PAR_HEURE = 70;
    private static final double HEURES_CONDUITE_PAR_JOUR = 9;
    private static final double HEURES_CONDUITE_URGENTE_PAR_JOUR = 18;
    private static final double DISTANCE_RAIL_PAR_JOUR = 1_440;
    private static final int JOURS_SUPPLEMENTAIRES_RAIL = 1;
    private static final double DISTANCE_MER_PAR_JOUR = 720;
    private static final int JOURS_SUPPLEMENTAIRES_MER = 2;
    private static final int SEUIL_DISTANCE_AERIENNE_LONGUE = 3_000;

    public int calculer(int distance, ModeTransport mode, boolean urgent) {
        int jours = 0;
        switch (mode) {
            case ROUTE:
                double heures = distance / VITESSE_ROUTE_KM_PAR_HEURE;
                jours = (int) Math.ceil(heures
                        / (urgent ? HEURES_CONDUITE_URGENTE_PAR_JOUR : HEURES_CONDUITE_PAR_JOUR));
                break;
            case RAIL:
                jours = (int) Math.ceil(distance / DISTANCE_RAIL_PAR_JOUR) + JOURS_SUPPLEMENTAIRES_RAIL;
                if (urgent) jours--;
                break;
            case MER:
                jours = (int) Math.ceil(distance / DISTANCE_MER_PAR_JOUR) + JOURS_SUPPLEMENTAIRES_MER;
                break;
            case AIR:
                jours = distance > SEUIL_DISTANCE_AERIENNE_LONGUE ? 2 : 1;
                if (urgent) jours = 1;
                break;
        }
        return Math.max(jours, 1);
    }
}
