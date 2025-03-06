package modele;

public class Joueur {
    // Les Attributs
    private String Pseudo;
    private  int NbPartiesgagnés;

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
