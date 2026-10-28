package fr.efrei.transfret;

public class PolitiqueRemiseAucune implements PolitiqueRemise {

    @Override
    public double appliquer(double prix) {
        return prix;
    }
}
