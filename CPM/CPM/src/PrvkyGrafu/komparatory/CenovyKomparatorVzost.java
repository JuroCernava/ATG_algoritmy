package PrvkyGrafu.komparatory;

import PrvkyGrafu.Hrana;

import java.util.Comparator;

public class CenovyKomparatorVzost implements Comparator<Hrana> {

    public CenovyKomparatorVzost() {

    }
        public int compare(Hrana a, Hrana b) {
            return a.getCena() - b.getCena();
        }
    }
