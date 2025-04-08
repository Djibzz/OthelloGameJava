package modele;

public class JoueurOthello extends JoueurAbstrait {
    private int nbPions;

    public JoueurOthello(String pseudo) {
        super(pseudo);
        this.nbPions = 30;
    }

    public int getNbPions() {
        return nbPions;
    }

    public void setNbPions(int nbPions) {
        this.nbPions = nbPions;
    }

    @Override
    public JoueurOthello copier() {
        JoueurOthello copie = new JoueurOthello(this.pseudo);
        copie.nbPartiesGagnees = this.nbPartiesGagnees;
        return copie;
    }
}
