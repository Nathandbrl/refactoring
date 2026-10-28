package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public class SmsService implements ServiceSms {

    private final List<String[]> journal = new ArrayList<>();

    @Override
    public void envoyerSms(String numero, String message) {
        if (message.length() > 160) {
            message = message.substring(0, 157) + "...";
        }
        System.out.println("[SMS] -> " + numero + " : " + message);
        journal.add(new String[]{numero, message});
    }

    @Override
    public List<String[]> journal() {
        return List.copyOf(journal);
    }
}
