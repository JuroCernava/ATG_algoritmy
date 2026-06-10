package prvkyGrafu.komparatory;

import prvkyGrafu.Hrana;

import java.util.Comparator;

public class CenovyKomparatorZost implements Comparator<Hrana> {
    public CenovyKomparatorZost() {

    }
    public int compare(Hrana a, Hrana b) {
        return (a.getKapacita() - b.getKapacita())^-1;
    }
}
