package modele;

public class Plateau {

    /**Cette classe traite les données brutes envoyé par le Joueuer et les transforme en
     * données utilisable par le jeu Ex::l"User ecrit 2H ca va nous rendre un int[2]={1,7}
     **/
    private static final int taille = 8; // Taille du plateau 8x8
    private static String [][] tableau;

    public String [][] getTableau() {
        return tableau;
    }

    public static void setTableau(String[][] tableau) {
        Plateau.tableau = tableau;
    }
    public Plateau(String[][] tableauCopie) {
        this.tableau = tableauCopie;
    }

    public Plateau(){
        tableau = new String[taille][taille];
        InitialisationTableau();
    }

    public  void InitialisationTableau(){
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

    public int lettreVersColonne(String s){
        String[] Alphabet = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int i =0;i< Alphabet.length;i++){
           if(s.equals( Alphabet[i]))
               return i;
        }
        throw new IllegalArgumentException("Lettre invalide : " + s);
    }
    public int[] inputVersCoordonnes(String s) {
        if (s.equalsIgnoreCase("P"))
            return new int[]{-1, -1}; // Indique que le joueur veut passer
        if (s.length() < 2)
            throw new IllegalArgumentException("Format invalide : " + s);
        // Vérification que le premier caractère est un entier
        int ligne;
        try {
            ligne = Integer.parseInt(s.substring(0, 1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Le premier caractère doit être un entier : " + s, e);
        }
        int colonne = lettreVersColonne(s.substring(1));
        // Vérification des limites
        if (ligne < 1 || ligne > 8 || colonne < 0 || colonne > 7) {
            throw new IllegalArgumentException("Coordonnées hors limites : " + s);
        }
        return new int[]{ligne - 1, colonne}; // Ajustement (lignes 1-8 → index 0-7)
    }

    public int evaluerPlateau( String couleurJoueur) {
        int score = 0;
        int nbPionsJoueur = 0, nbPionsAdversaire = 0;
        String couleurAdverse = couleurJoueur.equals("⚫") ? "⚪" : "⚫";
        String [][] tableau = getTableau();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tableau[i][j].equals(couleurJoueur)) {
                    nbPionsJoueur++;
                    if ((i == 0 && j == 0) || (i == 0 && j == 7) || (i == 7 && j == 0) || (i == 7 && j == 7)) {
                        score += 11; // Coin
                    } else if (i == 0 || i == 7 || j == 0 || j == 7) {
                        score += 6; // Bord
                    } else {
                        score += 1; // Autres positions
                    }
                }else if (tableau[i][j].equals(couleurAdverse)) {
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
    public boolean partieEstFinie() {
        // Si plus de case vide ou aucun joueur ne peut jouer
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tableau[i][j].equals("\uD83D\uDFE9")) {
                    return false;
                }
            }
        }
        return true; // Plateau rempli
    }
    public Plateau copier() {
        Plateau copie = new Plateau();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                copie.getTableau()[i][j] = new String(this.tableau[i][j]);
            }
        }
        return copie;
    }


    public void initialiserPlateauDebug() {
        // Tout vider
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tableau[i][j] = "🟩";  // Case vide
            }
        }
        // Exemple d’état complexe pour test
        tableau[4][2] = "⚫";
        //tableau[3][4] = "⚪";
        tableau[4][3] = "⚫";
        tableau[4][4] = "⚫";
        tableau[5][3] = "⚫";
        tableau[5][4] = "⚪";
    }



}
