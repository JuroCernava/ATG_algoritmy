import AnalyzyGrafu.MonotonneOcislovanie;
import CPM.NajneskorKoniec;
import CPM.NajskorZaciatok;
import CPM.Rozvrh;
import Nacitanie_vstupu.Input;
import Nacitanie_vstupu.InputEC;
import PrvkyGrafu.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Input vstupHrany = new Input();
        InputEC vstupTrvania = new InputEC();
        String cestaHrany = JOptionPane.showInputDialog("Zadajte cestu k súboru s hranamy:");
        String cestaTrvania = JOptionPane.showInputDialog("Zadajte cestu k súboru s trvaniami cinnosti:");
        vstupHrany.readData(cestaHrany);
        vstupTrvania.readData(cestaTrvania);
        ArrayList<Hrana> hrany = vstupHrany.getZoznamHran();
        HashMap<Integer, ElemCinnost> elemCinnosti = vstupTrvania.getZoznamCinnosti();
        Rozvrh rozvrh = new Rozvrh();
        rozvrh.doplnCinnosti(elemCinnosti.values());
        Graf graf = new Graf(hrany);
        ArrayList<Integer> monotOcislovanie = new MonotonneOcislovanie(graf).getMonotOcislovanie();
        System.out.println("V monotonnom ocislovani sa nachadza " + monotOcislovanie.size() + " vrcholov.");
        System.out.println("Cinnost nasleduju za sebou takto:");
        for (int cislo : monotOcislovanie) {
            System.out.print(cislo + " ");
        }
        NajskorZaciatok najskorZaciatok = new NajskorZaciatok(monotOcislovanie, elemCinnosti, graf);
        int[] zaciatky = najskorZaciatok.getZaciatky();
        rozvrh.doplnZaciatky(zaciatky);
        NajneskorKoniec najneskorKoniec = new NajneskorKoniec(graf, najskorZaciatok.getTrvanieProjektu(), monotOcislovanie, elemCinnosti);
        int[] konce = najneskorKoniec.getKonce();
        rozvrh.doplnKonce(konce);
        rozvrh.dopocitajRezervy();
        rozvrh.vypis();
        rozvrh.vypisKritickuCestu();
        System.out.println("Projekt moze trvat " + najskorZaciatok.getTrvanieProjektu() + " jednotiek casu");
    }
}