
import prvkyGrafu.Graf;
import prvkyGrafu.Hrana;
import prvkyGrafu.Input;
import toky.NajlacnejsiTok;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Input vstup = new Input();
        String cesta = JOptionPane.showInputDialog("Zadajte cestu k súboru s grafom:");
        vstup.readData(cesta);
        ArrayList<Hrana> hrany = vstup.getZoznamHran();
        Graf graf = new Graf(hrany);
        NajlacnejsiTok najlacnejsiTok = new NajlacnejsiTok(graf);
    }
}