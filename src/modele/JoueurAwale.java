package modele;

/**
 * Représente un joueur du jeu Awalé.
 * Ce joueur possède un grenier pour stocker les graines capturées.
 */
public class JoueurAwale extends JoueurAbstrait {
    private int grenier = 0;

    /**
     * Crée un joueur Awalé avec le pseudo donné.
     * @param pseudo le pseudo du joueur
     */
    public JoueurAwale(String pseudo) {
        super(pseudo);
    }

    /**
     * Ajoute un nombre de graines au grenier du joueur.
     * @param nbGraines le nombre de graines à ajouter
     */
    public void ajouterAuGrenier(int nbGraines) {
        grenier += nbGraines;
    }

    /**
     * Retourne le nombre de graines actuellement dans le grenier.
     * @return le nombre de graines
     */
    public int getGrenier() {
        return grenier;
    }

    /**
     * Réinitialise le grenier à zéro.
     */
    public void resetGrenier() {
        grenier = 0;
    }

    /**
     * Crée une copie du joueur Awalé.
     * @return une nouvelle instance de JoueurAwale avec les mêmes attributs
     */
    @Override
    public JoueurAbstrait copier() {
        JoueurAwale copie = new JoueurAwale(this.pseudo);
        copie.nbPartiesGagnees = this.nbPartiesGagnees;
        return copie;
    }
}