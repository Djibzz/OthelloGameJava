package vue;

import modele.Joueur;
import modele.JoueurIA;
import modele.Partie;
import modele.Plateau;

import java.util.Scanner;

public class Ihm {
    private Scanner scanner;

    public Ihm() {
        scanner = new Scanner(System.in);
    }

    // Demander le nom des joueurs
    public void demanderNom(Joueur joueur1, Joueur joueur2) {
        System.out.println("Joueur 1, veuillez saisir votre nom :");
        joueur1.setPseudo(scanner.nextLine());
        if (!(joueur2 instanceof JoueurIA)) {
            System.out.println("Joueur 2, veuillez saisir votre nom :");
            joueur2.setPseudo(scanner.nextLine());
        }
    }


    // Afficher le plateau de jeu
    public void afficherPlateau(Plateau plateau) {
        System.out.println("   A  B  C  D   E  F  G  H  ");
        for (int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(plateau.getTableau()[i][j] + " ");
            }
            System.out.println();
        }
    } // Afficher le joueur courant
    public void afficherJoueurCourant(Joueur joueur) {
        System.out.println("C'est au tour de " + joueur.getPseudo() + " de jouer.");
    }

    // Afficher les scores (pions capturés et parties gagnées)
    public void afficherScores(Partie partie) {
        int scoreNoir = 0, scoreBlanc = 0;
        // Parcours du tableau pour compter les pions noirs et blancs
        for (String[] ligne : partie.getPlateau().getTableau()) {
            for (String pion : ligne) {
                if (pion.equals("⚫")) {  // Pion noir
                    scoreNoir++;
                } else if (pion.equals("⚪")) {  // Pion blanc
                    scoreBlanc++;
                }
            }
        }

        System.out.println("Score actuel :");
        System.out.println("Noir (joueur " + partie.getJoueurs()[0].getPseudo() + ") = " + scoreNoir + " pions | Parties gagnées = " + partie.getJoueurs()[0].getNbPartiesgagnés());
        System.out.println("Blanc (joueur " + partie.getJoueurs()[1].getPseudo() + ") = " + scoreBlanc + " pions | Parties gagnées = " + partie.getJoueurs()[1].getNbPartiesgagnés());
    }
    // Demander un coup au joueur
    public String demanderCoup(String nomJoueur) {
        System.out.println(nomJoueur + ", entrez votre coup (ex: 3C) ou 'P' pour passer : ");
        return scanner.nextLine().toUpperCase();
    }

        // Afficher un message
        public void afficherMessage(String message) {
            System.out.println(message);
        }



    // Demander aux joueurs s'ils veulent rejouer
    public boolean demanderRejouer() {
        System.out.println("Voulez-vous rejouer ? (O/N)");
        String reponse = scanner.nextLine().toLowerCase();

        return reponse.equals("o"); // Comparaison en minuscule
    }

    // Méthode pour demander le type de jeu (contre IA ou 2 joueurs)
    public String demanderTypeDeJeu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Souhaitez-vous jouer contre l'IA ou à 2 joueurs ? (Tapez 'IA' ou '2')");
        String choix = scanner.nextLine();
        while (!choix.equalsIgnoreCase("IA") && !choix.equals("2")) {
            System.out.println("Choix invalide. Tapez 'IA' pour jouer contre l'IA ou '2' pour jouer à 2.");
            choix = scanner.nextLine();
        }
        return choix;
    }

    public String demanderTypeJeu(){
        // Méthode pour demander le type de jeu (contre IA ou 2 joueurs)
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez vous jouer contre contre l'IA ou un joueur 2 , Taper 'IA' ou 'J2'");
        String.choix = scanner.nextLine();
        while (!choix.equalsIgnoreCase("IA") && !choix.equals("2")) {
            System.out.println("Choix invalide. Tapez 'IA' pour jouer contre l'IA ou '2' pour jouer à 2.");
            choix = scanner.nextLine();
        }
        return choix;
    }




    }


    // Fermer le scanner proprement
    public void fermerScanner() { scanner.close(); }
}
