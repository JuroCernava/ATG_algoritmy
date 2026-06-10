package PrvkyGrafu;

import java.util.ArrayList;

public class Graf {
    private ArrayList<Integer> zoznamVrcholov;
    private ArrayList<Hrana> zoznamHran;
    public Graf(ArrayList<Hrana> zoznamHran) {
        this.zoznamHran = zoznamHran;
        this.zoznamVrcholov = new ArrayList<>();
        vytvorZoznamVrcholov();
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
