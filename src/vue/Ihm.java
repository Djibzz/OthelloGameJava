package vue;

import java.util.Scanner;

/**
 * La classe {@code Ihm} gère toutes les interactions avec l'utilisateur.
 * Elle permet d'afficher des informations à l'écran, de récupérer les saisies
 * clavier, et de dialoguer avec le joueur.
 *
 * <p>Cette classe fait partie du package {@code vue} et respecte l'architecture MVC.</p>
 */
public class Ihm {
    private Scanner scanner;

    /**
     * Constructeur de la classe {@code Ihm}.
     * Initialise le scanner pour les entrées clavier.
     */
    public Ihm() {
        scanner = new Scanner(System.in);
    }

    /**
     * Demande au joueur de saisir son nom.
     *
     * @param joueur le numéro du joueur (1 ou 2)
     * @return le nom saisi
     */
    public String demanderNom(int joueur) {
        System.out.println("Joueur " + joueur + ", veuillez saisir votre nom :");
        return scanner.nextLine();
    }

    /**
     * Affiche le plateau de jeu à l'écran.
     *
     * @param tableau un tableau à deux dimensions représentant le plateau
     */
    public void afficherPlateauOthello(String[][] tableau) {
        System.out.println("   A  B  C  D   E  F  G  H  ");
        System.out.println("  -----------------------------");
        for (int i = 0; i < tableau.length; i++) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < tableau[0].length; j++) {
                System.out.print(tableau[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("  -----------------------------");
    }

    /**
     * Affiche le pseudo du joueur courant.
     *
     * @param joueur le nom du joueur courant
     */
    public void afficherJoueurCourant(String joueur) {
        System.out.println("C'est au tour de " + joueur + " de jouer.");
    }

    /**
     * Demande au joueur de saisir un coup au clavier.
     *
     * @param nomJoueur le nom du joueur concerné
     * @return la chaîne représentant le coup (ex : "3C" ou "P")
     */
    public String demanderCoup(String nomJoueur) {
        System.out.println(nomJoueur + ", entrez votre coup (ex: 3C) ou 'P' pour passer : ");
        return scanner.nextLine().toUpperCase();
    }
    public String demanderChoixJeu() {
        System.out.println("Quel jeu voulez-vous lancer ? (Othello / Awale)");
        return scanner.nextLine();
    }


    /**
     * Affiche un message texte à l'utilisateur.
     *
     * @param message le texte à afficher
     */
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    /**
     * Demande aux joueurs s'ils souhaitent rejouer une nouvelle partie.
     *
     * @return {@code true} si la réponse est "o" ou "O", {@code false} sinon
     */
    public boolean demanderRejouer() {
        System.out.println("Voulez-vous rejouer ? (O/N)");
        String reponse = scanner.nextLine().toLowerCase();
        return reponse.equalsIgnoreCase("o");
    }

    /**
     * Demande à l'utilisateur s'il souhaite jouer contre l'IA ou un autre joueur humain.
     *
     * @return "IA" ou "J2" selon le choix de l'utilisateur
     */
    public String demanderTypedeJeu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez vous jouer contre contre l'IA ou un joueur 2 , Taper 'IA' ou 'J2'");
        String choix = scanner.nextLine();
        while (!choix.equalsIgnoreCase("IA") && !choix.equalsIgnoreCase("J2")) {
            System.out.println("Choix invalide. Tapez 'IA' pour jouer contre l'IA ou 'J2' pour jouer à 2.");
            choix = scanner.nextLine();
        }
        return choix;
    }

    /**
     * Demande le type d’IA à affronter (naïve ou forte).
     *
     * @return "naif" ou "fort" selon la sélection de l’utilisateur
     */
    public String demanderTypeIA() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voulez vous jouer contre contre l'IA naive ou une IA intélligente , Taper 'naif' ou 'fort'");
        String choix = scanner.nextLine();
        while (!choix.equalsIgnoreCase("naif") && !choix.equalsIgnoreCase("fort")) {
            System.out.println("Choix invalide. Tapez 'naif' pour jouer contre l'IA naive ou 'fort' pour jouer contre l'IA intélligente");
            choix = scanner.nextLine();
        }
        return choix;
    }

    /**
     * Ferme le scanner pour libérer les ressources d’entrée.
     */
    public void fermerScanner() {
        scanner.close();
    }
}
