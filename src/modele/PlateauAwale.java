package modele;

/**
 * Plateau du jeu d'Awalé : 2 rangées de 6 cases contenant des graines.
 */
public class PlateauAwale implements PlateauJeu {
    private int[][] tableau;
    private  int grainesParTrous;

    /**
     * Crée un plateau d'Awalé vide mais prêt à être initialisé.
     *
     *
     */
    public PlateauAwale() {
        this.grainesParTrous = 4;
        this.tableau = new int[2][6];
    }

    @Override
    public void InitialisationTableau() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                tableau[i][j] = grainesParTrous;
            }
        }
    }

    @Override
    public int[][] getTableau() {
        return tableau;
    }

    /**
     * Retourne le nombre de graines dans un trou donné.
     *
     * @param ligne la rangée (0 ou 1)
     * @param colonne l’index du trou (0 à 5)
     * @return le nombre de graines
     */
    public int getGraines(int ligne, int colonne) {
        return tableau[ligne][colonne];
    }

    /**
     * Met à jour un trou spécifique avec un nouveau nombre de graines.
     *
     * @param ligne la rangée
     * @param colonne l’index du trou
     * @param nbGraines le nouveau nombre
     */
    public void setGraines(int ligne, int colonne, int nbGraines) {
        tableau[ligne][colonne] = nbGraines;
    }

    public boolean peutJouer(int ligne) {
        for (int col = 0; col < 6; col++) {
            if (tableau[ligne][col] > 0) {
                return true;
            }
        }
        return false;
    }

    public int totalGraines(int ligne) {
        int total = 0;
        for (int col = 0; col < 6; col++) {
            total += tableau[ligne][col];
        }
        return total;
    }



    /**
     * Retourne une copie profonde du tableau.
     * Utile pour IA ou simulations.
     */
    public int[][] copierTableau() {
        int[][] copie = new int[2][6];
        for (int i = 0; i < 2; i++) {
            System.arraycopy(tableau[i], 0, copie[i], 0, 6);
        }
        return copie;
    }
}
