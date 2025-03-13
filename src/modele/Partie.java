package modele;

import controleur.Controleur;
import java.util.ArrayList;
import java.util.List;


public class Partie {

    private Joueur joueurCourant;
    private Joueur[] joueurs = new Joueur[2];
    private Plateau plateau;
    private boolean PartieFinie;
    private static final String Noir = "⚫";
    private static final String Blanc = "⚪";


    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Joueur LejoueurSuivant(Joueur j) {
        if (joueurCourant.equals(joueurs[0])) {
            return joueurs[1];
        } else return joueurs[0];
    }

    public void setJoueurCourant(Joueur j) {
        joueurCourant = j;
    }

    public Partie(Joueur joueur1, Joueur joueur2) {
        this.joueurCourant = joueur1;
        joueurs[0] = joueur1;
        joueurs[1] = joueur2;
        PartieFinie = false;
        plateau = new Plateau();
    }

    public Joueur gagnant() {
        if (joueurs[0].getNbPartiesgagnés() < joueurs[1].getNbPartiesgagnés()) {
            return joueurs[1];
        } else if (joueurs[0].getNbPartiesgagnés() > joueurs[1].getNbPartiesgagnés()) {
            return joueurs[0];
        }
        return null; // Cas d'égalité
    }


    public Joueur gagnantdelaPartie() {
        int nbNoir = 0, nbBlanc = 0;
        for (String[] ligne : plateau.getTableau()) {
            for (String p : ligne) {
                if (p.equals(Noir)) {
                    nbNoir++;
                } else if (p.equals(Blanc)) {
                    nbBlanc++;
                }
            }
        }
        if (nbNoir > nbBlanc) {
            joueurs[0].gagne();
            return joueurs[0];
        } else {
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
        return !getEncadrements(ligne, colonne).isEmpty();
    }

    public List<int[]> getEncadrements(int ligne, int colonne) {
        String couleurJoueur = (joueurCourant.equals(joueurs[0])) ? Noir : Blanc;
        String couleurAdverse = (couleurJoueur.equals(Noir)) ? Blanc : Noir;

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, // Vertical & Horizontal
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1} // Diagonales
        };

        List<int[]> directionsValides = new ArrayList<>();

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int x = ligne + dx, y = colonne + dy;
            boolean pionAdverseTrouve = false;

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                String[][] tableau = plateau.getTableau();
                String caseActuelle = tableau[x][y];

                if (caseActuelle.equals("\uD83D\uDFE9"))  // Case vide = arrêt
                    break;
                if (caseActuelle.equals(couleurAdverse)) {
                    pionAdverseTrouve = true;
                } else if (caseActuelle.equals(couleurJoueur) && pionAdverseTrouve) {
                    directionsValides.add(new int[]{dx, dy}); // Ajouter la direction valide
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

    public boolean peutJouer(Joueur joueur) {
        for (int ligne = 1; ligne <= 8; ligne++) { // Lignes de 1 à 8
            for (char colonne = 'A'; colonne <= 'H'; colonne++) { // Colonnes de A à H
                String coup = ligne + "" + colonne; // Ex: "3C"
                if (coupValide(coup)) {
                    return true; // Le joueur a au moins un coup valide
                }
            }
        }
        return false; // Aucun coup valide trouvé, le joueur peut passer
    }

    public boolean Passersontour(String coup) {
        int[] coords = plateau.inputVersCoordonnes(coup);
        //Vérifier si le joueur n'as pas passé son tour
        if (coords.equals(new int[]{-1, -1})) {
            LejoueurSuivant(joueurCourant);
            return true;
        }
        return false;

    }
    public boolean partieEstFinie() {
        // Vérifier si les deux joueurs ne peuvent plus jouer
        boolean aucunCoupValide = !peutJouer(joueurs[0]) && !peutJouer(joueurs[1]);

        // Vérifier si le plateau est plein
        boolean plateauPlein = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (plateau.getTableau()[i][j].equals("\uD83D\uDFE9")) { // Si une case est vide
                    plateauPlein = false;
                    break;
                }
            }
        }

        // La partie est finie si le plateau est plein ou si aucun joueur ne peut jouer
        return plateauPlein || aucunCoupValide;
    }

    public void jouerCoup(String coup) {
        if (!Passersontour(coup)) {
            if (coupValide(coup)) {
                int[] coords = plateau.inputVersCoordonnes(coup);
                int ligne = coords[0], colonne = coords[1];
                List<int[]> lesEncadrements = getEncadrements(ligne, colonne);
                String couleurJoueur = (joueurCourant.equals(joueurs[0])) ? Noir : Blanc;
                String couleurAdverse = (couleurJoueur.equals(Noir)) ? Blanc : Noir;
                plateau.getTableau()[ligne][colonne]= couleurJoueur;

                for (int[] dir : lesEncadrements) {
                    int dx = dir[0], dy = dir[1];
                    int x = ligne + dx, y = colonne + dy;
                    while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                        String[][] tableau = plateau.getTableau();
                        String caseActuelle = tableau[x][y];
                        if (caseActuelle.equals("\uD83D\uDFE9"))  // Case vide = arrêt
                            break;
                        if (caseActuelle.equals(couleurAdverse)) {
                            tableau[x][y]= couleurJoueur;
                        }
                        if (caseActuelle.equals(couleurJoueur))  // Case couleur du joueur = arrêt
                            break;
                        x += dx;
                        y += dy;
                    }
                }
                LejoueurSuivant(joueurCourant);
            }


        }
    }
}



