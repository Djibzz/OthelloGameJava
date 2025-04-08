package modele;

import java.util.ArrayList;
import java.util.List;

public class StrategieMinimax implements StrategieIA {

    private int profondeur;

    public StrategieMinimax(int profondeur) {
        this.profondeur = profondeur;
    }

    @Override
    public String choisirCoup(PartieOthello partieOthello, String couleur) {
        List<String> coups = new JoueurIA("IA temporaire").obtenirCoupsValides(partieOthello);
        String meilleurCoup = null;
        int meilleurScore = Integer.MIN_VALUE;

        for (String coup : coups) {
            PartieOthello copie = partieOthello.copier();
            copie.jouerCoup(coup);
            copie.setJoueurCourant(copie.leJoueurSuivant());
            int score = minimax(copie, false, couleur, profondeur);
            if (score > meilleurScore) {
                meilleurScore = score;
                meilleurCoup = coup;
            }
        }
        return meilleurCoup;
    }

    public List<String> getTousLesCoupsValidesPour(PartieOthello partieOthello, String couleur) {
        List<String> coupsValides = new ArrayList<>();
        for (int ligne = 1; ligne <= 8; ligne++) {
            for (char colonne = 'A'; colonne <= 'H'; colonne++) {
                String coup = ligne + "" + colonne;
                if (partieOthello.coupValidePour(coup, couleur)) {
                    coupsValides.add(coup);
                }
            }
        }
        return coupsValides;
    }

    /**
     * Implémente l'algorithme Minimax pour évaluer un coup.
     *
     * @param partieOthello     la partie en cours
     * @param estMax     {@code true} si on maximise le score, {@code false} sinon
     * @param couleurIA  la couleur de l'IA
     * @param profondeur la profondeur de recherche
     * @return le score évalué pour la position
     */
    public int minimax(PartieOthello partieOthello, boolean estMax, String couleurIA, int profondeur) {
        if (profondeur == 0 || partieOthello.getPlateau().partieEstFinie()) {
            return evaluerPlateau(couleurIA, partieOthello.getPlateau()); // Fonction d'évaluation
        }
        String couleurAdverse = couleurIA.equals("⚫") ? "⚪" : "⚫";

        if (estMax) {
            int meilleurScore = Integer.MIN_VALUE;
            for (String coup : getTousLesCoupsValidesPour(partieOthello, couleurIA)) {
                PartieOthello copiePartieOthello = partieOthello.copier();
                copiePartieOthello.jouerCoup(coup);
                copiePartieOthello.setJoueurCourant(copiePartieOthello.leJoueurSuivant());
                int score = minimax(copiePartieOthello, false, couleurIA, profondeur - 1);
                meilleurScore = Math.max(meilleurScore, score);
            }
            return meilleurScore;
        } else {
            int pireScore = Integer.MAX_VALUE;
            for (String coup : getTousLesCoupsValidesPour(partieOthello, couleurAdverse)) {
                PartieOthello copiePartieOthello = partieOthello.copier();
                copiePartieOthello.jouerCoup(coup);
                copiePartieOthello.setJoueurCourant(copiePartieOthello.leJoueurSuivant());
                int score = minimax(copiePartieOthello, true, couleurIA, profondeur - 1);
                pireScore = Math.min(pireScore, score);
            }
            return pireScore;
        }
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