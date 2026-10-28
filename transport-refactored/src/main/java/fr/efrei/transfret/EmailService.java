package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public class EmailService implements ServiceEmail {

    private static final String SMTP_HOST = "smtp.transfret.fr";
    private static final String EXPEDITEUR = "noreply@transfret.fr";

    private final List<String[]> journal = new ArrayList<>();

    @Override
    public boolean envoyer(String destinataire, String sujet, String corps) {
        if (destinataire == null || destinataire.indexOf("@") < 0) {
            return false;
        }
        System.out.println("[EMAIL via " + SMTP_HOST + "] " + EXPEDITEUR + " -> " + destinataire + " | " + sujet);
        System.out.println("        " + corps);
        journal.add(new String[]{destinataire, sujet, corps});
        return true;
    }

    @Override
    public List<String[]> journal() {
        return List.copyOf(journal);
    }
}
