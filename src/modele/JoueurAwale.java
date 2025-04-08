package modele;

public class JoueurAwale extends JoueurAbstrait {
    private int grenier = 0;

    public JoueurAwale(String pseudo) {
        super(pseudo);
    }
    public void ajouterAuGrenier(int nbGraines) {
        grenier += nbGraines;
    }

    public int getGrenier() {
        return grenier;
    }
    public void resetGrenier() {
        grenier = 0;
    }

    @Override
    public JoueurAbstrait copier() {
        JoueurAwale copie = new JoueurAwale(this.pseudo);
        copie.nbPartiesGagnees = this.nbPartiesGagnees;
        return copie;
    }
}
