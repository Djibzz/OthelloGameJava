package vue;

import modele.Joueur;
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

        System.out.println("Joueur 2, veuillez saisir votre nom :");
        joueur2.setPseudo(scanner.nextLine());
    }


    // Afficher le plateau de jeu
    public void afficherPlateau(Plateau plateau) {
        Plateau.afficherPlateau();
    }

    // Demander un coup au joueur
    public String demanderCoup(String nomJoueur) { }

    // Afficher un message
    public void afficherMessage(String message) { }

    // Fermer le scanner proprement
    public void fermerScanner() { scanner.close(); }
}
