/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prvkyGrafu;

import java.util.Comparator;

/**
 *
 * @author Maroš
 */
public class Hrana {

    private int vrcholZ;
    private int vrcholDo;
    private int kapacita;
    private int tok;
    private int cenaToku;

    public Hrana(int vrcholZ, int vrcholDo, int kapacita, int cenaToku) {
        this.vrcholZ = vrcholZ;
        this.vrcholDo = vrcholDo;
        this.kapacita = kapacita;
        this.cenaToku = cenaToku;
        this.tok = 0;
    }

    public Hrana(int vrcholZ, int vrcholDo, int kapacita, int cenaToku, int tok) {
        this.vrcholZ = vrcholZ;
        this.vrcholDo = vrcholDo;
        this.kapacita = kapacita;
        this.cenaToku = cenaToku;
        this.tok = tok;
    }

    public Hrana(int vrcholZ, int vrcholDo) {
        this.vrcholZ = vrcholZ;
        this.vrcholDo = vrcholDo;
    }

    public int getRezerva(boolean vSmere) {
        if (vSmere) {
            return this.kapacita - this.tok;
        } else {
            return this.tok;
        }
    }

    public int getCenaToku() {
        return this.cenaToku;
    }

    public void setTok(int tok) {
        this.tok = tok;
    }

    public int getTok() {
        return this.tok;
    }

    public int getVrcholZ() {
        return this.vrcholZ;
    }

    public int getVrcholDo() {
        return this.vrcholDo;
    }

    public int getKapacita() {
        return this.kapacita;
    }

    public static class Comparators {
        public static Comparator<Hrana> CENA = new Comparator<Hrana>() {
            public int compare(Hrana hrana1, Hrana hrana2) {
                    return hrana1.getKapacita() - hrana2.kapacita;
            }
        };
    }
}
