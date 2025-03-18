package modele;

public class Joueur {
    // Les Attributs
    private String Pseudo;
    private  int NbPartiesgagnés;

    private int nbPions;


    public Joueur(String Pseudo){
        this.Pseudo = Pseudo ;
        NbPartiesgagnés = 0;
        nbPions=30;
    }

    //Les Accesseurs

    public String getPseudo() {
        return Pseudo;
    }

    public void setPseudo(String pseudo) {
        Pseudo = pseudo;
    }

    public int getNbPartiesgagnés() {
        return NbPartiesgagnés;
    }

    public void setNbPartiesgagnés(int nbPartiesgagnés) {
        NbPartiesgagnés = nbPartiesgagnés;
    }

    public void setNbPions(int nbPions) {
        this.nbPions = nbPions;
    }

    public int getNbPions() {return nbPions;}

    public void gagne(){NbPartiesgagnés++;}

    public boolean equals(Object  obj){
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
    public Joueur copier() {
        Joueur copie = new Joueur(this.Pseudo);  // Crée un nouveau joueur avec le même pseudo
        copie.setNbPartiesgagnés(getNbPartiesgagnés());  // Copie le nombre de parties gagnées
        copie.setNbPions(getNbPions());  // Copie le nombre de pions
        return copie;
    }

}
