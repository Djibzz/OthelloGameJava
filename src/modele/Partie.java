package modele;

public class Partie {

    private Joueur joueur1;
    private Joueur joueur2;

    private Plateau plateau ;
    private static final String Noir = "⚫";
    private static final String Blanc = "⚪";


    public Joueur getJoueur1() {
        return joueur1;
    }

    public Joueur getJoueur2() {return joueur2;
    }

    public Plateau getPlateau() {return plateau;
    }

    public String getNoir() {return Noir;
    }

    public String getBlanc() {return Blanc;
    }

    public Partie (Joueur joueur1, Joueur joueur2){
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.plateau = new Plateau();

    }




}



