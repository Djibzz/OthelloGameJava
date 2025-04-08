package modele;

public class PartieAwale extends PartieAbstraite {
    private PlateauAwale plateau;

    public PartieAwale(JoueurAbstrait j1, JoueurAbstrait j2) {
        super(j1, j2);
        this.plateau = new PlateauAwale();
        plateau.InitialisationTableau();
    }

    @Override
    public boolean partieEstFinie() {
        return !plateau.peutJouer(0) || !plateau.peutJouer(1);
    }

    @Override
    public boolean peutJouer(JoueurAbstrait joueur) {
        int ligne = (joueur == joueur1) ? 0 : 1;
        return plateau.peutJouer(ligne);
    }

    @Override
    public boolean coupValide(String coup) {
        try {
            int col = Integer.parseInt(coup) - 1;
            if (col < 0 || col > 5)
                return false;
            int ligne = (joueurCourant == joueur1) ? 0 : 1;
            return plateau.getGraines(ligne, col) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void jouerCoup(String coup) {
        int ligne = (joueurCourant == joueur1) ? 0 : 1;
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

            // Sauter la case d'origine UNE SEULE FOIS
            if (!origineIgnoree && i == origineLigne && j == origineCol) {
                origineIgnoree = true;
                continue; // graines-- est volontairement ignoré ici
            }

            plateau.setGraines(i, j, plateau.getGraines(i, j) + 1);
            graines--;
        }

        // Captures dans le camp adverse (si la dernière graine tombe chez l'adversaire)
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




    @Override
    public PlateauJeu getPlateau() {
        return plateau;
    }

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
