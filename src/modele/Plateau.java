package modele;

/**
 * La classe {@code Plateau} représente le plateau de jeu d'Othello.
 * Elle gère l'initialisation du plateau, la conversion des entrées utilisateur en coordonnées,
 * l'évaluation du plateau pour l'algorithme Minimax et la copie du plateau.
 */
public class Plateau {

    private static final int taille = 8; // Taille du plateau 8x8
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
    public Plateau() {
        tableau = new String[taille][taille];
        InitialisationTableau();
    }

    /**
     * Initialise le plateau avec des cases vides et positionne les pions de départ.
     */
    public void InitialisationTableau() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
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
     * Évalue le plateau pour une couleur donnée.
     * Les pions dans les coins, sur les bords et ailleurs sont valorisés différemment.
     * Si la partie est finie, retourne une valeur très élevée ou très basse.
     *
     * @param couleurJoueur la couleur à évaluer ("⚫" ou "⚪")
     * @return le score du plateau pour la couleur donnée
     */
    public int evaluerPlateau(String couleurJoueur) {
        int score = 0;
        int nbPionsJoueur = 0, nbPionsAdversaire = 0;
        String couleurAdverse = couleurJoueur.equals("⚫") ? "⚪" : "⚫";
        String[][] tableau = getTableau();

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (tableau[i][j].equals(couleurJoueur)) {
                    nbPionsJoueur++;
                    if ((i == 0 && j == 0) || (i == 0 && j == taille - 1) ||
                            (i == taille - 1 && j == 0) || (i == taille - 1 && j == taille - 1)) {
                        score += 11; // Coin
                    } else if (i == 0 || i == taille - 1 || j == 0 || j == taille - 1) {
                        score += 6; // Bord
                    } else {
                        score += 1; // Autres positions
                    }
                } else if (tableau[i][j].equals(couleurAdverse)) {
                    nbPionsAdversaire++;
                }
            }
        }
        if (partieEstFinie()) {
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

    /**
     * Vérifie si la partie est terminée en regardant si le plateau est plein.
     *
     * @return {@code true} si le plateau est rempli, {@code false} sinon
     */
    public boolean partieEstFinie() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
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
        Plateau copie = new Plateau();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                copie.getTableau()[i][j] = new String(this.tableau[i][j]);
            }
        }
        return copie;
    }

    /**
     * Initialise le plateau en mode debug avec un état prédéfini pour les tests.
     */
    public void initialiserPlateauDebug() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                tableau[i][j] = "🟩";  // Case vide
            }
        }
        tableau[4][2] = "⚫";
        tableau[4][3] = "⚫";
        tableau[4][4] = "⚫";
        tableau[5][3] = "⚫";
        tableau[5][4] = "⚪";
    }
}
