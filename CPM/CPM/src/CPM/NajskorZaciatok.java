package CPM;

import PrvkyGrafu.ElemCinnost;
import PrvkyGrafu.Graf;
import PrvkyGrafu.Hrana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class NajskorZaciatok {
    private int[] zaciatky;
    private int[] predchadzajuce;
    private ArrayList<Integer> monotonOcislovanie;
    private HashMap<Integer, ElemCinnost> trvania;
    private HashMap<Integer, ArrayList<Integer>> vystupVrcholy;
    private int trvanieProjektu;
    private ArrayList<Hrana> hrany;
    private ArrayList<Integer> vrcholy;
    public NajskorZaciatok(ArrayList<Integer> monotonOcislovanie, HashMap<Integer, ElemCinnost> trvania, Graf graf) {
        this.monotonOcislovanie = monotonOcislovanie;
        this.trvania = trvania;
        this.hrany = graf.getZoznamHran();
        this.vrcholy = graf.getZoznamVrcholov();
        // v podstate krok 1
        this.predchadzajuce = new int[this.monotonOcislovanie.size() + 1];
        this.zaciatky = new int[this.monotonOcislovanie.size() + 1];
        this.vystupVrcholy = new HashMap<>();
        najdiVystVrcholy();
        najdiZaciatky();
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
    private void najdiZaciatky() {
        int najneskorZac = 0;
        int idNajneskor = -1;
        for (int cislo : this.monotonOcislovanie) {
            int novyZaciatok = this.zaciatky[cislo] + this.trvania.get(cislo).getTrvanie();
            for (int vystVrchol : this.vystupVrcholy.get(cislo)) {
                if (this.zaciatky[vystVrchol] < novyZaciatok) {
                    this.zaciatky[vystVrchol] = novyZaciatok;
                    this.predchadzajuce[vystVrchol] = cislo;
                }
                if (novyZaciatok > najneskorZac) {
                    najneskorZac = novyZaciatok;
                    idNajneskor = vystVrchol;
                }
            }
        }
        this.trvanieProjektu = najneskorZac + this.trvania.get(idNajneskor).getTrvanie();
    }
    public int[] getZaciatky() {
        return this.zaciatky;
    }
    public int getTrvanieProjektu() {
        return this.trvanieProjektu;
    }

}
