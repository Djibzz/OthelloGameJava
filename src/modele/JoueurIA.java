package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code JoueurIA} représente une IA naïve qui joue à l'Othello.
 * Elle hérite de {@code Joueur} et implémente une stratégie de jeu aléatoire.
 */
public class JoueurIA extends JoueurOthello {
    private StrategieIA strategie;
    /**
     * Constructeur de l'IA naïve.
     *
     * @param pseudo le pseudo de l'IA
     */
    public JoueurIA(String pseudo) {
        super(pseudo);
    }


    public JoueurIA(String pseudo, StrategieIA strategie) {
        super(pseudo);
        this.strategie = strategie;
    }

    /**
     * Obtient la liste de tous les coups valides pour la partie donnée.
     *
     * @param partieOthello la partie en cours
     * @return la liste des coups valides
     */
    public List<String> obtenirCoupsValides(PartieOthello partieOthello) {
        List<String> coupsValides = new ArrayList<>();
        for (int ligne = 1; ligne <= 8; ligne++) { // Lignes de 1 à 8
            for (char colonne = 'A'; colonne <= 'H'; colonne++) { // Colonnes de A à H
                String coup = ligne + "" + colonne;
                if (partieOthello.coupValide(coup)) {
                    coupsValides.add(coup);
                }
            }
        }
        return coupsValides;
    }


    public String jouerCoup(PartieOthello partieOthello) {
        String couleur = this.equals(partieOthello.getJoueurs()[0]) ? "⚫" : "⚪";
        String coup = strategie.choisirCoup(partieOthello, couleur);
        if (coup != null) {
            partieOthello.jouerCoup(coup);
        } else {
            partieOthello.jouerCoup("P");
        }
        return coup;
    }
}
