package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une partie du jeu Othello.
 * Gère le plateau, les règles, la validation des coups, les encadrements, et le gagnant.
 */
public class PartieOthello extends PartieAbstraite {
    private PlateauOthello plateauOthello;
    private static final String Noir = "⚫";
    private static final String Blanc = "⚪";

    /**
     * Constructeur de la partie Othello.
     *
     * @param j1 joueur 1
     * @param j2 joueur 2
     */
    public PartieOthello(JoueurAbstrait j1, JoueurAbstrait j2) {
        super(j1, j2);
        this.joueurs = new JoueurAbstrait[]{j1, j2};
        this.plateauOthello = new PlateauOthello(8, 8);
        plateauOthello.InitialisationTableau();
    }

    /**
     * Retourne le joueur actuellement en train de jouer.
     *
     * @return le joueur courant
     */
    @Override
    public JoueurAbstrait getJoueurCourant() {
        return joueurCourant;
    }

    /**
     * Retourne le tableau des deux joueurs de la partie.
     *
     * @return un tableau contenant les deux joueurs
     */
    @Override
    public JoueurAbstrait[] getJoueurs() {
        return joueurs;
    }

    /**
     * Retourne le plateau de jeu Othello utilisé dans cette partie.
     *
     * @return le plateau de jeu
     */
    @Override
    public PlateauOthello getPlateau() {
        return plateauOthello;
    }

    /**
     * Retourne le joueur adverse.
     *
     * @return joueur suivant
     */
    public JoueurOthello leJoueurSuivant() {
        return getJoueurCourant().equals(joueurs[0])
                ? (JoueurOthello) joueurs[1]
                : (JoueurOthello) joueurs[0];
    }

    /**
     * Définit manuellement le joueur courant.
     *
     * @param j le joueur à définir
     */
    public void setJoueurCourant(JoueurAbstrait j) {
        this.joueurCourant = j;
    }

    @Override
    public JoueurOthello getGagnant() {
        if (joueurs[0].getNbPartiesGagnees() < joueurs[1].getNbPartiesGagnees()) {
            return (JoueurOthello) joueurs[1];
        } else if (joueurs[0].getNbPartiesGagnees() > joueurs[1].getNbPartiesGagnees()) {
            return (JoueurOthello) joueurs[0];
        }
        return null;
    }

    /**
     * Détermine le gagnant à l’issue d’une seule partie d’Othello.
     *
     * @return le joueur gagnant ou null si égalité
     */
    public JoueurOthello gagnantDeLaPartie() {
        int nbNoir = 0, nbBlanc = 0;
        for (String[] ligne : plateauOthello.getTableau()) {
            for (String p : ligne) {
                if (p.equals(Noir)) nbNoir++;
                else if (p.equals(Blanc)) nbBlanc++;
            }
        }

        if (nbNoir > nbBlanc) {
            joueurs[0].gagne();
            return (JoueurOthello) joueurs[0];
        } else if (nbNoir < nbBlanc) {
            joueurs[1].gagne();
            return (JoueurOthello) joueurs[1];
        }
        return null;
    }

    @Override
    public boolean coupValide(String coup) {
        int[] coords = plateauOthello.inputVersCoordonnes(coup);
        int ligne = coords[0], colonne = coords[1];

        if (ligne == -1 && colonne == -1) {
            if (!peutJouer(getJoueurCourant()))
                setJoueurCourant(leJoueurSuivant());
            return false;
        }

        if (!plateauOthello.getTableau()[ligne][colonne].equals("🟩")) {
            return false;
        }

        return !getEncadrements(ligne, colonne).isEmpty();
    }

