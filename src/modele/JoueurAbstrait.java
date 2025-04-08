package modele;

/**
 * Classe abstraite représentant un joueur générique.
 * Fournit les comportements communs à tous les types de joueurs (Othello, Awalé, etc.).
 */
public abstract class JoueurAbstrait {
    protected String pseudo;
    protected int nbPartiesGagnees;

    /**
     * Constructeur du joueur.
     *
     * @param pseudo le pseudo du joueur
     */
    public JoueurAbstrait(String pseudo) {
        this.pseudo = pseudo;
        this.nbPartiesGagnees = 0;
    }

    /**
     * Retourne le pseudo du joueur.
     *
     * @return le pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Définit un nouveau pseudo pour le joueur.
     *
     * @param pseudo le nouveau pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Retourne le nombre de parties gagnées.
     *
     * @return le nombre de victoires
     */
    public int getNbPartiesGagnees() {
        return nbPartiesGagnees;
    }

    /**
     * Incrémente le nombre de parties gagnées de 1.
     */
    public void gagne() {
        nbPartiesGagnees++;
    }

    /**
     * Vérifie si deux joueurs sont égaux (pseudo + nombre de parties gagnées).
     *
     * @param obj l'objet à comparer
     * @return true si les deux joueurs sont équivalents
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;
        JoueurAbstrait j = (JoueurAbstrait) obj;
        return this.pseudo.equals(j.pseudo) &&
                this.nbPartiesGagnees == j.nbPartiesGagnees;
    }

    /**
     * Méthode à implémenter dans chaque sous-classe pour dupliquer le joueur.
     *
     * @return une copie du joueur
     */
    public abstract JoueurAbstrait copier();
}
