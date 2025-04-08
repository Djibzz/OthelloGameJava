package modele;

public class JoueurAwale extends JoueurAbstrait {

    public JoueurAwale(String pseudo) {
        super(pseudo);
    }

    @Override
    public JoueurAbstrait copier() {
        JoueurAwale copie = new JoueurAwale(this.pseudo);
        copie.nbPartiesGagnees = this.nbPartiesGagnees;
        return copie;
    }
}
