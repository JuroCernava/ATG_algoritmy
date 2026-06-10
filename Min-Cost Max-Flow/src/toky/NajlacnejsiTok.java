package toky;

import prvkyGrafu.Graf;
import prvkyGrafu.Hrana;
import java.util.ArrayList;

public class NajlacnejsiTok {
    private Graf graf;
    private int cenaToku;
    private int zdroj;
    private int ustie;
    public NajlacnejsiTok(Graf graf) throws InterruptedException {
        this.graf = graf;
        this.cenaToku = 0;
        ZvacsujucaPolocesta zvacCesta = new ZvacsujucaPolocesta(this.graf);
        this.graf = zvacCesta.getGraf();
        this.zdroj = zvacCesta.getZdroj();
        this.ustie = zvacCesta.getUstie();
        this.vypocitajCenuToku();
        System.out.println("Cena maximalneho toku je " + this.cenaToku);
        boolean cyklusNajdeny = true;
        while (cyklusNajdeny) {
            System.out.println("Vrcholy: " );
            for (int vrchol : this.graf.getZoznamVrcholov()) {
                System.out.print(vrchol + " ");
            }
            System.out.println();
            HladanieZaporPoloCyklu hladanieZaporCyklu = new HladanieZaporPoloCyklu(this.graf);
            cyklusNajdeny = hladanieZaporCyklu.cyklusNajdeny();
            ArrayList<Integer> zaporCyklus = new ArrayList<>();
            zaporCyklus = hladanieZaporCyklu.getCyklus();
            if (hladanieZaporCyklu.getRezervaCyklu() > 0) {
                System.out.println("Zaporny polocyklus obsahuje vrcholy:");
                for (Integer vrchol : zaporCyklus) {
                    System.out.print(vrchol + " ");
                }
            } else {
                System.out.println("Zaporny polocyklus neexistuje");
            }
            System.out.println();
            System.out.println("Rezerva polocyklu je " + hladanieZaporCyklu.getRezervaCyklu());
            System.out.println("Cena polocyklu je " + hladanieZaporCyklu.getCenaCyklu());
            this.zmenTokyPodlaCyklu(zaporCyklus, hladanieZaporCyklu.getRezervaCyklu());
            this.cenaToku = 0;
            this.vypocitajCenuToku();
            this.vypisToky();
        }
    }

    private void vypocitajCenuToku() {
        for (Hrana hrana : this.graf.getZoznamHran()) {
            this.cenaToku = this.cenaToku + (hrana.getTok() * hrana.getCenaToku());
        }
    }

    private void zmenTokyPodlaCyklu(ArrayList<Integer> cyklus, int rezervaCyklu) {
        for (int vid = 0; vid < cyklus.size(); vid++) {
            if (vid > 0) {
                int vrchol1 = cyklus.get(vid - 1);
                int vrchol2 = cyklus.get(vid);
                if (this.graf.getHranaVrcholmi(vrchol1, vrchol2) != null) {
                    Hrana aktHrana = this.graf.getHranaVrcholmi(vrchol1, vrchol2);
                    int aktTok = aktHrana.getTok();
                    aktHrana.setTok(aktTok + rezervaCyklu);
                } else {
                    Hrana aktHrana = this.graf.getHranaVrcholmi(vrchol2, vrchol1);
                    int aktTok = aktHrana.getTok();
                    aktHrana.setTok(aktTok - rezervaCyklu);
                }
            }
        }
    }
    public void vypisToky() {
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
            System.out.println(" Cena toku je " + this.cenaToku);
    }

    public int getTok() {
        int tok = 0;
        for (Hrana hrana : graf.getHranySoVstupnym(this.zdroj)) {
            tok = tok + hrana.getTok();
        }
        return tok;
    }
}
