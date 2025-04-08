package modele;

import java.util.ArrayList;
import java.util.List;

public class PartieOthello extends PartieAbstraite {
    private PlateauOthello plateauOthello;
    private static final String Noir = "⚫";
    private static final String Blanc = "⚪";

    public PartieOthello(JoueurAbstrait j1, JoueurAbstrait j2) {
        super(j1, j2);
        this.joueurs = new JoueurAbstrait[]{j1, j2};
        this.plateauOthello = new PlateauOthello(8, 8);
        plateauOthello.InitialisationTableau();
    }

    public JoueurAbstrait getJoueurCourant() {
        return joueurCourant;
    }

    public JoueurAbstrait[] getJoueurs() {
        return joueurs;
    }


    @Override
    public PlateauOthello getPlateau() {
        return plateauOthello;
    }

    public JoueurOthello leJoueurSuivant() {
        return getJoueurCourant().equals(joueurs[0])
                ? (JoueurOthello) joueurs[1]
                : (JoueurOthello) joueurs[0];
    }

    public void setJoueurCourant(JoueurAbstrait j) {
        this.joueurCourant = j;
    }

    public JoueurOthello getGagnant() {
        if (joueurs[0].getNbPartiesGagnees() < joueurs[1].getNbPartiesGagnees()) {
            return (JoueurOthello) joueurs[1];
        } else if (joueurs[0].getNbPartiesGagnees() > joueurs[1].getNbPartiesGagnees()) {
            return (JoueurOthello) joueurs[0];
        }
        return null;
    }

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

    @Override
    public boolean peutJouer(JoueurAbstrait joueur) {
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

    public boolean passerSonTour(String coup) {
        int[] coords = plateauOthello.inputVersCoordonnes(coup);
        return coords[0] == -1 && coords[1] == -1;
    }

    @Override
    public boolean partieEstFinie() {
        boolean aucunCoup = !peutJouer(joueurs[0]) && !peutJouer(joueurs[1]);

        for (String[] ligne : plateauOthello.getTableau()) {
            for (String p : ligne) {
                if (p.equals("🟩")) return false;
            }
        }

        return true || aucunCoup;
    }

    @Override
    public void jouerCoup(String coup) {
        if (passerSonTour(coup)) return;
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


    public boolean peutJouer(JoueurOthello joueur) {
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

    public boolean coupValidePour(String coup, String couleurJoueur) {
        int[] coords = plateauOthello.inputVersCoordonnes(coup);
        int ligne = coords[0], colonne = coords[1];

        if (ligne == -1 && colonne == -1) {
            return false; // On ne considère pas le passage de tour ici
        }
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
                String caseActuelle = plateauOthello.getTableau()[x][y];

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
