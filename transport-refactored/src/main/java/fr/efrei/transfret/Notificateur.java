package fr.efrei.transfret;

public class Notificateur {

    private final ServiceEmail email;
    private final ServiceSms sms;

    public Notificateur(ServiceEmail email, ServiceSms sms) {
        this.email = email;
        this.sms = sms;
    }

    public void envoyerBienvenue(Client client) {
        envoyerEmail(client, "Bienvenue chez TransFret", "Bonjour " + client.getNom()
                + ", votre compte " + client.getType().name().toLowerCase() + " a été créé.");
    }

    public void notifier(Client client, String sujet, String corps, String sms) {
        envoyerEmail(client, sujet, corps);
        if (client.getType() == TypeClient.PREMIUM) {
            this.sms.envoyerSms(client.getTelephone(), sms);
        }
    }

    private void envoyerEmail(Client client, String sujet, String corps) {
        if (!email.envoyer(client.getEmail(), sujet, corps)) {
            throw new IllegalStateException("Impossible d'envoyer un email à " + client.getEmail());
        }
    }
}
