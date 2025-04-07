package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code Partie} gère le déroulement d'une partie d'Othello.
 * Elle contient les joueurs, le plateau, et la logique pour jouer un coup,
 * vérifier la validité d'un coup, déterminer les encadrements et la fin de la partie.
 */
public class PartieOthello extends PartieAbstraite{
    private Joueur[] joueurs = new Joueur[2];
    private Plateau plateau;
    private static final String Noir = "⚫";
    private static final String Blanc = "⚪";

    /**
     * Constructeur d'une partie.
     *
     * @param j1 le premier joueur (Noir)
     * @param j2 le second joueur (Blanc)
     */
    public PartieOthello(Joueur j1, Joueur j2) {
        super(j1, j2);
        joueurs[0] = j1;
        joueurs[1] = j2;
        plateau = new Plateau(8,8);
        plateau.InitialisationTableau();
    }

    /**
     * Retourne le joueur dont c'est le tour.
     *
     * @return le joueur courant
     */
    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    /**
     * Retourne le tableau des deux joueurs.
     *
     * @return un tableau contenant les deux joueurs
     */
    public Joueur[] getJoueurs() {
        return joueurs;
    }

    /**
     * Retourne le plateau de jeu.
     *
     * @return le plateau
     */
    public Plateau getPlateau() {
        return plateau;
    }

    /**
     * Retourne le joueur suivant.
     *
     * @return le joueur qui doit jouer après le joueur courant
     */
    public Joueur LejoueurSuivant() {
        if (joueurCourant.equals(joueurs[0])) {
            return joueurs[1];
        } else {
            return joueurs[0];
        }
    }

    /**
     * Définit le joueur courant.
     *
     * @param j le joueur à définir comme courant
     */
    public void setJoueurCourant(Joueur j) {
        joueurCourant = j;
    }

    /**
     * Détermine le gagnant global en comparant le nombre de parties gagnées de chaque joueur.
     *
     * @return le joueur gagnant ou {@code null} en cas d'égalité
     */
    public Joueur gagnant() {
        if (joueurs[0].getNbPartiesgagnés() < joueurs[1].getNbPartiesgagnés()) {
            return joueurs[1];
        } else if (joueurs[0].getNbPartiesgagnés() > joueurs[1].getNbPartiesgagnés()) {
            return joueurs[0];
        }
        return null; // Cas d'égalité
    }

    /**
     * Détermine le gagnant de la partie en fonction du nombre de pions.
     * Incrémente le score du gagnant.
     *
     * @return le joueur gagnant de la partie ou {@code null} en cas d'égalité
     */
    public Joueur gagnantdelaPartie() {
        int nbNoir = 0, nbBlanc = 0;
        for (String[] ligne : plateau.getTableau()) {
            for (String p : ligne) {
                if (p.equals(Noir)) {
                    nbNoir++;
                } else if (p.equals(Blanc)) {
                    nbBlanc++;
                }
            }
        }
        if (nbNoir > nbBlanc) {
            joueurs[0].gagne();
            return joueurs[0];
        } else if (nbNoir < nbBlanc) {
            joueurs[1].gagne();
            return joueurs[1];
        }
        return null; // Cas d'égalité
    }

    /**
     * Vérifie si un coup est valide.
     * Le coup est valide s'il correspond à une case vide et qu'il permet d'encadrer un ou plusieurs pions adverses.
     *
     * @param coup le coup sous forme de chaîne (ex: "3C" ou "P" pour passer)
     * @return {@code true} si le coup est valide, {@code false} sinon
     */
    public boolean coupValide(String coup) {
        int[] coords = plateau.inputVersCoordonnes(coup);
        int ligne = coords[0], colonne = coords[1];

        if (ligne == -1 && colonne == -1) {
            if (!peutJouer(joueurCourant))
                setJoueurCourant(LejoueurSuivant());
            return false;
        }
        // Vérifier si la case est vide
        if (!plateau.getTableau()[ligne][colonne].equals("\uD83D\uDFE9")) {
            return false; // Case déjà occupée
        }
        // Vérifier si un pion adverse est adjacent et si un encadrement est possible
        return !getEncadrements(ligne, colonne).isEmpty();
    }

