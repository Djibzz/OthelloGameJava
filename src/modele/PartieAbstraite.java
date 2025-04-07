package modele;

public abstract class PartieAbstraite {
    protected Joueur joueur1;
    protected Joueur joueur2;
    protected Joueur joueurCourant;

    public PartieAbstraite(Joueur j1, Joueur j2) {
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.joueurCourant = j1;
    }

    public abstract boolean partieEstFinie();
    public abstract boolean peutJouer(Joueur joueur);
    public abstract void jouerCoup(String coup);
    public abstract boolean coupValide(String coup);
    public abstract Plateau getPlateau();


    public void changerJoueur() {
        joueurCourant = (joueurCourant == joueur1) ? joueur2 : joueur1;
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public Joueur getGagnant() {
        // à surcharger si logique spécifique
        return null;
    }
}
