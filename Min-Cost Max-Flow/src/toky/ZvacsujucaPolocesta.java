package toky;

import prvkyGrafu.Graf;
import prvkyGrafu.Hrana;
import java.util.ArrayList;
import java.util.Collections;

public class ZvacsujucaPolocesta {
    private ArrayList<Integer> vrcholy;
    private int[] znacky;
    private int[] vstupStupne;
    private int[] vystupStupne;
    private int zdroj;
    private int ustie;
    private ArrayList<Integer> epsilon;
    private ArrayList<Integer> cesta;
    private ArrayList<Integer> nekonecne;
    private Graf graf;
    private int[][] toky;

    public ZvacsujucaPolocesta(Graf graf) throws InterruptedException {
        this.graf = graf;
        this.vrcholy = this.graf.getZoznamVrcholov();
        this.znacky = new int[this.vrcholy.size()];
        this.vstupStupne = new int[this.vrcholy.size()];
        this.vystupStupne = new int[this.vrcholy.size()];
        this.toky = new int[this.vrcholy.size()][this.vrcholy.size()];
        for (Hrana hrana : this.graf.getZoznamHran()) {
            this.toky[hrana.getVrcholZ()][hrana.getVrcholDo()] = 0;
        }
        this.priradVstupStupneVrcholom();
        this.priradVystStupneVrcholom();
        this.najdiZdrojAUstie();
        System.out.println("Zdroj je: " + this.zdroj + " a ustie je: " + this.ustie);
        this.inicializacia();
        this.najdiTok();
    }

    public void najdiTok() throws InterruptedException {
        while (this.epsilon.size() > 0) {
            this.inicializacia();
            while (!this.epsilon.contains(this.ustie) && this.epsilon.size() > 0) {
                ArrayList<Integer> pridajEpsilon = new ArrayList<>();
                ArrayList<Integer> odstranEpsilon = new ArrayList<>();
                for (Integer eid : this.epsilon) {
                    odstranEpsilon.add(eid);
                    ArrayList<Hrana> vystupneHrany = this.graf.getHranySoVstupnym(eid);
                    if (vystupneHrany != null && vystupneHrany.size() > 0) {
                        for (Hrana hrana : vystupneHrany) {
                            if (!this.epsilon.contains(hrana.getVrcholDo()) && this.nekonecne.contains(hrana.getVrcholDo()) && hrana.getTok() < hrana.getKapacita()) {
                                this.znacky[hrana.getVrcholDo()] = eid;
                                this.nekonecne.remove((Integer)hrana.getVrcholDo());
                                pridajEpsilon.add(hrana.getVrcholDo());
                            }
                        }
                    }
                    ArrayList<Hrana> vstupneHrany = this.graf.getHranySVystupnym(eid);
                    if (vstupneHrany != null && vstupneHrany.size() > 0) {
                        for (Hrana hrana : vstupneHrany) {
                            if (!this.epsilon.contains(hrana.getVrcholZ()) && this.nekonecne.contains(hrana.getVrcholZ()) && hrana.getTok() > 0) {
                                this.znacky[hrana.getVrcholZ()] = -eid;
                                this.nekonecne.remove((Integer)hrana.getVrcholZ());
                                pridajEpsilon.add(hrana.getVrcholZ());
                            }
                        }
                    }
                }
                for (Integer vrchol : pridajEpsilon) {
                    this.epsilon.add(vrchol);
                }
                for (Integer eid : odstranEpsilon) {
                    this.epsilon.remove((Integer)eid);
                }
            }
            if (this.epsilon.contains(this.ustie)) {
                int aktVrchol = this.ustie;
                int rezervaPolocesty = Integer.MAX_VALUE;
                ArrayList<Integer> moznaCesta = new ArrayList<>();
                if (this.znacky[this.ustie] < Integer.MAX_VALUE) {
                    moznaCesta.add(this.ustie);
                    int rezervaHrany = 0;
                    while (aktVrchol != this.zdroj) {
                        moznaCesta.add(Math.abs(this.znacky[aktVrchol]));
                        if (this.znacky[aktVrchol] > 0) {
                            rezervaHrany = this.graf.getHranaVrcholmi(Math.abs(this.znacky[aktVrchol]), aktVrchol).getKapacita() - this.graf.getHranaVrcholmi(Math.abs(this.znacky[aktVrchol]),aktVrchol).getTok();
                        } else {
                            if (this.znacky[aktVrchol] < 0) {
                                rezervaHrany = this.graf.getHranaVrcholmi(aktVrchol, Math.abs(this.znacky[aktVrchol])).getTok();
                            }
                        }
                        if (rezervaHrany < rezervaPolocesty) {
                            rezervaPolocesty = rezervaHrany;
                        }
                        aktVrchol = Math.abs(this.znacky[aktVrchol]);
                    }
                    System.out.println("Rezerva polcesty je " + rezervaPolocesty);
                    Hrana aktHrana = null;
                    for (Integer vid : moznaCesta) {
                        if (vid != this.zdroj) {
                            if (this.znacky[vid] > 0) {
                                aktHrana = this.graf.getHranaVrcholmi(Math.abs(this.znacky[vid]), vid);
                            } else {
                                aktHrana = this.graf.getHranaVrcholmi(vid, Math.abs(this.znacky[vid]));
                            }
                            Integer novytok = 0;
                            if (this.znacky[vid] > 0) {
                                novytok = aktHrana.getTok() + rezervaPolocesty;
                            } else {
                                novytok = aktHrana.getTok() - rezervaPolocesty;
                            }
                            aktHrana.setTok(novytok);
                        }
                    }
                    this.cesta = moznaCesta;
                    Collections.reverse(this.cesta);
                }
            }
        }
        this.vypisTok();
        //Neexistuje zvacs. polocesta => Ford-Fulkersonova veta => Tok je maximalny.
    }

