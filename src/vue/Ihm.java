package vue;



import java.util.Scanner;

public class Ihm {
    private Scanner scanner;

    public Ihm() {
        scanner = new Scanner(System.in);
    }

    // Demander le nom des joueurs
    public String demanderNom(int joueur ) {
        System.out.println("Joueur "+joueur+", veuillez saisir votre nom :");
        return scanner.nextLine();
    }


    // Afficher le plateau de jeu
    public void afficherPlateau(String [][] tableau) {
        System.out.println("   A  B  C  D   E  F  G  H  ");
        System.out.println("  -----------------------------");
        for (int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < 8; j++) {
                System.out.print(tableau[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("  -----------------------------");
    }
    // Afficher le joueur courant
    public void afficherJoueurCourant(String joueur) {
        System.out.println("C'est au tour de " + joueur + " de jouer.");
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

        return reponse.equalsIgnoreCase("o"); // Comparaison en minuscule
    }

    public String demanderTypedeJeu(){
        // Méthode pour demander le type de jeu (contre IA ou 2 joueurs)
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez vous jouer contre contre l'IA ou un joueur 2 , Taper 'IA' ou 'J2'");
        String choix = scanner.nextLine();
        while (!choix.equalsIgnoreCase("IA") && !choix.equalsIgnoreCase("J2")) {
            System.out.println("Choix invalide. Tapez 'IA' pour jouer contre l'IA ou 'J2' pour jouer à 2.");
            choix = scanner.nextLine();
        }
        return choix;
    }

    public String demanderTypeIA(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez vous jouer contre contre l'IA naive ou une IA intélligente , Taper 'naif' ou 'fort'");
        String choix = scanner.nextLine();
        while (!choix.equalsIgnoreCase("naif") && !choix.equalsIgnoreCase("fort")) {
            System.out.println("Choix invalide. Tapez 'naif' pour jouer contre l'IA naive ou 'fort' pour jouer contre l'IA intélligente");
            choix = scanner.nextLine();
        }
        return choix;
    }

    // Fermer le scanner proprement
    public void fermerScanner() { scanner.close(); }
}
