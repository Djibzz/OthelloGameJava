package controleur;
import modele.*;
import vue.Ihm;

public abstract class ControleurAbstrait {
    protected PartieAbstraite partie;
    protected Ihm ihm = new Ihm();

    public  void jouerPartie() {
        boolean rejouer;
        do {
            initialiser();
            while (!partie.partieEstFinie()) {
                afficherPlateau();
                 JoueurAbstrait  joueur = partie.getJoueurCourant();
                ihm.afficherJoueurCourant(joueur.getPseudo());

                if (partie.peutJouer(joueur)) {
                    traiterTour();
                } else {
                    ihm.afficherMessage("Aucun coup possible. Tour passé.");
                }

                partie.changerJoueur();
            }
            afficherResultatPartie();

            rejouer = ihm.demanderRejouer();
        }while(rejouer);
        afficherResultat();
        ihm.fermerScanner();
    }

    protected abstract void initialiser();
    protected abstract void traiterTour();
    protected abstract void afficherResultat();
    protected abstract void afficherResultatPartie();
    protected abstract void afficherPlateau();

}
