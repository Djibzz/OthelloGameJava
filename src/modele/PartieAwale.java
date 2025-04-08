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

    @Override
    public void jouerCoup(String coup) {
        int ligne = (joueurCourant == joueur1) ? 0 : 1;
        int col = Integer.parseInt(coup) - 1;
        int graines = plateau.getGraines(ligne, col);
        plateau.setGraines(ligne, col, 0);

        int i = ligne, j = col;
        while (graines > 0) {
            j++;
            if (j > 5) {
                j = 0;
                i = 1 - i;
            }
            if (i == ligne && j == col) continue;
            plateau.setGraines(i, j, plateau.getGraines(i, j) + 1);
            graines--;
        }
    }

    @Override
    public PlateauJeu getPlateau() {
        return plateau;
    }

    @Override
    public JoueurAbstrait getGagnant() {
        int score1 = plateau.totalGraines(0);
        int score2 = plateau.totalGraines(1);
        if (score1 > score2) return joueur1;
        if (score2 > score1) return joueur2;
        return null;
    }
}
