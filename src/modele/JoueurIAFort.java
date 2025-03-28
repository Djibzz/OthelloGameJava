package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code JoueurIAFort} représente une IA plus avancée qui utilise l'algorithme Minimax.
 * Elle hérite de {@code Joueur} et implémente la stratégie Minimax pour choisir le meilleur coup.
 */
public class JoueurIAFort extends Joueur {

    /**
     * Constructeur de l'IA forte.
     *
     * @param pseudo le pseudo de l'IA
     */
    public JoueurIAFort(String pseudo) {
        super(pseudo);
    }

    /**
     * Obtient la liste de tous les coups valides pour une couleur donnée.
     *
     * @param partie  la partie en cours
     * @param couleur la couleur utilisée ("⚫" ou "⚪")
     * @return la liste des coups valides pour cette couleur
     */
    public List<String> getTousLesCoupsValidesPour(Partie partie, String couleur) {
        List<String> coupsValides = new ArrayList<>();
        for (int ligne = 1; ligne <= 8; ligne++) {
            for (char colonne = 'A'; colonne <= 'H'; colonne++) {
                String coup = ligne + "" + colonne;
                if (partie.coupValidePour(coup, couleur)) {
                    coupsValides.add(coup);
                }
            }
        }
        return coupsValides;
    }

    /**
     * Implémente l'algorithme Minimax pour évaluer un coup.
     *
     * @param partie     la partie en cours
     * @param estMax     {@code true} si on maximise le score, {@code false} sinon
     * @param couleurIA  la couleur de l'IA
     * @param profondeur la profondeur de recherche
     * @return le score évalué pour la position
     */
    public int minimax(Partie partie, boolean estMax, String couleurIA, int profondeur) {
        if (profondeur == 0 || partie.getPlateau().partieEstFinie()) {
            return evaluerPlateau(couleurIA,partie.getPlateau()); // Fonction d'évaluation
        }
        String couleurAdverse = couleurIA.equals("⚫") ? "⚪" : "⚫";

        if (estMax) {
            int meilleurScore = Integer.MIN_VALUE;
            for (String coup : getTousLesCoupsValidesPour(partie, couleurIA)) {
                Partie copiePartie = partie.copier();
                copiePartie.jouerCoup(coup);
                copiePartie.setJoueurCourant(copiePartie.LejoueurSuivant());
                int score = minimax(copiePartie, false, couleurIA, profondeur - 1);
                meilleurScore = Math.max(meilleurScore, score);
            }
            return meilleurScore;
        } else {
            int pireScore = Integer.MAX_VALUE;
            for (String coup : getTousLesCoupsValidesPour(partie, couleurAdverse)) {
                Partie copiePartie = partie.copier();
                copiePartie.jouerCoup(coup);
                copiePartie.setJoueurCourant(copiePartie.LejoueurSuivant());
                int score = minimax(copiePartie, true, couleurIA, profondeur - 1);
                pireScore = Math.min(pireScore, score);
            }
            return pireScore;
        }
    }

    /**
     * Joue un coup en utilisant l'algorithme Minimax.
     *
     * @param partie la partie en cours
     * @return le meilleur coup choisi par l'IA
     */
    public String jouerCoupIA(Partie partie) {
        String meilleurCoup = null;
        int meilleurScore = Integer.MIN_VALUE;
        String couleurIA = partie.getJoueurCourant().equals(partie.getJoueurs()[0]) ? "⚫" : "⚪";

        for (String coup : getTousLesCoupsValidesPour(partie, couleurIA)) {
            Partie copiePartie = partie.copier();
            copiePartie.jouerCoup(coup);
            copiePartie.setJoueurCourant(copiePartie.LejoueurSuivant());
            int score = minimax(copiePartie, false, couleurIA, 4);
            if (score > meilleurScore) {
                meilleurScore = score;
                meilleurCoup = coup;
            }
        }

        if (meilleurCoup != null) {
            partie.jouerCoup(meilleurCoup);
        } else {
            partie.jouerCoup("P");
        }
        return meilleurCoup;
    }

    /**
     * Évalue le plateau pour une couleur donnée.
     * Les pions dans les coins, sur les bords et ailleurs sont valorisés différemment.
     * Si la partie est finie, retourne une valeur très élevée ou très basse.
     *
     * @param couleurJoueur la couleur à évaluer ("⚫" ou "⚪")
     * @return le score du plateau pour la couleur donnée  
     */
    public int evaluerPlateau(String couleurJoueur,Plateau plateau) {
        int score = 0;
        int nbPionsJoueur = 0, nbPionsAdversaire = 0;
        String couleurAdverse = couleurJoueur.equals("⚫") ? "⚪" : "⚫";
        String[][] tableau = plateau.getTableau();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tableau[i][j].equals(couleurJoueur)) {
                    nbPionsJoueur++;
                    if ((i == 0 && j == 0) || (i == 0 && j == 8 - 1) ||
                            (i == 8 - 1 && j == 0) || (i == 8 - 1 && j == 8 - 1)) {
                        score += 11; // Coin
                    } else if (i == 0 || i == 8 - 1 || j == 0 || j == 8 - 1) {
                        score += 6; // Bord
                    } else {
                        score += 1; // Autres positions
                    }
                } else if (tableau[i][j].equals(couleurAdverse)) {
                    nbPionsAdversaire++;
                }
            }
        }
        if (plateau.partieEstFinie()) {
            if (nbPionsJoueur > nbPionsAdversaire) {
                return 10000;
            } else if (nbPionsJoueur < nbPionsAdversaire) {
                return -10000;
            } else {
                return 0;
            }
        }
        return score;
    }
}
