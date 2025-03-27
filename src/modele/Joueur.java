package modele;

/**
 * La classe {@code Joueur} représente un joueur humain dans le jeu d'Othello.
 * Elle contient le pseudo du joueur, le nombre de parties gagnées et le nombre de pions.
 */
public class Joueur {
    private String Pseudo;
    private int NbPartiesgagnés;
    private int nbPions;

    /**
     * Constructeur de la classe Joueur.
     *
     * @param Pseudo le pseudo initial du joueur
     */
    public Joueur(String Pseudo) {
        this.Pseudo = Pseudo;
        NbPartiesgagnés = 0;
        nbPions = 30;
    }

    /**
     * Retourne le pseudo du joueur.
     *
     * @return le pseudo
     */
    public String getPseudo() {
        return Pseudo;
    }

    /**
     * Définit le pseudo du joueur.
     *
     * @param pseudo le nouveau pseudo
     */
    public void setPseudo(String pseudo) {
        Pseudo = pseudo;
    }

    /**
     * Retourne le nombre de parties gagnées.
     *
     * @return le nombre de parties gagnées
     */
    public int getNbPartiesgagnés() {
        return NbPartiesgagnés;
    }

    /**
     * Définit le nombre de parties gagnées.
     *
     * @param nbPartiesgagnés le nouveau nombre de parties gagnées
     */
    public void setNbPartiesgagnés(int nbPartiesgagnés) {
        NbPartiesgagnés = nbPartiesgagnés;
    }

    /**
     * Retourne le nombre de pions.
     *
     * @return le nombre de pions
     */
    public int getNbPions() {
        return nbPions;
    }

    /**
     * Définit le nombre de pions.
     *
     * @param nbPions le nouveau nombre de pions
     */
    public void setNbPions(int nbPions) {
        this.nbPions = nbPions;
    }

    /**
     * Incrémente le nombre de parties gagnées.
     */
    public void gagne() {
        NbPartiesgagnés++;
    }

    /**
     * Vérifie l'égalité entre deux joueurs en comparant le pseudo, le nombre de parties gagnées et le nombre de pions.
     *
     * @param obj l'objet à comparer
     * @return {@code true} si les joueurs sont égaux, {@code false} sinon
     */
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;
        final Joueur j = (Joueur) obj;
        if ((this.getPseudo() == null) ? (j.getPseudo() != null) : !this.getPseudo().equals(j.getPseudo()))
            return false;
        if (this.getNbPartiesgagnés() != j.getNbPartiesgagnés())
            return false;
        if (this.getNbPions() != j.getNbPions())
            return false;

        return true;
    }

    /**
     * Crée une copie du joueur.
     *
     * @return une nouvelle instance de Joueur avec les mêmes attributs
     */
    public Joueur copier() {
        Joueur copie = new Joueur(this.Pseudo);
        copie.setNbPartiesgagnés(getNbPartiesgagnés());
        copie.setNbPions(getNbPions());
        return copie;
    }
}
