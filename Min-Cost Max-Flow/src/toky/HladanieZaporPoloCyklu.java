package toky;

import prvkyGrafu.Graf;
import prvkyGrafu.Hrana;

import java.util.ArrayList;
import java.util.Collections;

public class HladanieZaporPoloCyklu {
    private ArrayList<Integer> epsilon;
    private int[] znacky;
    private Graf graf;
    private int[] ceny;
    private int[] rezervy;
    private int cenaCyklu;
    private ArrayList<Integer> cyklus;
    private boolean cyklusNajdeny;

    public HladanieZaporPoloCyklu(Graf graf) {
        this.graf = new Graf(graf.getZoznamHran());
        this.epsilon = new ArrayList<Integer>();
        this.epsilon = this.graf.getZoznamVrcholov();
        this.epsilon.remove(0);
        Collections.sort(this.epsilon);
        this.znacky = new int[this.epsilon.size() + 1];
        this.ceny = new int[this.epsilon.size() + 1];
        this.rezervy = new int[this.epsilon.size() + 1];
        this.inicializacia();
        this.cenaCyklu = 0;
        this.cyklusNajdeny = false;
        this.cyklus = new ArrayList<>();
        this.najdiCyklus();
    }

    public ArrayList<Integer> najdiCyklus() {
        while (this.epsilon.size() > 0) {
            int riadiaci = this.epsilon.get(0);
            ArrayList<Integer> sled = new ArrayList<>();
            for (Hrana hrana : this.graf.getZoznamHran()) {
                if ((int)hrana.getVrcholZ() == riadiaci) {
                    if (hrana.getRezerva(true) > 0 && this.ceny[hrana.getVrcholDo()] > this.ceny[riadiaci] + hrana.getCenaToku()) {
                        int aktVrchol = hrana.getVrcholDo();
                        this.znacky[aktVrchol] = riadiaci;
                        this.ceny[aktVrchol] = this.ceny[riadiaci] + hrana.getCenaToku();
                        if (!this.epsilon.contains(aktVrchol)) {
                            this.epsilon.add(aktVrchol);
                        }
                        if (this.vytvorSled(aktVrchol, hrana, true)) {
                            return null;
                        }
                    }
                } else {
                    if ((int)hrana.getVrcholDo() == riadiaci) {
                        if (hrana.getTok() > 0 && this.ceny[hrana.getVrcholZ()] > this.ceny[riadiaci] - hrana.getCenaToku()) {
                            int aktVrchol = hrana.getVrcholZ();
                            this.znacky[aktVrchol] = riadiaci;
                            this.ceny[aktVrchol] = this.ceny[riadiaci] - hrana.getCenaToku();
                            if (!this.epsilon.contains(aktVrchol)) {
                                this.epsilon.add(aktVrchol);
                            }
                            if (vytvorSled(aktVrchol, hrana, false)) {
                                return null;
                            }
                        }
                    }
                }
            }
            this.epsilon.remove(this.epsilon.indexOf((Integer) riadiaci));
        }
        System.out.println("Epsilon je prazdny.");
        return null;
    }

    public boolean vytvorSled(int aktVrchol, Hrana hrana, boolean vSmere) {
        ArrayList<Integer> sled = new ArrayList<>();
        int vrcholDo = -1;
        if (vSmere) {
            vrcholDo = hrana.getVrcholDo();
        } else {
            vrcholDo = hrana.getVrcholZ();
        }
        sled.add(aktVrchol);
        aktVrchol = this.znacky[aktVrchol];
        sled.add(aktVrchol);
        while (aktVrchol != 0 && aktVrchol != vrcholDo) {
            aktVrchol = this.znacky[aktVrchol];
            if (aktVrchol > 0) {
                sled.add(aktVrchol);
            }
            if (aktVrchol == vrcholDo) {
                this.cyklus = sled;
                this.cyklusNajdeny = true;
                Collections.reverse(this.cyklus);
                return true;
            }
        }
        return false;
    }

    public boolean cyklusNajdeny() {
        return this.cyklusNajdeny;
    }

    public ArrayList<Integer> getCyklus() {
        return this.cyklus;
    }

    public int getCenaCyklu() {
        return this.cenaCyklu;
    }

    private void inicializacia() {
        for (int i = 0; i < this.znacky.length; i++) {
            this.znacky[i] = 0;
            this.ceny[i] = 0;
        }
    }

    public int getRezervaCyklu() {
        int rezervaCyklu = Integer.MAX_VALUE;
        for (int vid = 0; vid < this.cyklus.size(); vid++) {
            if (vid > 0) {
                int vrchol1 = this.cyklus.get(vid - 1);
                int vrchol2 = this.cyklus.get(vid);
                int rezervaHrany = -5;
                if (this.graf.getHranaVrcholmi(vrchol1, vrchol2) != null) {
                    Hrana aktHrana = this.graf.getHranaVrcholmi(vrchol1, vrchol2);
                    rezervaHrany = aktHrana.getKapacita() - aktHrana.getTok();
                    if (rezervaCyklu > rezervaHrany) {
                        rezervaCyklu = rezervaHrany;
                    }
                    this.cenaCyklu = cenaCyklu + aktHrana.getCenaToku();
                } else {
                    Hrana aktHrana = this.graf.getHranaVrcholmi(vrchol2, vrchol1);
                    rezervaHrany = aktHrana.getTok();
                    if (rezervaCyklu > rezervaHrany) {
                        rezervaCyklu = rezervaHrany;
                    }
                    this.cenaCyklu = cenaCyklu - aktHrana.getCenaToku();
                }
            }
        }
        if (rezervaCyklu == Integer.MAX_VALUE) {
            rezervaCyklu = 0;
        }
        return rezervaCyklu;
    }
}
