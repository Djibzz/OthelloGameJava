package modele;

/**
 * Classe abstraite représentant une partie générique d’un jeu à deux joueurs.
 * Chaque sous-classe doit implémenter les règles spécifiques du jeu (ex : Othello, Awalé...).
 */
public abstract class PartieAbstraite {
    protected JoueurAbstrait joueur1;
    protected JoueurAbstrait joueur2;
    protected JoueurAbstrait joueurCourant;
    protected JoueurAbstrait[] joueurs;

    /**
     * Constructeur d’une partie générique.
     * Initialise les joueurs et définit le joueur courant comme le joueur 1.
     *
     * @param j1 le premier joueur
     * @param j2 le second joueur
     */
    public PartieAbstraite(JoueurAbstrait j1, JoueurAbstrait j2) {
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.joueurCourant = j1;
        this.joueurs = new JoueurAbstrait[]{j1, j2};
    }

    /**
     * Change le joueur courant (alterne entre les deux joueurs).
     */
    public void changerJoueur() {
        joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
    }

    /**
     * Retourne le joueur courant.
     *
     * @return le joueur dont c’est le tour
     */
    public JoueurAbstrait getJoueurCourant() {
        return joueurCourant;
    }

    /**
     * Retourne les deux joueurs sous forme de tableau.
     *
     * @return tableau contenant joueur1 et joueur2
     */
    public JoueurAbstrait[] getJoueurs() {
        return joueurs;
    }

    /**
     * Retourne le joueur suivant dans la partie.
     *
     * @return le joueur adverse
     */
    public JoueurAbstrait leJoueurSuivant() {
        return joueurCourant.equals(joueurs[0]) ? joueurs[1] : joueurs[0];
    }

    /**
     * Méthode à implémenter : indique si la partie est finie.
     *
     * @return true si la partie est terminée
     */
    public abstract boolean partieEstFinie();

    /**
     * Méthode à implémenter : vérifie si un joueur peut jouer.
     *
     * @param joueur le joueur concerné
     * @return true s’il peut jouer
     */
    public abstract boolean peutJouer(JoueurAbstrait joueur);

    /**
     * Méthode à implémenter : applique un coup.
     *
     * @param coup la chaîne représentant le coup
     */
    public abstract void jouerCoup(String coup);

    /**
     * Méthode à implémenter : vérifie si un coup est valide.
     *
     * @param coup le coup saisi
     * @return true si le coup est valide
     */
    public abstract boolean coupValide(String coup);

    /**
     * Méthode à implémenter : retourne le plateau associé à la partie.
     *
     * @return le plateau de jeu
     */
    public abstract PlateauJeu getPlateau();

    /**
     * Méthode à implémenter : retourne le gagnant.
     *
     * @return le joueur gagnant ou null si égalité
     */
    public abstract JoueurAbstrait getGagnant();
}