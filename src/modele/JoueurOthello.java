package modele;

/**
 * Représente un joueur du jeu Othello.
 * Chaque joueur possède un nombre de pions.
 */
public class JoueurOthello extends JoueurAbstrait {
    private int nbPions;

    /**
     * Crée un joueur Othello avec un pseudo et initialise ses pions.
     * @param pseudo le pseudo du joueur
     */
    public JoueurOthello(String pseudo) {
        super(pseudo);
        this.nbPions = 30;
    }

    /**
     * Retourne le nombre de pions du joueur.
     * @return le nombre de pions
     */
    public int getNbPions() {
        return nbPions;
    }

    /**
     * Définit le nombre de pions du joueur.
     * @param nbPions le nouveau nombre de pions
     */
    public void setNbPions(int nbPions) {
        this.nbPions = nbPions;
    }

    /**
     * Crée une copie du joueur Othello.
     * @return une nouvelle instance de JoueurOthello avec les mêmes attributs
     */
    @Override
    public JoueurOthello copier() {
        JoueurOthello copie = new JoueurOthello(this.pseudo);
        copie.nbPartiesGagnees = this.nbPartiesGagnees;
        return copie;
    }
}