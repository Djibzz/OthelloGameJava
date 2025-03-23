package Main;

import controleur.Controleur;
import modele.Partie;
import vue.Ihm;

public class Main {
    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        Controleur controleur = new Controleur();
        controleur.jouerPartie();
        //controleur.lancerModeDebug();
       
    }
}