package AnalyzyGrafu;

import PrvkyGrafu.Graf;
import PrvkyGrafu.Hrana;

import java.util.ArrayList;

public class MonotonneOcislovanie {
    private Graf grafPrecedencie;
    // Postupnost P
    private ArrayList<Integer> monotOcislovanie;
    private int[] stupneVrcholov;
    public MonotonneOcislovanie(Graf grafPrecedencie) {
        this.grafPrecedencie = grafPrecedencie;
        this.monotOcislovanie = new ArrayList<>();
        this.stupneVrcholov = new int[grafPrecedencie.getZoznamVrcholov().size()];
        this.vytvorMonotonneOcislovanie();
    }
    private void vytvorMonotonneOcislovanie() {
        priradStupneVrcholom();
        ArrayList<Integer> aktNulove = new ArrayList<>();
        //Podmienka Krok3
        while (this.monotOcislovanie.size() < this.grafPrecedencie.getZoznamVrcholov().size() - 1) {
            aktNulove = najdiNulu(this.stupneVrcholov);
            ///Krok 2
            for (int aktNulovy : aktNulove) {
                this.monotOcislovanie.add(aktNulovy);
                this.stupneVrcholov[aktNulovy] = -1;
            }
            for (Hrana hrana : this.grafPrecedencie.getZoznamHran()) {
                if (aktNulove.contains(hrana.getVrcholZ())) {
                    this.stupneVrcholov[hrana.getVrcholDo()]--;
                }
            }
        }
    }
    //V podstate krok 1
    private void priradStupneVrcholom() {
        for (Hrana hrana : this.grafPrecedencie.getZoznamHran()) {
            int vstupVrchol = hrana.getVrcholDo();
            this.stupneVrcholov[vstupVrchol]++;
        }
    }
    private ArrayList<Integer> najdiNulu(int[] cisla) {
        ArrayList<Integer> nulove = new ArrayList<>();
        for (int i = 0; i < cisla.length; i++) {
            if (i > 0 && cisla[i] == 0) {
                nulove.add(i);
            }
        }
        return nulove;
    }
    public ArrayList<Integer> getMonotOcislovanie() {
        return this.monotOcislovanie;
    }
}
