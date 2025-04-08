package controleur;
import modele.*;
import vue.Ihm;

public abstract class ControleurAbstrait {
    protected PartieAbstraite partie;
    protected Ihm ihm = new Ihm();

    public final void jouerPartie() {
        boolean rejouer;
        do {
            initialiser();
            while (!partie.partieEstFinie()) {
                ihm.afficherPlateauOthello(partie.getPlateau().getTableau());
                Joueur joueur = partie.getJoueurCourant();
                ihm.afficherJoueurCourant(joueur.getPseudo());

                if (partie.peutJouer(joueur)) {
                    traiterTour();
                } else {
                    ihm.afficherMessage("Aucun coup possible. Tour passé.");
                }

                partie.changerJoueur();
            }
            afficherResultat();

            rejouer = ihm.demanderRejouer();
        }while(rejouer);
        ihm.fermerScanner();
    }

    protected abstract void initialiser();
    protected abstract void traiterTour();
    protected abstract void afficherResultat();
}
