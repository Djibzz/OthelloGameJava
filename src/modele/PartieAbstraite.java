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
     * Méthode à surcharger : retourne le gagnant selon les règles du jeu.
     *
     * @return le joueur gagnant ou null si égalité
     */
    public JoueurAbstrait leJoueurSuivant() {
        return joueurCourant.equals(joueurs[0]) ? joueurs[1] : joueurs[0];
    }


    // Méthodes abstraites à implémenter dans les classes spécifiques

    public abstract boolean partieEstFinie();

    public abstract boolean peutJouer(JoueurAbstrait joueur);

    public abstract void jouerCoup(String coup);

    public abstract boolean coupValide(String coup);

    public abstract PlateauOthello getPlateau();
}
