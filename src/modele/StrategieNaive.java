package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategieNaive implements StrategieIA {

    @Override
    public String choisirCoup(Partie partie, String couleur) {
        List<String> coupsValides = new ArrayList<>();
        for (int ligne = 1; ligne <= 8; ligne++) {
            for (char colonne = 'A'; colonne <= 'H'; colonne++) {
                String coup = ligne + "" + colonne;
                if (partie.coupValidePour(coup, couleur)) {
                    coupsValides.add(coup);
                }
            }
        }
        if (coupsValides.isEmpty())
            return null;
        return coupsValides.get(new Random().nextInt(coupsValides.size()));
    }
}