package fr.efrei.transfret;

import java.util.List;

public class Bateau extends Vehicule {

    private static final double KILOMETRES_PAR_MILLE_NAUTIQUE = 1.852;
    private static final double TEMPERATURE_REFRIGERATION = 4;

    private final boolean conteneursRefrigeres;

    public Bateau(String immatriculation, double capaciteMax, boolean conteneursRefrigeres) {
        super(immatriculation, capaciteMax);
        this.conteneursRefrigeres = conteneursRefrigeres;
    }

    @Override
    public boolean peutPrendreEnCharge(ModeTransport mode, List<Colis> colis) {
        return mode == ModeTransport.MER
                && (!contient(colis, TypeMarchandise.REFRIGERE) || conteneursRefrigeres);
    }

    @Override
    public void partir(int distance, List<Colis> colis) {
        int milesNautiques = (int) (distance / KILOMETRES_PAR_MILLE_NAUTIQUE);
        System.out.println("Le navire " + immatriculation + " navigue sur "
                + milesNautiques + " milles nautiques");
        if (contient(colis, TypeMarchandise.REFRIGERE)) {
            System.out.println("Conteneurs frigorifiques réglés à " + TEMPERATURE_REFRIGERATION + "°C");
        }
    }

    private static boolean contient(List<Colis> colis, TypeMarchandise type) {
        return colis.stream().anyMatch(c -> c.getType() == type);
    }
}
