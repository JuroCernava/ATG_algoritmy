package Nacitanie_vstupu;

import PrvkyGrafu.ElemCinnost;

import java.io.*;
import java.util.*;

public class InputEC {
        private HashMap<Integer, ElemCinnost> zoznamCinnosti; // smerníky na prvu hranu pre dany vrchol pomocou arraylistu
        private int pocetCinnosti;
        /**
         * Metóda na načítanie hrán, ich zoradenie podľa prvého a druhého stĺpca a
         * vytvorenie poľa smerníkov na vrcholy
         *
         * @param name Názov súboru, z ktorého načítať hrany
         */
        public void readData(String name) {
            readFile(name);
        }

        /**
         * Načítanie súboru s hranami a vyzvorenie zoznamov hrán, respektíve poľa
         * hreán H
         *
         * @param name Názov súboru, z ktorého načítať hrany
         */
        public void readFile(String name) {
            pocetCinnosti = 0;
            try {
                this.zoznamCinnosti = new HashMap<>();
                int index = 1;
                Scanner citacka = new Scanner(new File(name));
                String line;
                this.zoznamCinnosti.put(0, new ElemCinnost(0,0));
                while ((citacka.hasNextLine())) {
                    String riadok = citacka.nextLine();
                    if (!riadok.isEmpty()) {
                        Scanner citacRiadkov = new Scanner(riadok);
                        int trvanie = citacRiadkov.nextInt();
                        this.zoznamCinnosti.put(index, new ElemCinnost(index, trvanie));
                        index++;
                        citacRiadkov.close();
                    }
                }
                citacka.close();
                this.pocetCinnosti = this.zoznamCinnosti.size();
            } catch (FileNotFoundException ex) {
                System.err.println("Subor neexistuje");
            } catch (IOException ex) {
                System.err.println("IOException: " + ex.getMessage());
            }
        }
        public HashMap<Integer, ElemCinnost> getZoznamCinnosti() {
            return this.zoznamCinnosti;
        }
}