    public int getTok() {
        int tok = 0;
        for (Hrana hrana : graf.getHranySoVstupnym(this.zdroj)) {
            tok = tok + hrana.getTok();
        }
        return tok;
    }

    public void vypisTok() {
        System.out.format("|    h     |");
        for (Hrana hrana : this.graf.getZoznamHran()) {
            if (hrana.getVrcholZ() != 0) {
                System.out.format("(%3d, %-3d) |", hrana.getVrcholZ(), hrana.getVrcholDo());
            }
        }
        System.out.println();
        System.out.format("|y(h)/c(h) |");
        for (Hrana hrana : this.graf.getZoznamHran()) {
            if (hrana.getVrcholZ() != 0) {
                System.out.format("%5d/%-4d |", hrana.getTok(), hrana.getKapacita());
            }
        }
        System.out.println();
        System.out.println(" Velkost toku = " + this.getTok());
    }

    private void inicializacia() {
        this.epsilon = new ArrayList<>();
        this.nekonecne = new ArrayList<>();
        this.cesta = new ArrayList<>();
        for (Integer vrchol : this.vrcholy) {
            if (vrchol != this.zdroj) {
                this.znacky[vrchol] = Integer.MAX_VALUE;
                this.nekonecne.add(vrchol);
            } else {
                this.znacky[vrchol] = 0;
                this.epsilon.add(vrchol);
            }
        }
    }

    private void priradVstupStupneVrcholom() {
        for (Hrana hrana : this.graf.getZoznamHran()) {
            int vstupVrchol = hrana.getVrcholDo();
            this.vstupStupne[vstupVrchol]++;
        }
    }

    private void priradVystStupneVrcholom() {
        for (Hrana hrana : this.graf.getZoznamHran()) {
            int vystupVrchol = hrana.getVrcholZ();
            this.vystupStupne[vystupVrchol]++;
        }
    }

    private void najdiZdrojAUstie() throws IllegalStateException{
        ArrayList<Integer> zdroje = this.najdiNulu(this.vstupStupne);
        if (zdroje.size() > 1) {
            throw new IllegalStateException("Tok nemoze mat viac ako jeden zdroj.");
        } else {
            this.zdroj = zdroje.get(0);
        }
        ArrayList<Integer> ustia = this.najdiNulu(this.vystupStupne);
        if (ustia.size() > 1) {
            throw new IllegalStateException("Tok nemoze mat viac ako jedno ustie.");
        } else {
            this.ustie = ustia.get(0);
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

    public int getZdroj() {
        return this.zdroj;
    }

    public int getUstie() {
        return this.ustie;
    }

    public Graf getGraf() {
        return this.graf;
    }
}
