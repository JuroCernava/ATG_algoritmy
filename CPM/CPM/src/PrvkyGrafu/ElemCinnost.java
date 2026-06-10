package PrvkyGrafu;

import java.util.Comparator;

public class ElemCinnost {
    private int id;
    private int trvanie;
    private int najskorZac;
    private int najneskorKoniec;
    private int rezerva;
    public ElemCinnost(int id, int trvanie) {
        this.id = id;
        this.trvanie = trvanie;
    }
    public int getId() {
        return this.id;
    }
    public int getTrvanie() {
        return this.trvanie;
    }
    public void setNajskorZac(int najskorZac) {
        this.najskorZac = najskorZac;
    }
    public void dopocitajRezervu() {
        this.rezerva = this.najneskorKoniec - this.najskorZac - this.trvanie;
    }
    public void setZaciatok(int zaciatok) {
        this.najskorZac = zaciatok;
    }
    public void setKoniec(int koniec) {
        this.najneskorKoniec = koniec;
    }
    public void setNajneskorKoniec(int najneskorKoniec) {
        this.najneskorKoniec = najneskorKoniec;
    }
    public int getNajskorZac() {
        return najskorZac;
    }

    public int getNajneskorKoniec() {
        return najneskorKoniec;
    }

    public int getRezerva() {
        return rezerva;
    }
    public static class Comparators {
        public static Comparator<ElemCinnost> ZACIATOK = new Comparator<ElemCinnost>() {
            public int compare(ElemCinnost cinnost1, ElemCinnost cinnost2) {
                return cinnost1.getNajskorZac() - cinnost2.najskorZac;
            }
        };
    }
}
