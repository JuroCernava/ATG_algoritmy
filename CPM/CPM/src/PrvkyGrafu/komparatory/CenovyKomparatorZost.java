package PrvkyGrafu.komparatory;

import PrvkyGrafu.Hrana;

import java.util.Comparator;

public class CenovyKomparatorZost implements Comparator<Hrana> {
    public CenovyKomparatorZost() {

    }
    public int compare(Hrana a, Hrana b) {
            return (b.getCena() - a.getCena());
    }
}
