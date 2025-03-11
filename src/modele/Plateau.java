package modele;

public class Plateau {

    /**Cette classe traite les données brutes envoyé par le Joueuer et les transforme en
     * données utilisable par le jeu Ex::l"User ecrit 2H ca va nous rendre un int[2]={1,7}
     **/
    private static final int taille = 8; // Taille du plateau 8x8
    private String [][] tableau;

    public String [][] getTableau() {
        return tableau;
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
    // Affichage du plateau
    public void afficherPlateau() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                System.out.print(tableau[i][j] + " ");
            }
            System.out.println(); // Retour à la ligne
        }
    }


    public int lettreVersColonne(String s){
        String[] Alphabet = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int i =0;i< Alphabet.length;i++){
           if(s.equals( Alphabet[i]))
               return i;
        }
        throw new IllegalArgumentException("Lettre invalide : " + s);
    }
    public int[] inputVersCoordonnes(String s){
        if (s.length() < 2)
            throw new IllegalArgumentException("Format invalide : " + s);

        // Vérification que le premier caractère est un entier
        int ligne;
        try {
            ligne = Integer.parseInt(s.substring(0, 1)); // Essaye de convertir le premier caractère en entier
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Le premier caractère doit être un entier : " + s, e);
        }
        int colonne = lettreVersColonne(s.substring(1)); //Convertit la lettre en index de colonne
        return new int[]{ligne, colonne};
    }



}
