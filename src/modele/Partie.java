package modele;

public class Partie {

    private Joueur joueurCourant;
    private Joueur[] joueurs= new Joueur[2];
    private Plateau plateau ;
    private boolean PartieFinie;
    private static final String Noir = "⚫";
    private static final String Blanc = "⚪";


    public Joueur getJoueurCourant() {return joueurCourant;}


    public Plateau getPlateau() {return plateau;}

    public Joueur LejoueurSuivant(Joueur j) {
        if (joueurCourant.equals(joueurs[0])) {
            return joueurs[1];
        } else return joueurs[0];
    }

    public void setJoueurCourant(Joueur j) {
        joueurCourant = j;
    }

    public Partie(Joueur joueur1,Joueur joueur2) {
        this.joueurCourant = joueur1;
        joueurs[0]= joueur1;
        joueurs[1]= joueur2;
        PartieFinie =false;
        plateau=new Plateau();
    }

    public Joueur gagnant(){
        if (joueurs[0].getNbPartiesgagnés() < joueurs[1].getNbPartiesgagnés()) {
            return joueurs[1];
        } else if (joueurs[0].getNbPartiesgagnés() > joueurs[1].getNbPartiesgagnés()) {
            return joueurs[0];
        }
        return null; // Cas d'égalité
    }


    public Joueur gagnantdelaPartie(){
        int nbNoir = 0, nbBlanc = 0;
        for (String[] ligne : plateau.getTableau()) {
                for (String p: ligne) {
                    if (p.equals(Noir)) { nbNoir++; }
                    else if (p.equals(Blanc)) { nbBlanc++; }
                }
        }
        if (nbNoir>nbBlanc) {
            joueurs[0].gagne();
            return joueurs[0];
        }
        else {
            joueurs[1].gagne();
            return joueurs[0];
        }
    }

    public boolean coupValide(String coup) {
        int[] coords = plateau.inputVersCoordonnes(coup);
        int ligne = coords[0], colonne = coords[1];

        // Vérifier si la case est vide
        if (!plateau.getTableau()[ligne][colonne].equals("\uD83D\uDFE9")) {
            return false; // Case déjà occupée
        }

        // Vérifier si un pion adverse est adjacent et si un encadrement est possible
        return peutRetournerPions(ligne, colonne);
    }
    private boolean peutRetournerPions(int ligne, int colonne) {
        String couleurJoueur = (joueurCourant.equals(joueurs[0])) ? Noir : Blanc;
        String couleurAdverse = (couleurJoueur.equals(Noir)) ? Blanc : Noir;

    public void jouerCoup(String coup) { }




}



