package vue;

import java.util.Scanner;

public class Ihm {
    private Scanner scanner;

    public Ihm() {
        scanner = new Scanner(System.in);
    }

    // Demander le nom des joueurs
    public String demanderNomJoueur(int numeroJoueur) { }

    // Afficher le plateau de jeu
    public void afficherPlateau(String[][] plateau) { }

    // Demander un coup au joueur
    public String demanderCoup(String nomJoueur) { }

    // Afficher un message
    public void afficherMessage(String message) { }

    // Fermer le scanner proprement
    public void fermerScanner() { scanner.close(); }
}
