package fr.efrei.transfret;

import java.util.List;

public interface ServiceSms {

    void envoyerSms(String numero, String message);

    List<String[]> journal();
}
