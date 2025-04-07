package modele;

/**
 * La classe {@code Plateau} représente le plateau de jeu d'Othello.
 * Elle gère l'initialisation du plateau, la conversion des entrées utilisateur en coordonnées,
 * l'évaluation du plateau pour l'algorithme Minimax et la copie du plateau.
 */
public class Plateau {

    private  int taille1 ,taille2 ;
    private String[][] tableau;

    /**
     * Retourne le tableau représentant le plateau.
     *
     * @return le tableau à deux dimensions du plateau
     */
    public String[][] getTableau() {
        return tableau;
    }

    /**
     * Constructeur qui crée une copie du plateau à partir d'un tableau donné.
     *
     * @param tableauCopie le tableau de copie
     */
    public Plateau(String[][] tableauCopie) {
        this.tableau = tableauCopie;
    }

    /**
     * Constructeur par défaut.
     * Initialise le plateau et le remplit avec des cases vides, puis place les pions de départ.
     */
    public Plateau(int t1,int t2) {
        this.tableau = new String[t1][t2];
        this.taille1=t1;
        this.taille2=t2;

    }

    /**
     * Initialise le plateau avec des cases vides et positionne les pions de départ.
     */
    public void InitialisationTableau() {
        for (int i = 0; i < taille1; i++) {
            for (int j = 0; j < taille2; j++) {
                tableau[i][j] = "\uD83D\uDFE9"; // Cases vides
            }
        }
        tableau[3][3] = "⚪"; // Blanc
        tableau[3][4] = "⚫"; // Noir
        tableau[4][3] = "⚫"; // Noir
        tableau[4][4] = "⚪"; // Blanc
    }

    /**
     * Convertit une lettre (A-H) en indice de colonne.
     *
     * @param s la lettre représentant la colonne
     * @return l'indice de colonne correspondant
     * @throws IllegalArgumentException si la lettre est invalide
     */
    public int lettreVersColonne(String s) {
        String[] Alphabet = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int i = 0; i < Alphabet.length; i++) {
            if (s.equals(Alphabet[i]))
                return i;
        }
        throw new IllegalArgumentException("Lettre invalide : " + s);
    }

    /**
     * Convertit une saisie utilisateur en coordonnées sur le plateau.
     * La saisie doit être du format "3C" ou "P" pour passer.
     *
     * @param s la saisie utilisateur
     * @return un tableau de deux entiers représentant la ligne et la colonne (indices 0-based),
     *         ou {-1, -1} si le joueur veut passer.
     * @throws IllegalArgumentException si le format est invalide ou hors limites
     */
    public int[] inputVersCoordonnes(String s) {
        if (s.equalsIgnoreCase("P"))
            return new int[]{-1, -1}; // Passage de tour
        if (s.length() < 2)
            throw new IllegalArgumentException("Format invalide : " + s);
        int ligne;
        try {
            ligne = Integer.parseInt(s.substring(0, 1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Le premier caractère doit être un entier : " + s, e);
        }
        int colonne = lettreVersColonne(s.substring(1));
        if (ligne < 1 || ligne > 8 || colonne < 0 || colonne > 7) {
            throw new IllegalArgumentException("Coordonnées hors limites : " + s);
        }
        return new int[]{ligne - 1, colonne}; // Conversion en indices 0-based
    }

    /**
     * Vérifie si la partie est terminée en regardant si le plateau est plein.
     *
     * @return {@code true} si le plateau est rempli, {@code false} sinon
     */
    public boolean partieEstFinie() {
        for (int i = 0; i < taille1; i++) {
            for (int j = 0; j < taille2; j++) {
                if (tableau[i][j].equals("\uD83D\uDFE9")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Crée une copie du plateau.
     *
     * @return une nouvelle instance de Plateau avec le même état que l'original
     */
    public Plateau copier() {
        Plateau copie = new Plateau(8,8);
        for (int i = 0; i < taille1; i++) {
            for (int j = 0; j < taille2; j++) {
                copie.getTableau()[i][j] = new String(this.tableau[i][j]);
            }
        }
        return copie;
    }


}
