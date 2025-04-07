package Main;

import controleur.*;
import vue.Ihm;

/**
 * La classe {@code Main} contient le point d'entrée du programme.
 * Elle crée une instance de {@code Controleur} qui lance la partie.
 */
public class Main {
    /**
     * Point d'entrée de l'application.
     *
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        String choixJeu = ihm.demanderChoixJeu();

        ControleurAbstrait controleur= null;

        if (choixJeu.equalsIgnoreCase("othello")) {
            controleur = new ControleurOthello();
        } else if (choixJeu.equalsIgnoreCase("awale")) {
            //controleur = new ControleurAwale();
        } else {
            ihm.afficherMessage("Jeu non reconnu. Fin du programme.");
            return;
        }

        controleur.jouerPartie();
    }
}

