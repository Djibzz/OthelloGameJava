package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JoueurIA extends Joueur {
    public JoueurIA(String pseudo) {
        super(pseudo);
    }

    public List<String> obtenirCoupsValides(Partie partie) {
        List<String> coupsValides = new ArrayList<>();
        for (int ligne = 1; ligne <= 8; ligne++) { // Lignes de 1 à 8
            for (char colonne = 'A'; colonne <= 'H'; colonne++) { // Colonnes de A à H
                String coup = ligne + "" + colonne; //
                if (partie.coupValide(coup)) {
                    coupsValides.add(coup);
                }
            }
        }
        return coupsValides;
    }

    public String choisircoup(Partie partie){
        if (obtenirCoupsValides(partie).isEmpty())
            return null;  // Aucun coup valide, l'IA passe son tour
        Random rand = new Random();
        int rand_int = rand.nextInt(obtenirCoupsValides(partie).size());
        return obtenirCoupsValides(partie).get(rand_int);
    }

    public void jouerCoup(Partie partie) {
        String coup = choisircoup(partie);
        if (coup != null) {
            partie.jouerCoup(coup);
        } else {
            // Si l'IA ne peut pas jouer, elle passe son tour
            partie.jouerCoup("P");
        }
    }

}