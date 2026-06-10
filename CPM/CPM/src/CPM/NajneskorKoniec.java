package CPM;

import PrvkyGrafu.ElemCinnost;
import PrvkyGrafu.Graf;
import PrvkyGrafu.Hrana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class NajneskorKoniec {
    private int[] konce;
    private int[] nasledujuce;
    private int trvanieProjektu;
    private ArrayList<Integer> monotonOcislovanie;
    private HashMap<Integer, ElemCinnost> trvania;
    private HashMap<Integer, ArrayList<Integer>> vystupVrcholy;
    private ArrayList<Hrana> hrany;
    private ArrayList<Integer> vrcholy;
    public NajneskorKoniec(Graf graf, int trvanieProjektu, ArrayList<Integer> monotonOcislovanie, HashMap<Integer, ElemCinnost> trvania) {
        this.trvanieProjektu = trvanieProjektu;
        this.monotonOcislovanie = monotonOcislovanie;
        Collections.reverse(this.monotonOcislovanie);
        this.trvania = trvania;
        this.vrcholy = graf.getZoznamVrcholov();
        this.hrany = graf.getZoznamHran();
        this.konce = new int[monotonOcislovanie.size() + 1];
        this.nasledujuce = new int[monotonOcislovanie.size() + 1];
        this.vystupVrcholy = new HashMap<>();
        this.najdiVystVrcholy();
        this.inicializacia();
        this.najdiKonce();
    }
    //Krok1
    private void inicializacia() {
        for (int i = 1; i < this.konce.length; i++) {
            this.konce[i] = this.trvanieProjektu;
            this.nasledujuce[i] = 0;
        }
    }

    private void najdiKonce() {
        System.out.println();
        for (int cislo : this.monotonOcislovanie) {
            int povodnyKoniec = this.konce[cislo];
            for (int vystVrchol : this.vystupVrcholy.get(cislo)) {
                int novyKoniec = this.konce[vystVrchol] - this.trvania.get(vystVrchol).getTrvanie();
                if (povodnyKoniec > novyKoniec) {
                    this.konce[cislo] = novyKoniec;
                    povodnyKoniec = this.konce[cislo];
                    this.nasledujuce[cislo] = vystVrchol;
                }
            }
        }
    }

    private void najdiVystVrcholy() {
        for (int vrchol : this.vrcholy) {
            this.vystupVrcholy.put(vrchol, new ArrayList<>());
        }
        for (Hrana hrana : this.hrany) {
            int vstup = hrana.getVrcholZ();
            int vystup = hrana.getVrcholDo();
            if (!this.vystupVrcholy.get(vstup).contains(vystup)) {
                this.vystupVrcholy.get(vstup).add(vystup);
            }
        }
    }
    public int[] getKonce() {
        return this.konce;
    }
}
