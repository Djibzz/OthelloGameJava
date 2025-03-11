package modele;

public class Joueur {
    // Les Attributs
    private String Pseudo;
    private  int NbPartiesgagnés;



    public Joueur(String Pseudo){
        this.Pseudo = Pseudo ;
        NbPartiesgagnés = 0;
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

}