    /**
     * Retourne la liste des directions dans lesquelles des pions adverses sont encadrés.
     *
     * @param ligne   la ligne du coup joué
     * @param colonne la colonne du coup joué
     * @return une liste d'entiers représentant les directions valides
     */
    public List<int[]> getEncadrements(int ligne, int colonne) {
        String couleurJoueur = (joueurCourant.equals(joueurs[0])) ? Noir : Blanc;
        String couleurAdverse = (couleurJoueur.equals(Noir)) ? Blanc : Noir;

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, // Vertical & Horizontal
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1} // Diagonales
        };

        List<int[]> directionsValides = new ArrayList<>();

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int x = ligne + dx, y = colonne + dy;
            boolean pionAdverseTrouve = false;

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                String[][] tableau = plateau.getTableau();
                String caseActuelle = tableau[x][y];

                if (caseActuelle.equals("\uD83D\uDFE9"))  // Case vide = arrêt
                    break;
                if (caseActuelle.equals(couleurAdverse)) {
                    pionAdverseTrouve = true;
                } else if (caseActuelle.equals(couleurJoueur) && pionAdverseTrouve) {
                    directionsValides.add(new int[]{dx, dy}); // Ajouter la direction valide
                    break;
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
     * Vérifie si le joueur peut jouer (au moins un coup valide est disponible).
     *
     * @param joueur le joueur à tester
     * @return {@code true} si le joueur peut jouer, {@code false} sinon
     */
    public boolean peutJouer(Joueur joueur) {
        for (int ligne = 1; ligne <= 8; ligne++) {
            for (char colonne = 'A'; colonne <= 'H'; colonne++) {
                String coup = ligne + "" + colonne;
                if (coupValide(coup)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si le joueur a choisi de passer son tour.
     *
     * @param coup le coup saisi
     * @return {@code true} si le coup correspond à un passage de tour, {@code false} sinon
     */
    public boolean Passersontour(String coup) {
        int[] coords = plateau.inputVersCoordonnes(coup);
        return coords[0] == -1 && coords[1] == -1;
    }

    /**
     * Vérifie si la partie est terminée.
     * La partie est terminée si le plateau est plein ou si aucun joueur ne peut jouer.
     *
     * @return {@code true} si la partie est finie, {@code false} sinon
     */
    public boolean partieEstFinie() {
        boolean aucunCoupValide = !peutJouer(joueurs[0]) && !peutJouer(joueurs[1]);

        // Vérifier si le plateau est plein
        boolean plateauPlein = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (plateau.getTableau()[i][j].equals("\uD83D\uDFE9")) {
                    plateauPlein = false;
                    return plateauPlein;
                }
            }
        }
        return plateauPlein || aucunCoupValide;
    }

    /**
     * Applique un coup sur le plateau.
     * Si le coup n'est pas un passage, il est validé et appliqué en retournant les pions encadrés.
     *
     * @param coup le coup à jouer
     */
    public void jouerCoup(String coup) {
        if (!Passersontour(coup)) {
            if (coupValide(coup)) {
                int[] coords = plateau.inputVersCoordonnes(coup);
                int ligne = coords[0], colonne = coords[1];
                List<int[]> lesEncadrements = getEncadrements(ligne, colonne);
                String couleurJoueur = (joueurCourant.equals(joueurs[0])) ? Noir : Blanc;
                String couleurAdverse = (couleurJoueur.equals(Noir)) ? Blanc : Noir;
                plateau.getTableau()[ligne][colonne] = couleurJoueur;

                for (int[] dir : lesEncadrements) {
                    int dx = dir[0], dy = dir[1];
                    int x = ligne + dx, y = colonne + dy;
                    while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                        String[][] tableau = plateau.getTableau();
                        String caseActuelle = tableau[x][y];
                        if (caseActuelle.equals("\uD83D\uDFE9"))
                            break;
                        if (caseActuelle.equals(couleurAdverse)) {
                            tableau[x][y] = couleurJoueur;
                        }
                        if (caseActuelle.equals(couleurJoueur))
                            break;
                        x += dx;
                        y += dy;
                    }
                }
            }
        }
    }

    /**
     * Vérifie la validité d'un coup pour une couleur donnée (utilisé pour l'algorithme Minimax).
     *
     * @param coup         le coup à tester
     * @param couleurJoueur la couleur utilisée ("⚫" ou "⚪")
     * @return {@code true} si le coup est valide, {@code false} sinon
     */
    public boolean coupValidePour(String coup, String couleurJoueur) {
        int[] coords = plateau.inputVersCoordonnes(coup);
        int ligne = coords[0], colonne = coords[1];

        if (ligne == -1 && colonne == -1) {
            return false; // On ne considère pas le passage de tour ici
        }
        if (!plateau.getTableau()[ligne][colonne].equals("\uD83D\uDFE9")) {
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

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        List<int[]> directionsValides = new ArrayList<>();

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int x = ligne + dx, y = colonne + dy;
            boolean pionAdverseTrouve = false;

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                String caseActuelle = plateau.getTableau()[x][y];

                if (caseActuelle.equals("\uD83D\uDFE9"))
                    break;
                if (caseActuelle.equals(couleurAdverse)) {
                    pionAdverseTrouve = true;
                } else if (caseActuelle.equals(couleurJoueur) && pionAdverseTrouve) {
                    directionsValides.add(new int[]{dx, dy});
                    break;
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
     * Crée une copie de la partie, utilisée pour simuler les coups dans l'algorithme Minimax.
     *
     * @return une nouvelle instance de Partie avec le même état que l'original
     */
    public PartieOthello copier() {
        PartieOthello copie = new PartieOthello(this.joueurs[0].copier(), this.joueurs[1].copier());
        copie.setJoueurCourant(this.getJoueurCourant());
        String[][] tableauOriginal = this.plateau.getTableau();
        String[][] tableauCopie = new String[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(tableauOriginal[i], 0, tableauCopie[i], 0, 8);
        }
        copie.plateau = new Plateau(tableauCopie);
        return copie;
    }
}
