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

    /**
     * Choisit un coup aléatoire parmi les coups valides.
     *
     * @param partie la partie en cours
     * @return un coup valide ou {@code null} s'il n'existe aucun coup valide
     */
    public String choisircoup(Partie partie) {
        List<String> coups = obtenirCoupsValides(partie);
        if (coups.isEmpty())
            return null;  // Aucun coup valide, l'IA passe son tour
        Random rand = new Random();
        int rand_int = rand.nextInt(coups.size());
        return coups.get(rand_int);
    }

    /**
     * Joue un coup en utilisant la stratégie aléatoire.
     *
     * @param partie la partie en cours
     * @return le coup joué
     */
    public String jouerCoup(Partie partie) {
        String coup = choisircoup(partie);
        if (coup != null) {
            partie.jouerCoup(coup);
        } else {
            // Si l'IA ne peut pas jouer, elle passe son tour
            partie.jouerCoup("P");
        }
        return coup;
    }
}
