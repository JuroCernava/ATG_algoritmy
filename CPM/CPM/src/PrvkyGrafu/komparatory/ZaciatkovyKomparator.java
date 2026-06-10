package PrvkyGrafu.komparatory;

import PrvkyGrafu.ElemCinnost;
import PrvkyGrafu.Hrana;

import java.util.Comparator;

public class ZaciatkovyKomparator implements Comparator<ElemCinnost> {
    public int compare(ElemCinnost a, ElemCinnost b) {
        return (a.getNajskorZac() - b.getNajskorZac());
    }
}
