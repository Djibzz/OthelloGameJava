package modele;

/**
 * Représente une partie du jeu Awalé.
 * Gère le plateau, les coups joués, les captures, et la détection de la fin de partie.
 */
public class PartieAwale extends PartieAbstraite {
    private PlateauAwale plateau;

    /**
     * Constructeur d'une partie Awalé.
     *
     * @param j1 le premier joueur
     * @param j2 le second joueur
     */
    public PartieAwale(JoueurAbstrait j1, JoueurAbstrait j2) {
        super(j1, j2);
        this.plateau = new PlateauAwale();
        plateau.InitialisationTableau();
        this.joueurCourant = j1;
    }

    /**
     * Indique si la partie est terminée.
     * La partie est finie si un joueur ne peut plus jouer.
     *
     * @return true si la partie est finie, false sinon
     */
    @Override
    public boolean partieEstFinie() {
        return !plateau.peutJouer(0) || !plateau.peutJouer(1);
    }

    /**
     * Vérifie si un joueur donné peut jouer.
     *
     * @param joueur le joueur à tester
     * @return true si le joueur peut jouer
     */
    @Override
    public boolean peutJouer(JoueurAbstrait joueur) {
        int ligne = (joueur == joueur1) ? 1 : 0;
        return plateau.peutJouer(ligne);
    }

    /**
     * Vérifie si un coup est valide.
     *
     * @param coup le coup saisi
     * @return true si le coup est valide
     */
    @Override
    public boolean coupValide(String coup) {
        try {
            int col = Integer.parseInt(coup) - 1;
            if (col < 0 || col > 5)
                return false;
            int ligne = (joueurCourant == joueur1) ? 1 : 0;
            return plateau.getGraines(ligne, col) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Joue un coup donné en suivant les règles du jeu Awalé.
     *
     * @param coup la colonne choisie (1 à 6)
     */
    @Override
    public void jouerCoup(String coup) {
        int ligne = (joueurCourant == joueur1) ? 1 : 0;
        int col = Integer.parseInt(coup) - 1;
        int graines = plateau.getGraines(ligne, col);
        plateau.setGraines(ligne, col, 0);

        int i = ligne, j = col;
        int origineLigne = ligne;
        int origineCol = col;
        boolean origineIgnoree = false;

        while (graines > 0) {
            j++;
            if (j > 5) {
                j = 0;
                i = 1 - i;
            }

            if (origineIgnoree || i != origineLigne || j != origineCol) {
                plateau.setGraines(i, j, plateau.getGraines(i, j) + 1);
                graines--;
            } else {
                origineIgnoree = true;
            }
        }

        // Captures dans le camp adverse
        int campAdverse = (ligne == 0) ? 1 : 0;
        int grainesCapturees = 0;

        if (i == campAdverse) {
            while (j >= 0) {
                int nb = plateau.getGraines(i, j);
                if (nb == 2 || nb == 3) {
                    grainesCapturees += nb;
                    plateau.setGraines(i, j, 0);
                } else {
                    break;
                }
                j--;
            }
            ((JoueurAwale) joueurCourant).ajouterAuGrenier(grainesCapturees);
        }
    }

    /**
     * Retourne le plateau de jeu Awalé.
     *
     * @return le plateau
     */
    @Override
    public PlateauJeu getPlateau() {
        return plateau;
    }

    /**
     * Détermine le gagnant de la partie.
     *
     * @return le joueur gagnant, ou null en cas d'égalité
     */
    @Override
    public JoueurAbstrait getGagnant() {
        int score1 = ((JoueurAwale) joueur1).getGrenier();
        int score2 = ((JoueurAwale) joueur2).getGrenier();

        if (score1 > score2) {
            joueur1.gagne();
            return joueur1;
        } else if (score2 > score1) {
            joueur2.gagne();
            return joueur2;
        }
        return null;
    }
}