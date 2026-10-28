package fr.efrei.transfret;

import java.util.List;

public interface ServiceEmail {

    boolean envoyer(String destinataire, String sujet, String corps);

    List<String[]> journal();
}
