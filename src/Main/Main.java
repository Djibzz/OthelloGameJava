package Main;

import controleur.Controleur;
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
        Controleur controleur = new Controleur();
        controleur.jouerPartie();
    }
}