    /**
     * Retourne les directions valides d'encadrement pour un coup donné.
     *
     * @param ligne ligne du coup
     * @param colonne colonne du coup
     * @return liste des directions valides
     */
    public List<int[]> getEncadrements(int ligne, int colonne) {
        String couleurJoueur = (getJoueurCourant().equals(joueurs[0])) ? Noir : Blanc;
        String couleurAdverse = couleurJoueur.equals(Noir) ? Blanc : Noir;

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        List<int[]> valides = new ArrayList<>();

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int x = ligne + dx, y = colonne + dy;
            boolean encadrement = false;

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                String caseActuelle = plateauOthello.getTableau()[x][y];
                if (caseActuelle.equals("🟩")) break;
                if (caseActuelle.equals(couleurAdverse)) encadrement = true;
                else if (caseActuelle.equals(couleurJoueur) && encadrement) {
                    valides.add(new int[]{dx, dy});
                    break;
                } else break;
                x += dx; y += dy;
            }
        }

        return valides;
    }

    /**
     * Vérifie si un joueur donné peut encore effectuer un coup valide.
     *
     * @param joueur le joueur à tester
     * @return true si un coup est possible, false sinon
     */
    @Override
    public boolean peutJouer(JoueurAbstrait joueur) {
        for (int ligne = 1; ligne <= 8; ligne++) {
            for (char colonne = 'A'; colonne <= 'H'; colonne++) {
                String coup = ligne + "" + colonne;
                if (coupValide(coup)) return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si la partie est terminée.
     * La partie est finie lorsqu'il n'y a plus de case vide sur le plateau.
     *
     * @return true si la partie est terminée, false sinon
     */
    @Override
    public boolean partieEstFinie() {
        for (String[] ligne : plateauOthello.getTableau()) {
            for (String p : ligne) {
                if (p.equals("🟩")) return false;
            }
        }
        return true;
    }

    /**
     * Joue le coup donné sur le plateau, en retournant les pions encadrés.
     *
     * @param coup le coup joué sous forme de chaîne (ex : "3C")
     */
    @Override
    public void jouerCoup(String coup) {
        if (!coupValide(coup)) return;

        int[] coords = plateauOthello.inputVersCoordonnes(coup);
        int ligne = coords[0], colonne = coords[1];
        List<int[]> directions = getEncadrements(ligne, colonne);
        String couleurJoueur = (getJoueurCourant().equals(joueurs[0])) ? Noir : Blanc;
        String couleurAdverse = couleurJoueur.equals(Noir) ? Blanc : Noir;

        plateauOthello.getTableau()[ligne][colonne] = couleurJoueur;

        for (int[] dir : directions) {
            int x = ligne + dir[0], y = colonne + dir[1];
            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                String caseActuelle = plateauOthello.getTableau()[x][y];
                if (caseActuelle.equals("🟩")) break;
                if (caseActuelle.equals(couleurAdverse)) {
                    plateauOthello.getTableau()[x][y] = couleurJoueur;
                } else if (caseActuelle.equals(couleurJoueur)) break;
                x += dir[0]; y += dir[1];
            }
        }
    }


    // Methodes créées pour l'Algorithme MiniMAX elle ne dépende pas du joueur Courant mais de la couleur en paramètre


    /**


     * Vérifie la validité d'un coup pour une couleur donnée (utilisé pour l'algorithme Minimax).


     *


     * @param coup         le coup à tester


     * @param couleurJoueur la couleur utilisée ("⚫" ou "⚪")


     * @return {@code true} si le coup est valide, {@code false} sinon


     */

    public boolean coupValidePour(String coup, String couleurJoueur) {

        int[] coords = plateauOthello.inputVersCoordonnes(coup);

        int ligne = coords[0], colonne = coords[1];



        if (ligne == -1 && colonne == -1) {

            return false; // On ne considère pas le passage de tour ici

        }





        // Vérifier si la case est vide

        if (!plateauOthello.getTableau()[ligne][colonne].equals("\uD83D\uDFE9")) {

            return false; // Case déjà occupée

        }


        return !getEncadrementsPour(ligne, colonne, couleurJoueur).isEmpty();

    }


    /**


     * Retourne la liste des directions d'encadrement valides pour une couleur donnée.


     *


     * @param ligne         la ligne du coup


     * @param colonne       la colonne du coup


     * @param couleurJoueur la couleur utilisée ("⚫" ou "⚪")


     * @return la liste des directions d'encadrement


     */

    public List<int[]> getEncadrementsPour(int ligne, int colonne, String couleurJoueur) {
        String couleurAdverse = (couleurJoueur.equals("⚫")) ? "⚪" : "⚫";
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        List<int[]> directionsValides = new ArrayList<>();

        for (int[] dir : directions) {

            int dx = dir[0], dy = dir[1];

            int x = ligne + dx, y = colonne + dy;

            boolean pionAdverseTrouve = false;

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {

                String caseActuelle = plateauOthello.getTableau()[x][y];
                if (caseActuelle.equals("\uD83D\uDFE9"))


                    break;

                if (caseActuelle.equals(couleurAdverse)) {

                    pionAdverseTrouve = true;
                } else {
                    break;
                }
                x += dx;
                y += dy;
            }
        }
        return directionsValides;
    }

    /**
     * Crée une copie profonde de la partie Othello.
     *
     * @return une nouvelle instance de PartieOthello
     */
    public PartieOthello copier() {
        JoueurOthello j1 = ((JoueurOthello) joueurs[0]).copier();
        JoueurOthello j2 = ((JoueurOthello) joueurs[1]).copier();
        PartieOthello copie = new PartieOthello(j1, j2);
        copie.setJoueurCourant(this.getJoueurCourant());
        String[][] plateauOriginal = this.plateauOthello.getTableau();
        String[][] plateauCopie = new String[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(plateauOriginal[i], 0, plateauCopie[i], 0, 8);
        }
        copie.plateauOthello = new PlateauOthello(plateauCopie);
        return copie;
    }
}