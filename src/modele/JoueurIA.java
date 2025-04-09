package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code JoueurIA} représente une IA qui joue à l'Othello.
 * Elle hérite de {@code JoueurOthello} et utilise une stratégie fournie.
 */
public class JoueurIA extends JoueurOthello {
    private StrategieIA strategie;

    /**
     * Constructeur par défaut de l'IA avec un pseudo.
     * @param pseudo le pseudo de l'IA
     */
    public JoueurIA(String pseudo) {
        super(pseudo);
    }

    /**
     * Constructeur avec une stratégie spécifique.
     * @param pseudo le pseudo de l'IA
     * @param strategie la stratégie utilisée par l'IA
     */
    public JoueurIA(String pseudo, StrategieIA strategie) {
        super(pseudo);
        this.strategie = strategie;
    }

    /**
     * Obtient la liste de tous les coups valides pour la partie donnée.
     * @param partieOthello la partie en cours
     * @return la liste des coups valides
     */
    public List<String> obtenirCoupsValides(PartieOthello partieOthello) {
        List<String> coupsValides = new ArrayList<>();
        for (int ligne = 1; ligne <= 8; ligne++) {
            for (char colonne = 'A'; colonne <= 'H'; colonne++) {
                String coup = ligne + "" + colonne;
                if (partieOthello.coupValide(coup)) {
                    coupsValides.add(coup);
                }
            }
        }
        return coupsValides;
    }

    /**
     * Joue un coup en utilisant la stratégie définie.
     * @param partieOthello la partie en cours
     * @return le coup joué
     */
    public String jouerCoup(PartieOthello partieOthello) {
        String couleur = this.equals(partieOthello.getJoueurs()[0]) ? "⚫" : "⚪";
        String coup = strategie.choisirCoup(partieOthello, couleur);
        if (coup != null) {
            partieOthello.jouerCoup(coup);
        } else {
            partieOthello.jouerCoup("P");
        }
        return coup;
    }
}