package prvkyGrafu;

import java.util.ArrayList;
import java.util.HashMap;

public class Graf {
    private ArrayList<Integer> zoznamVrcholov;
    private HashMap<Integer, ArrayList<Hrana>> vstupHrany;
    private HashMap<Integer, ArrayList<Hrana>> vystupHrany;
    private ArrayList<Hrana> zoznamHran;

    public Graf(ArrayList<Hrana> zoznamHran) {
        this.zoznamHran = zoznamHran;
        this.zoznamVrcholov = new ArrayList<>();
        this.vstupHrany = new HashMap<>();
        this.vystupHrany = new HashMap<>();
        vytvorZoznamVrcholov();
        this.oindexujHrany(this.zoznamHran);
    }

    private void oindexujHrany(ArrayList<Hrana> zoznamHran) {
        for (Hrana hrana : zoznamHran) {
            if (this.vstupHrany.get(hrana.getVrcholDo()) == null) {
                this.vstupHrany.put(hrana.getVrcholDo(), new ArrayList<>());
            }
            if (this.vystupHrany.get(hrana.getVrcholZ()) == null) {
                this.vystupHrany.put(hrana.getVrcholZ(), new ArrayList<>());
            }
            this.vstupHrany.get(hrana.getVrcholDo()).add(hrana);
            this.vystupHrany.get(hrana.getVrcholZ()).add(hrana);
        }
    }

    public ArrayList<Hrana> getHranySoVstupnym(int vrchol) {
        return this.vystupHrany.get(vrchol);
    }

    public ArrayList<Hrana> getHranySVystupnym(int vrchol) {
        return this.vstupHrany.get(vrchol);
    }

    public Hrana getHranaVrcholmi(int vrchol1, int vrchol2) {
        if (this.vystupHrany.keySet().contains(vrchol1)) {
            for (Hrana hrana : this.vystupHrany.get(vrchol1)) {
                if (hrana.getVrcholDo() == vrchol2) {
                    return hrana;
                }
            }
        }
        return null;
    }

    private void vytvorZoznamVrcholov() {
        for (Hrana hrana : this.zoznamHran) {
            int vrchol1 = hrana.getVrcholZ();
            int vrchol2 = hrana.getVrcholDo();
            if (!this.zoznamVrcholov.contains(vrchol1) || !this.zoznamVrcholov.contains(vrchol2)) {
                if (!this.zoznamVrcholov.contains(vrchol1)) {
                    this.zoznamVrcholov.add(vrchol1);
                }
                if (!this.zoznamVrcholov.contains(vrchol2)) {
                    this.zoznamVrcholov.add(vrchol2);
                }
            }
        }
    }
    public ArrayList<Integer> getZoznamVrcholov() {
        return this.zoznamVrcholov;
    }
    public ArrayList<Hrana> getZoznamHran() {
        return this.zoznamHran;
    }
}
