package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La classe {@code JoueurIA} représente une IA naïve qui joue à l'Othello.
 * Elle hérite de {@code Joueur} et implémente une stratégie de jeu aléatoire.
 */
public class JoueurIA extends Joueur {

    /**
     * Constructeur de l'IA naïve.
     *
     * @param pseudo le pseudo de l'IA
     */
    public JoueurIA(String pseudo) {
        super(pseudo);
    }

    /**
     * Obtient la liste de tous les coups valides pour la partie donnée.
     *
     * @param partie la partie en cours
     * @return la liste des coups valides
     */
    public List<String> obtenirCoupsValides(Partie partie) {
        List<String> coupsValides = new ArrayList<>();
        for (int ligne = 1; ligne <= 8; ligne++) { // Lignes de 1 à 8
            for (char colonne = 'A'; colonne <= 'H'; colonne++) { // Colonnes de A à H
                String coup = ligne + "" + colonne;
                if (partie.coupValide(coup)) {
                    coupsValides.add(coup);
                }
            }
        }
        return coupsValides;
    }

    private StrategieIA strategie;

    public JoueurIA(String pseudo, StrategieIA strategie) {
        super(pseudo);
        this.strategie = strategie;
    }

    public String jouerCoup(Partie partie) {
        String couleur = this.equals(partie.getJoueurs()[0]) ? "⚫" : "⚪";
        String coup = strategie.choisirCoup(partie, couleur);
        if (coup != null) {
            partie.jouerCoup(coup);
        } else {
            partie.jouerCoup("P");
        }
        return coup;
    }
}
