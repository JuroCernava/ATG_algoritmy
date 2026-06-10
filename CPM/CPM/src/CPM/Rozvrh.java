package CPM;

import PrvkyGrafu.ElemCinnost;
import PrvkyGrafu.komparatory.ZaciatkovyKomparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Rozvrh {
    private ArrayList<ElemCinnost> cinnosti;

    public Rozvrh() {
        this.cinnosti = new ArrayList<>();
    }
    public void doplnCinnosti(Iterable<ElemCinnost> cinnosti) {
        for (ElemCinnost cinnost : cinnosti) {
            this.cinnosti.add(cinnost);
        }
    }
    public void doplnCinnost(ElemCinnost cinnost) {
        this.cinnosti.add(cinnost);
    }
    public void doplnZaciatky(int[] zaciatky) {
        for (ElemCinnost cinnost : this.cinnosti) {
            int idCinnosti = cinnost.getId();
            cinnost.setZaciatok(zaciatky[idCinnosti]);
        }
    }
    public void doplnKonce(int[] konce) {
        for (ElemCinnost cinnost : this.cinnosti) {
            int idCinnosti = cinnost.getId();
            cinnost.setKoniec(konce[idCinnosti]);
        }
    }
    public void dopocitajRezervy() {
        for (ElemCinnost cinnost : this.cinnosti) {
            cinnost.dopocitajRezervu();
        }
    }
    public void vypis() {
        for (int o = 0; o < 12 * 5 -4; o++) {
            System.out.print("_");
        }
        System.out.println();
        System.out.println("|  cinnost |  trvanie | zaciatok |  koniec  |  rezerva |");
        for (ElemCinnost cinnost : this.cinnosti) {
            if (cinnost.getId() > 0) {
            System.out.format("| %-8d | %-8d | %-8d | %-8d | %-8d | %n" , cinnost.getId(), cinnost.getTrvanie(),
                                                                          cinnost.getNajskorZac(), cinnost.getNajneskorKoniec(),
                                                                          cinnost.getRezerva());
            }
        }
        for (int o = 0; o < 12 * 5 -4; o++) {
            System.out.print("_");
        }
        System.out.println();
    }
    public void vypisKritickuCestu() {
        ArrayList<ElemCinnost> sortCinnosti = this.cinnosti;
        Collections.sort(sortCinnosti, new ZaciatkovyKomparator());
        System.out.print("Kriticka cesta (skratena): (");
        for (ElemCinnost cinnost : sortCinnosti) {
            if (cinnost.getRezerva() == 0 && cinnost.getId() != 0) {
                if (cinnost.getId() != sortCinnosti.get(sortCinnosti.size() -1).getId()) {
                    System.out.print(cinnost.getId() + ",");
                } else {
                    System.out.print(cinnost.getId());
                }
            }
        }
        System.out.println(")");
    }
}
